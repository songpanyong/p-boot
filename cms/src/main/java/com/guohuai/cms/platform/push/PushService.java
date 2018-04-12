package com.guohuai.cms.platform.push;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.mail.MailUtil;
import com.guohuai.cms.component.push.PushContEntity;
import com.guohuai.cms.component.push.PushUtil;
import com.guohuai.cms.component.userUtil.RedisUserinfo;
import com.guohuai.cms.component.userUtil.UserinfoRedisUtil;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.mail.api.InvestorLabelResp;
import com.guohuai.cms.platform.mail.api.UserCenterApi;
import com.guohuai.cms.platform.mail.api.UserInfoRep;
import com.guohuai.cms.platform.mail.api.UserLabelResp;
import com.guohuai.cms.platform.mail.api.UserLabelResp.LabelInvestor;
import com.guohuai.cms.platform.push.PushQueryRep.PushQueryRepBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PushService {
	
	@Autowired
	private PushDao pushDao;
	@Autowired
	private UserCenterApi userCenterApi;
	@Autowired
	private AdminUtil adminUtil;
    
	@Value("${push.appId}")
	private String appId;
    
    @Value("${push.appKey}")
	private String appKey;
   
    @Value("${push.masterSecret}")
	private String masterSecret;
   
    @Value("${push.host}")
	private String host;
    
    @Autowired
	private RedisTemplate<String, String> redis;
    
    private static long OFFLINEEXPIRETIME = 24 * 3600 * 1000;//推送离线有效时间
    
    public PushEntity save(PushEntity en){
    	return this.pushDao.save(en);
    }
    
   /**
    * 页面查询信息
    * @param spec
    * @param pageable
    * @return
    */
	public PagesRep<PushQueryRep> pushFindAll(Specification<PushEntity> spec, Pageable pageable) {
		Page<PushEntity> products = this.pushDao.findAll(spec, pageable);
		PagesRep<PushQueryRep> pagesRep = new PagesRep<PushQueryRep>();
		
		for (PushEntity pe : products) {
			String labelCodeName = null;
			if (pe.getLabelCode() != null && !pe.getLabelCode().isEmpty()){
				InvestorLabelResp label = userCenterApi.getInvestorLabel(pe.getLabelCode()); 
					labelCodeName = label.getLabelName();
			}
			
			PushQueryRep rep = new PushQueryRepBuilder()
					.oid(pe.getOid())
					.title(pe.getTitle())
					.pusher(this.adminUtil.getAdminName(pe.getPusher()))
					.status(pe.getStatus())
					.pushTime(pe.getPushTime())
					.url(pe.getUrl())
					.type(pe.getType())
					.summary(pe.getSummary())
					.pushType(pe.getPushType())
					.pushUserOid(pe.getPushUserOid())
					.pushUserAcc(pe.getPushUserAcc())
					.labelCode(pe.getLabelCode())
					.labelCodeName(labelCodeName)
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(products.getTotalElements());	
		return pagesRep;
	}


	/**
	 * 新增/编辑推送信息
	 * @param req
	 * @param operator
	 * @return
	 */
    public BaseRep addPush(PushAddReq req, String operator) {
    	BaseRep rep = new BaseRep();
    	if(req.getOid() != null && !"".equals(req.getOid())){
    		PushEntity push = this.getOne(req.getOid());
			
    		if (req.getPushType().equals(PushEntity.PUSH_pushType_person)){
    			UserInfoRep user = userCenterApi.isregist(req.getPushUserAcc());
				if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
    			push = groupPushEntity(push, req, operator, user.getInvestorOid(), req.getPushUserAcc(), null);
    		}else if(req.getPushType().equals(PushEntity.PUSH_pushType_group)){
    			push = groupPushEntity(push, req, operator, null, null, req.getLabelCode());
    		}else{
    			push = groupPushEntity(push, req, operator, null, null, null);
    		}
    		
        	push = this.pushDao.save(push);
		}else{
			PushEntity push = new PushEntity();
			if (req.getPushType().equals(PushEntity.PUSH_pushType_person)){
				UserInfoRep user = userCenterApi.isregist(req.getPushUserAcc());
				if (user == null || !user.isRegist() || user.getInvestorOid() == null || user.getInvestorOid().isEmpty()){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
				push = groupPushEntity(push, req, operator, user.getInvestorOid(), req.getPushUserAcc(), null);
	        	push = this.pushDao.save(push);
			}else if(req.getPushType().equals(PushEntity.PUSH_pushType_group)){
    			push = groupPushEntity(push, req, operator, null, null, req.getLabelCode());
    		}else{
				push = groupPushEntity(push, req, operator, null, null, null);
			}
			push = this.pushDao.save(push);
		}
    	
    	return rep;
    }
    
    // 组装并保存
    public PushEntity groupAndSave(String title, String url, String type, String creator, String summary, String pushType, String pushUserOid, String pushUserAcc,String labelCode){
    	PushEntity push = new PushEntity();
    	push.setTitle(title);
    	push.setUrl(url);
    	push.setType(type);
    	push.setCreator(creator);
    	push.setSummary(summary);
    	push.setPushType(pushType);
    	push.setLabelCode(labelCode);
    	push.setPushUserOid(pushUserOid);
    	push.setPushUserAcc(pushUserAcc);
    	
    	push.setStatus(PushEntity.PUSH_status_pending);
    	push.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
    	
		return this.save(push);
    }
    
    // 组装推送
    private PushEntity groupPushEntity(PushEntity push, PushAddReq req, String operator, String userOid, String pushUserAcc, String labelCode){
		push.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
    	push.setTitle(req.getTitle());
    	push.setStatus(PushEntity.PUSH_status_pending);
    	push.setType(req.getType());
    	push.setLabelCode(labelCode);
    	push.setCreator(operator);
    	if("activity".equals(req.getType())){
    		push.setUrl(req.getUrl());
    	}else{
    		push.setUrl(req.getType());
    	}
    	push.setSummary(req.getSummary());
    	push.setPushType(req.getPushType());
    	push.setPushUserOid(userOid);
    	push.setPushUserAcc(pushUserAcc);
		return push;
    }
    
    /**
	 * 获取推送实体
	 * @param oid
	 * @return
	 */
	public PushEntity getOne(String oid){
		PushEntity en = this.pushDao.findOne(oid);
		if(en == null){
			//error.define[70001]=推送信息不存在(CODE:70001)
			throw MoneyException.getException(70001);
		}
		return en;
	}
	
	/**
	 * 删除推送信息
	 * @param oid
	 * @return
	 */
	public BaseRep delPush(String oid) {
		BaseRep rep = new BaseRep();
		this.pushDao.delete(oid);
		return rep;
	}
	
	/**
	 * 标题名称重复判断
	 * @param title
	 * @param oid
	 * @return
	 */
	public int isHasSamePushTitle(String title, String oid) {
		return this.pushDao.isHasSamePushTitle(title,oid);
	}
	
	/**
	 * 获取推送信息详情
	 * @param oid
	 * @return
	 */
	public PushQueryRep getPush(String oid) {
		PushEntity pe = this.pushDao.getOne(oid);
		String labelCodeName = null;
		if (pe.getLabelCode() != null && !pe.getLabelCode().isEmpty()){
			InvestorLabelResp label = userCenterApi.getInvestorLabel(pe.getLabelCode()); 
				labelCodeName = label.getLabelName();
		}
		
		PushQueryRep rep = new PushQueryRepBuilder()
				.oid(pe.getOid())
				.title(pe.getTitle())
				.status(pe.getStatus())
				.pushTime(pe.getPushTime())
				.url(pe.getUrl())
				.review(adminUtil.getAdminName(pe.getReview()))
				.reviewTime(pe.getReviewTime())
				.pusher(adminUtil.getAdminName(pe.getPusher()))
				.creator(adminUtil.getAdminName(pe.getCreator()))
				.createTime(pe.getCreateTime())
				.reviewRemark(pe.getReviewRemark())
				.type(pe.getType())
				.summary(pe.getSummary())
				.pushType(pe.getPushType())
				.pushUserOid(pe.getPushUserOid())
				.pushUserAcc(pe.getPushUserAcc())
				.labelCode(pe.getLabelCode())
				.labelCodeName(labelCodeName)
				.build();
		return rep;
		
	}
	
	/**
	 * 推送审核
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep pushReview(PushReviewRep req, String operator) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		PushEntity push =  this.getOne(req.getOid());
		push.setReview(operator);
		push.setReviewTime(now);
		if(req.getApprResult().equals(PushEntity.PUSH_reviewStatus_pass)){
			push.setStatus(PushEntity.PUSH_status_reviewed);
		}else if(req.getApprResult().equals(PushEntity.PUSH_reviewStatus_refused)){
			push.setStatus(PushEntity.PUSH_status_refused);
		}
		push.setReviewRemark(req.getRemark());
		this.pushDao.save(push);
		return rep;
	}
	
	/**
	 * 推送上/下架
	 * @param oid
	 * @return
	 */
	public BaseRep pushPubilsh(String oid,String operator) {
		BaseRep rep = new BaseRep();
		try{
			PushEntity push = this.getOne(oid);
			
			if (push.getPushType().equals(PushEntity.PUSH_pushType_all)){
				//向安卓手机推送信息
				this.pushAndroidtoApp(push);
				//向IOS手机推送信息
				this.pushIOStoApp(push);
			}else if(push.getPushType().equals(PushEntity.PUSH_pushType_person)){
				RedisUserinfo info = UserinfoRedisUtil.get(redis, push.getPushUserOid());
				if (info != null){ 
					if (info.getClientSys().equals(PushEntity.PUSH_clientSys_android)){
						this.push2Single(info.getClientId(), 0, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
					}else{
						this.push2Single(info.getClientId(), 1, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
					}
				}
			}else if(push.getPushType().equals(PushEntity.PUSH_pushType_group)){
				UserLabelResp userInfolist = userCenterApi.getUserLabelList(push.getLabelCode()); 
				if(userInfolist!=null&&userInfolist.getErrorCode()==0&&userInfolist.getRows()!=null&&userInfolist.getRows().size()>0){
					List<String> iosClientIds = new ArrayList<String>();
					List<String> androidClientIds = new ArrayList<String>();
					for(LabelInvestor userInfos:userInfolist.getRows()){
						RedisUserinfo info = UserinfoRedisUtil.get(redis, userInfos.getInvestorOid());
						
						// 逐一推送方式
//						if (info.getClientSys().equals("android")){
//							this.push2Single(info.getClientId(), 0, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
//						}else{
//							this.push2Single(info.getClientId(), 1, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
//						}
						
						if (info.getClientId() != null && !info.getClientId().isEmpty()){
							if (info.getClientSys().equals(PushEntity.PUSH_clientSys_android)){
								androidClientIds.add(info.getClientId());
							}else{
								iosClientIds.add(info.getClientId());
							}
						}
					}
					// 批量推送方式
					if (!androidClientIds.isEmpty()){
						this.push2ToList(androidClientIds, 0, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
					}
					if (!iosClientIds.isEmpty()){
						this.push2ToList(iosClientIds, 1, push.getType(), push.getTitle(), push.getSummary(), push.getUrl());
					}
				}
			}else{
				// 推送类型有误(CODE:70002)
				throw MoneyException.getException(70002);
			}
			
			if(push.getStatus().equalsIgnoreCase(PushEntity.PUSH_status_reviewed)){
				push.setStatus(PushEntity.PUSH_status_on);
				push.setPusher(operator);
				push.setPushTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
			}
			this.pushDao.save(push);
		}catch(Exception e){
			e.printStackTrace();
			//error.define[70000]=推送失败(CODE:70000)
			throw MoneyException.getException(70000);
		}
		return rep;
	}
	
	/**
	 * 给安卓推送
	 * @param pushEntity
	 * @throws Exception
	 */
	public void pushAndroidtoApp(PushEntity pushEntity){
    	IGtPush push = new IGtPush(host, appKey, masterSecret);

        AppMessage message = new AppMessage();
        //链接推送
        LinkTemplate template = linkTemplate(pushEntity.getTitle(), pushEntity.getSummary(), pushEntity.getUrl());
        NotificationTemplate notiTem = notificationTemplate(pushEntity.getUrl(), pushEntity.getTitle(), pushEntity.getSummary());
        String type=pushEntity.getType();//活动属于链接类型
         if(type.equals("activity")){
            message.setData(template);
         }else{
            message.setData(notiTem);
         }

        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINEEXPIRETIME);
        //推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions(); 
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
       
        //手机类型
        List<String> phoneTypeList = new ArrayList<String>();
        phoneTypeList.add("ANDROID");
        //省份
        List<String> provinceList = new ArrayList<String>();
        //自定义tag
        List<String> tagList = new ArrayList<String>();

        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG,tagList);
        message.setConditions(cdt); 
        
        IPushResult ret = push.pushMessageToApp(message, "Android");
        System.out.println(ret.getResponse().toString());
	}
	
	/**
	 * 给IOS推送
	 * @param pushEntity
	 * @throws Exception
	 */
	public void pushIOStoApp(PushEntity pushEntity)throws Exception{
    	 IGtPush push = new IGtPush(host, appKey, masterSecret);
    	 //透传模板，对ios使用透传推送
    	 TransmissionTemplate transmisionTem = transmissionTemplate(pushEntity.getUrl(), pushEntity.getTitle(), pushEntity.getSummary());
         
         AppMessage message = new AppMessage();
         message.setData(transmisionTem);
         message.setOffline(true);
         //离线有效时间，单位为毫秒，可选
         message.setOfflineExpireTime(OFFLINEEXPIRETIME);
         //推送给App的目标用户需要满足的条件
         AppConditions cdt = new AppConditions(); 
         List<String> appIdList = new ArrayList<String>();
         appIdList.add(appId);
         message.setAppIdList(appIdList);
         
         //手机类型
         List<String> phoneTypeList = new ArrayList<String>();
         phoneTypeList.add("IOS");
         //省份
         List<String> provinceList = new ArrayList<String>();
         //自定义tag
         List<String> tagList = new ArrayList<String>();

         cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
         cdt.addCondition(AppConditions.REGION, provinceList);
         cdt.addCondition(AppConditions.TAG,tagList);
         message.setConditions(cdt); 
         
         IPushResult ret = push.pushMessageToApp(message,"IOS");
         System.out.println(ret.getResponse().toString());
	}
	    
	/**
	 * 通知模板
	 * @param content	透传消息
	 * @param title		标题
	 * @param text		内容
	 * @return
	 */
	public NotificationTemplate notificationTemplate(String content, String title, String text) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        String summary=text;
        if(summary != null && !"".equals(summary)){
        	if(summary.length() > 45){
        		summary=summary.substring(0, 45) + "......";
        	}
        }
        template.setText(summary);
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
     
        return template;
	}

	/**
	 * 链接模板
	 * @return
	 * @throws Exception
	 */
    public LinkTemplate linkTemplate(String title, String text, String url){
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle(title);
        String summary=text;
        if(summary != null && !"".equals(summary)){
        	if(summary.length() > 45){
        		summary=summary.substring(0, 45) + "......";
        	}
        }
        template.setText(summary);
        template.setLogo("icon.png");
        template.setLogoUrl("");
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        template.setUrl(url);

        return template;
    }
    
    /**
     * 透传模板
     * @return
     */
    public TransmissionTemplate transmissionTemplate(String content, String title, String text) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        //透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        template.setTransmissionContent(content);
        APNPayload payload = new APNPayload();
        payload.setAutoBadge("1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        payload.addCustomMsg("data", content);
        //字典模式使用下者
        payload.setAlertMsg(getDictionaryAlertMsg(title, text));
        template.setAPNInfo(payload);
        return template;
    }
    
    /**
     * 给ISO发送消息
     * @return
     */
    private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String text){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        //通知文本消息标题
        alertMsg.setTitle(title);
        //通知文本消息字符串
        String summary=text;
        if(summary != null && !"".equals(summary)){
        	if(summary.length() > 45){
        		summary=summary.substring(0, 45) + "......";
        	}
        }
        alertMsg.setBody(summary);
        alertMsg.setActionLocKey("国槐金融演示版");
        alertMsg.setLaunchImage("launch-image");
        // IOS8.2以上版本支持
        alertMsg.setTitleLocKey(title);
        return alertMsg;
    }
    
    // 个推
    /**
     * @param clientId 终端clientId
     * @param userOid 用户userOid
     * @param phoneType	0是安卓系统1是苹果系统
     * @param mesTemp	站内信模板id
     * @param param		参数
     * @return
     */
	public BaseResp sendPush2Person(String userOid, String mesTempCode, String mesParam) {
		BaseResp resp = new BaseResp();
		try {
			log.info("后台请求推送参数：用户oid："+userOid+",推送模板code："+mesTempCode+",推送参数："+mesParam);
			if (userOid == null || userOid.isEmpty()){
				// 会员id不能为空！(CODE:13002)
				throw MoneyException.getException(13002);
			}
			
			String phone = null;
			try {
				UserInfoRep user = userCenterApi.getLoginUserInfo(userOid);
				if (user == null || user.getPhoneNum() == null){
					// 会员不存在！(CODE:13000)
					throw MoneyException.getException(13000);
				}
				phone = user.getPhoneNum();
			} catch (Exception e) {
				e.printStackTrace();
				// 会员信息访问失败！(CODE:13001)
				throw MoneyException.getException(13001);
			}
			
			PushContEntity pushContEntity = PushUtil.pushContentsMap.get(mesTempCode);
			if (pushContEntity == null){
				// 推送内容模板不存在！(CODE:70003)
				throw MoneyException.getException(70003);
			}
			
			String content = pushContEntity.getPushContent();
			if (mesParam != null && !mesParam.isEmpty()){
				String[] par = JSON.parseObject(mesParam, String[].class);
				content = MailUtil.replaceComStrArr(content, par);
			}
			
			String clientId = null;
			RedisUserinfo info = UserinfoRedisUtil.get(redis, userOid);
			if (info != null){
				clientId = info.getClientId();
			}
			
			log.info("后台请求推送参数处理后：用户oid："+userOid+",手机："+phone+",推送标题："+pushContEntity.getPushTitle()+",推送内容："+content+",clientId:"+clientId);
			
			PushEntity push = new PushEntity();
			push.setTitle(pushContEntity.getPushTitle());
	    	push.setUrl(PushEntity.PUSH_type_mail);
	    	push.setType(PushEntity.PUSH_type_mail);
	    	push.setCreator(null);
	    	push.setSummary(content);
	    	push.setPushType(PushEntity.PUSH_pushType_person);
	    	push.setPushUserOid(userOid);
	    	push.setPushUserAcc(phone);
	    	push.setStatus(PushEntity.PUSH_status_on);
	    	push.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
	    	push.setPushTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
	    	push = this.save(push);
	    	log.info("后台请求推送：用户oid："+userOid+",手机："+phone+",推送保存成功！");
	    	if (clientId != null && !clientId.isEmpty()){
	    		this.push2Single(clientId, 0, "", pushContEntity.getPushTitle(), content, push.getUrl());
	    		this.push2Single(clientId, 1, "", pushContEntity.getPushTitle(), content, push.getUrl());
	    		log.info("后台请求推送：用户oid："+userOid+",手机："+phone+",推送已发送！clientId:"+clientId);
	    	}else{
	    		log.error("后台请求推送：用户oid："+userOid+",手机："+phone+",推送oid："+push.getOid()+",推送发送失败！clientId不存在！");
	    	}
		} catch (Exception e) {
			resp.setErrorCode(-1);
			resp.setErrorMessage(e.getMessage());
			log.error("后台请求推送：用户oid："+userOid+",推送发送失败！失败内容："+e.getMessage());
		}
		
		return resp;
	}
	
	// 个推组装发送
	/**
	 * @param clientId  终端clientId
	 * @param phoneType	0是安卓系统1是苹果系统
	 * @param type 个推类型
	 * @param title	标题
	 * @param content	内容
	 * @param url	链接
	 */
	private void push2Single(String clientId, int phoneType, String type, String title, String content, String url) {
		ITemplate template = null;
		if (phoneType == 0){
			if(type.equals("activity")){
	            template = linkTemplate(title, content, url);
	         }else{
	        	// 点击通知打开应用模板
	 			template = notificationTemplate(url, title, content);
	         }
			
		}else{
			// 透传模板，对ios使用透传推送
			template = transmissionTemplate(url, title, content);
		}
		
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		
		SingleMessage message = groupSingleMes(template);
		Target target = groupTarget(clientId);
   	 	
		IPushResult ret = null;
		try {
		    ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
		    e.printStackTrace();
		    ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
		    System.out.println(ret.getResponse().toString());
		} else {
		    System.out.println("服务器响应异常");
		}
	}
	
	// 个推组装发送
	/**
	 * @param labelCode  发送组
	 * @param phoneType	0是安卓系统1是苹果系统
	 * @param type 个推类型
	 * @param title	标题
	 * @param content	内容
	 * @param url	链接
	 */
	private void push2ToList(List<String> clientIds, int phoneType, String type, String title, String content, String url) {
		ITemplate template = null;
		if (phoneType == 0){
			if(type.equals("activity")){
	            template = linkTemplate(title, content, url);
	         }else{
	        	// 点击通知打开应用模板
	 			template = notificationTemplate(url, title, content);
	         }
			
		}else{
			// 透传模板，对ios使用透传推送
			template = transmissionTemplate(url, title, content);
		}
		
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		ListMessage message = groupMes(template);
		
		List<Target> targets = new ArrayList<Target>();
		if(clientIds!=null && !clientIds.isEmpty()){
			
			for(String clientId:clientIds){
				Target target = groupTarget(clientId);
				targets.add(target);
			}
	   	 	String taskId = push.getContentId(message);
			IPushResult ret = null;
			try {
			    ret = push.pushMessageToList(taskId, targets);
			} catch (RequestException e) {
			    e.printStackTrace();
			}
			if (ret != null) {
			    System.out.println(ret.getResponse().toString());
			} else {
			    System.out.println("服务器响应异常");
			}
		}
	}
	
	// 组装个推信息
	private ListMessage groupMes(ITemplate template) {
		ListMessage message = new ListMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINEEXPIRETIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        
		return message;
	}

	// 组装个推目标
	private Target groupTarget(String clientId) {
		// 设置接收目标
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);
        //target.setAlias(Alias);	// 设置别名
		return target;
	}

	// 组装个推信息
	private SingleMessage groupSingleMes(ITemplate template) {
		SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(OFFLINEEXPIRETIME);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        
		return message;
	}

}
