package com.guohuai.tulip.platform.coupon.sms;

import java.math.BigDecimal;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.tulip.component.api.SendCmsAPI;
import com.guohuai.tulip.platform.coupon.enums.MessageTypeEnum;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户获得卡券后的消息提醒service
 * 
 * @author mr_gu
 *
 */
@Slf4j
@Service
@Transactional
public class MessageService {
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Value("${async.pool.size:5}")
	int msgAsyncPoolSize;
	
	@Autowired
	private SendCmsAPI sendCmsAPI;
	
	@Autowired
	private UserInvestService userInvestService;
	
	@Bean(name = "msgAsyncTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(
				"async-pool-%d").build();
		return Executors.newFixedThreadPool(this.msgAsyncPoolSize, factory);
	}

	/**
	 * 异步消息提醒入口
	 * @param req
	 */
	@Async("msgAsyncTaskExecutor")
	@EventListener
	public void sendMessge(MessageReq req){
		try {
			this.sendMail(req);
		} catch (Exception e) {
			log.error("==================卡券到账提醒异常：{}", e.getMessage());
		}
	}
	
	/**
	 * 发送站内信
	 * @param req
	 */
	public void sendMail(MessageReq req){
		log.info("===========触发卡券到账站内信提醒========{}", JSONArray.toJSONString(req));
		/**
		 * 注册代金券		代金券到账提醒	恭喜您已成功注册！已将{num}元的代金券送达您的账户，快去“我的-卡券包”看看吧！
		 */
		if(req.getEventType().equals(EventConstants.EVENTTYPE_REGISTER) && req.getCouponType().equals(MessageReq.COUPONTYPE_COUPON)){
			this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.registerCoupon.toString(), this.assembleMsgParams(req.getCouponAmount()));
			log.debug("===============注册代金券		代金券到账提醒发送成功==========");
			return;
		}
		
		/**
		 * 首投红包		红包到账提醒	恭喜您投资成功！{num}元的“首投红包”已送达您的账户，快去“我的-卡券包”看看吧！
		 */
		if(req.getEventType().equals(EventConstants.EVENTTYPE_INVESTMENT) && ("fixed".equals(req.getCouponType()) || "random".equals(req.getCouponType()))){
			
			log.debug("============用户={}, 投资获得红包， 判断是否为首次投资", req.getUserId());
			UserInvestEntity userInvestEntity = new UserInvestEntity();
			String data = UserCouponRedisUtil.getStr(redis, req.getUserId());
			if(StringUtil.isEmpty(data)){
				userInvestEntity = userInvestService.findUserInvestByUserId(req.getUserId());
			}else {
				userInvestEntity = JSONObject.parseObject(data, UserInvestEntity.class);
			}
			log.debug("============用户={}, 累计投资次数={}, 首次投资金额={}", userInvestEntity.getUserId(), userInvestEntity.getInvestCount(), userInvestEntity.getFirstInvestAmount());
			if(userInvestEntity.getFirstInvestAmount().compareTo(BigDecimal.ZERO) == 0){
				if("fixed".equals(req.getCouponType())){
					this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.firstFixedRed.toString(), this.assembleMsgParams(req.getCouponAmount()));
					log.debug("===============首投红包		红包到账提醒发送成功==========");
					return;
				}else if("random".equals(req.getCouponType())){
					this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.firstRandomRed.toString(), "");
					log.debug("===============首投红包		红包到账提醒发送成功==========");
					return;
				}
			}
		}
		
		/**
		 * 投资体验金		收到投资体验金	好消息！您已获得{num}元投资体验卡，快去“我的-卡券包”看看吧！
		 */
		if(req.getCouponType().equals(MessageReq.COUPONTYPE_TASTECOUPON)){
			this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.investTestCoupon.toString(), this.assembleMsgParams(req.getCouponAmount()));
			log.debug("===============投资体验金		收到投资体验金提醒发送成功==========");
			return;
		}
		
		/**
		 * 邀请红包		红包到账提醒	您已成功邀请1个好友投资，收到1个{num}元的奖励红包，回「佳」理财，邀请好友送不停，再接再厉哦！
		 */
		if(req.getEventType().equals(EventConstants.EVENTTYPE_FRIEND) || req.getEventType().equals(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST)){
			if("fixed".equals(req.getCouponType())){
				this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.inviterFixedRed.toString(), this.assembleMsgParams(req.getCouponAmount()));
				log.debug("===============邀请红包		红包到账提醒发送成功==========");
				return;
			}else if("random".equals(req.getCouponType())){
				this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.inviterRandomRed.toString(), "");
				log.debug("===============邀请红包		红包到账提醒发送成功==========");
				return;
			}
		}
		
		/**
		 * 加息券		加息券到账提醒	好消息！您收到一份{num}%的加息券，快去“我的-卡券包”看看吧！
		 */
		if(req.getCouponType().equals(MessageReq.COUPONTYPE_RATECOUPON)){
			this.sendCmsAPI.sendMail(req.getUserId(), MessageTypeEnum.raiseRateCoupon.toString(), this.assembleMsgParams(req.getCouponAmount()));
			log.debug("===============加息券		加息券到账提醒发送成功==========");
			return;
		}
		
	}
	
	/**
	 * 发送推送
	 * @param req
	 */
	public void sendPush(MessageReq req){
//		//发推送
//		try {
//			if("redPackets".equals(userCouponRep.getType())){
//				orep = this.cmsApi.sendPush(userCouponRep.getUserId(), userCouponRep.getAmountType(), msgUtil.assembleMsgParams(userCouponRep.getAmount()));
//			}else{
//				orep = this.cmsApi.sendPush(userCouponRep.getUserId(), userCouponRep.getType(), msgUtil.assembleMsgParams(userCouponRep.getAmount()));		
//			}
//			log.debug("==========卡券即将过期提醒 为用户：{} 发送推送返回结果： {}", userCouponRep.getUserId(), orep);
//		} catch (Exception e) {
//			log.error("卡券即将过期提醒异常： {}", e.getMessage());
//		}
//		
//		try {
//			//红包过期发短信
//			if("redPackets".equals(userCouponRep.getType())){
//				if("fixed".equals(userCouponRep.getAmountType())){
//					orep = this.sMSUtils.sendSMS(userCouponRep.getPhone(), userCouponRep.getAmountType(), JSON.parseObject(msgUtil.assembleMsgParams(userCouponRep.getAmount(), DateUtil.format(userCouponRep.getFinish().getTime(), "yyyy-MM-dd HH:mm")), String[].class));				
//				}else{
//					orep = this.sMSUtils.sendSMS(userCouponRep.getPhone(), userCouponRep.getAmountType(), JSON.parseObject(msgUtil.assembleMsgParams(userCouponRep.getAmount(), DateUtil.format(userCouponRep.getFinish().getTime(), "yyyy-MM-dd HH:mm")), String[].class));				
//				}
//				log.debug("红包即将过期提醒 为用户：{} 发送短信返回结果： {}", userCouponRep.getUserId(), orep);
//			}
//		} catch (Exception e) {
//			log.error("红包即将过期短信提醒异常： {}", e.getMessage());
//		}
	}
	
	public String assembleMsgParams(Object... obj) {
		
		StringBuilder sb = new StringBuilder("[");
		for (Object tmp : obj) {
			if (tmp instanceof String) {
				sb.append("\"").append((String)tmp).append("\"").append(",");
			} else if (tmp instanceof BigDecimal) {
				sb.append("\"").append(((BigDecimal)tmp).toString()).append("\"").append(",");;
			} else if (tmp instanceof java.sql.Timestamp) {
				sb.append("\"").append(com.guohuai.basic.common.DateUtil.format((java.sql.Timestamp)tmp, com.guohuai.basic.common.DateUtil.datetimePattern)).append("\"").append(",");;
			}
				
		}
		if (sb.length() >= 2) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	
}
