package com.guohuai.tulip.platform.event;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.mimosa.api.MimosaSdk;
import com.guohuai.mimosa.api.obj.MimosaSdkPages;
import com.guohuai.mimosa.api.obj.ProductSDKRep;
import com.guohuai.mimosa.api.obj.ProductSDKReq;
import com.guohuai.rules.config.BizRule;
import com.guohuai.rules.config.DroolsContainerHolder;
import com.guohuai.rules.config.RuleDefinition;
import com.guohuai.rules.config.RuleExpression;
import com.guohuai.rules.config.RuleGenerator;
import com.guohuai.rules.enums.AttrTypeEnum;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.component.api.InvestorLabelResp;
import com.guohuai.tulip.component.api.InvestorlabelAPI;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.couponRule.CouponRuleEntity;
import com.guohuai.tulip.platform.coupon.couponRule.CouponRuleService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisInfo;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.event.rule.EventRuleEntity;
import com.guohuai.tulip.platform.event.rule.EventRuleService;
import com.guohuai.tulip.platform.eventAnno.AuthenticationEvent;
import com.guohuai.tulip.platform.eventAnno.BearerEvent;
import com.guohuai.tulip.platform.eventAnno.BindingCardEvent;
import com.guohuai.tulip.platform.eventAnno.BirthdayEvent;
import com.guohuai.tulip.platform.eventAnno.CashEvent;
import com.guohuai.tulip.platform.eventAnno.CustomEvent;
import com.guohuai.tulip.platform.eventAnno.EmployeeEvent;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.eventAnno.FirstFriendInvestEvent;
import com.guohuai.tulip.platform.eventAnno.ForwardedEvent;
import com.guohuai.tulip.platform.eventAnno.FriendEvent;
import com.guohuai.tulip.platform.eventAnno.InvalidBidsEvent;
import com.guohuai.tulip.platform.eventAnno.InvestEvent;
import com.guohuai.tulip.platform.eventAnno.RechargeEvent;
import com.guohuai.tulip.platform.eventAnno.RedeemEvent;
import com.guohuai.tulip.platform.eventAnno.RefundEvent;
import com.guohuai.tulip.platform.eventAnno.RegisterEvent;
import com.guohuai.tulip.platform.eventAnno.ScheduleEvent;
import com.guohuai.tulip.platform.eventAnno.SignEvent;
import com.guohuai.tulip.platform.rule.RuleEntity;
import com.guohuai.tulip.platform.rule.RuleService;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemEntity;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemService;
import com.guohuai.tulip.platform.rule.ruleProp.RulePropEntity;
import com.guohuai.tulip.platform.rule.ruleProp.RulePropService;
import com.guohuai.tulip.util.Collections3;
import com.guohuai.tulip.util.DateUtil;
import com.guohuai.tulip.util.KeyRedisUtils;
import com.guohuai.tulip.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class EventService {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private EventRuleService eventRuleService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private RuleItemService ruleItemService;
	@Autowired
	RuleGenerator ruleGenerator;
	@Autowired
	RulePropService rulePropService;
	@Autowired
	CouponRuleService couponRuleService;
	@Autowired
	CouponService couponService;
	@Autowired
	UserCouponService userCouponService;
	
	@Autowired
	private MimosaSdk mimoSdk;
	
	@Autowired
	DroolsContainerHolder containerHolder;
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Autowired
	private InvestorlabelAPI investorlabelApi;
	
		/**
	 * 初始化加载规则活动
	 * 
	 * @return
	 */
	public List<BizRule> initEventList(){
		List<EventEntity> eventList = eventDao.initEventList();
		List<BizRule> list = new ArrayList<BizRule>();
		if (!CollectionUtils.isEmpty(eventList)) {// 存在活动的情况
			for (EventEntity eventEntity : eventList) {
				List<String> ruleIds = KeyRedisUtils.loadEventRuleList.get(eventEntity.getType());//.initLoadEventList.get(eventEntity.getType());
				if(ruleIds == null){
					ruleIds = new ArrayList<String>();
				}
				List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventEntity.getOid());
				if (!CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况,并加载规则
					BizRule bizRule = null;
					for (RuleEntity ruleEntity : ruleList) {
						bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType(ruleEntity.getType()).content(ruleEntity.getExpression()).build();
						list.add(bizRule);
						ruleIds.add(ruleEntity.getOid());
					}
				}
				KeyRedisUtils.loadEventRuleList.put(eventEntity.getType(), ruleIds);
				log.info("=====================初始化存入内存中的活动类型={}, 对应的规则数={}", eventEntity.getType(), ruleIds.size());
			}
		}
		
		int lis = KeyRedisUtils.loadEventRuleList.size();
		log.info("=================memory--" + JSONObject.toJSONString(lis));
		return list;
	}
	
	/**
	 * 获取卡券信息提示
	 * @param userId
	 * @return
	 */
	public List<UserCouponRedisInfo> checkMyCouponNow(String userId, String eventType){
		List<UserCouponRedisInfo> userCouponRedis = null;
		if(eventType.equals(EventConstants.EVENTTYPE_REGISTER)){
			userCouponRedis = UserCouponRedisUtil.lRange(redis, UserCouponRedisUtil.USER_NEW_COUPON_LOGIN_REDIS_KEY + userId);
			log.debug("===============用户={} 此时获取的卡券数量={} 这里的卡券来源注册、自定义领取等非app正常场景获取", userId, JSONObject.toJSONString(userCouponRedis));
			UserCouponRedisUtil.delcoup(redis, UserCouponRedisUtil.USER_NEW_COUPON_LOGIN_REDIS_KEY + userId);
		} else{
			userCouponRedis = UserCouponRedisUtil.lRange(redis, UserCouponRedisUtil.USER_NEW_COUPON_REDIS_KEY + userId);
			log.debug("===============用户={} 此时获取的卡券数量={} ", userId, JSONObject.toJSONString(userCouponRedis));
			UserCouponRedisUtil.delcoup(redis, UserCouponRedisUtil.USER_NEW_COUPON_REDIS_KEY + userId);
		}
		try {
			if(!Collections3.isEmpty(userCouponRedis)){
				Iterator<UserCouponRedisInfo> it = userCouponRedis.iterator();
				Timestamp currentDate = DateUtil.getSqlCurrentDate();
				while(it.hasNext()){
					UserCouponRedisInfo user = it.next();
					if(currentDate.getTime() > user.getFinish().getTime()){
						//当前日期>过期日期, 从当前用户弹屏卡券包中删除当前已过期的卡券
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			log.error("======================用户={} 获取卡券弹屏异常={}", userId, e);
		}
		return userCouponRedis;
	}
	
		/**
	 * 根据标签编码查询基本信息
	 * @param labelCode
	 * @return
	 */
	public String findLabelByCode(String labelCode){
		InvestorLabelResp resp = new InvestorLabelResp();
		try {
			resp = investorlabelApi.findLabelByCode(labelCode);
		} catch (Exception e) {
			resp.setLabelCode("X0X");
			resp.setLabelName("不可用标签");
			log.error("通过sdk 访问mimosa标签信息接口异常:{} ", e);
		}
		return resp.getLabelName();
	}
	
	/**
	 * 查询活动列表
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<EventQueryRep> query(Specification<EventEntity> spec, Pageable pageable) {
		Page<EventEntity> enchs = this.eventDao.findAll(spec, pageable);
		PageResp<EventQueryRep> pageResp = new PageResp<EventQueryRep>();

		for (EventEntity ench : enchs) {
			EventQueryRep rep = new EventQueryRep();
			rep.setOid(ench.getOid());
			rep.setTitle(ench.getTitle());
			rep.setType(ench.getType());
			rep.setStatus(ench.getStatus());
			rep.setDescription(ench.getDescription());
			rep.setStart(ench.getStart());
			rep.setFinish(ench.getFinish());
			rep.setActive(ench.getActive());
			rep.setCreateTime(ench.getCreateTime());
			rep.setCreateUser(ench.getCreateUser());
			rep.setIsdel(ench.getIsdel());
			rep.setUpdateTime(ench.getUpdateTime());
			rep.setUpdateUser(ench.getUpdateUser());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}
	/**
	 * 批量审核暂停/恢复活动
	 * @param type
	 * @param oids
	 * @return
	 */
	public BaseResp batchEventActive(String type,String[] oids) {
		BaseResp rep = new BaseResp();
		if (!ArrayUtils.isEmpty(oids)) {
			this.eventDao.activeOtherOn(oids);
			this.eventDao.activeOtherOff(oids);
		}else{
			this.eventDao.activeOffOtherAll();
		}
		return rep;
	}
	/**
	 * 活动上下架
	 * @param type
	 * @param oids
	 * @return
	 */
	public BaseResp eventActive(String formActive,String oid) {
		BaseResp rep = new BaseResp();
		String toActive=EventEntity.ACTIVE_OFF.equals(formActive)?EventEntity.ACTIVE_ON:EventEntity.ACTIVE_OFF;
		int num = this.eventDao.activeEvent(formActive,toActive,oid);
		if(num < 1){
			rep.setErrorCode(-1);
			rep.setErrorMessage("活动上下架异常!");
			throw new GHException("活动上下架异常!");
		}
		return rep;
	}
	/**
	 * 查询活动报表
	 * @return
	 */
	public EventDetailRep eventViewDetail() {
		EventDetailRep rep = new EventDetailRep();
		Integer eventCount=eventDao.eventCount();
		Integer isseCount=userCouponService.isseCount();
		Integer useCount=userCouponService.useCount();
		rep.setEventCount(eventCount);
		rep.setIsseCoupon(isseCount);
		rep.setUseCoupon(useCount);
		return rep;
	}
	/**
	 * 获取审核通过的活动集合
	 * @return
	 */
	public PageResp<EventQueryRep> getPassDataList() {
		List<EventEntity> list = this.eventDao.getPassDataList();
		PageResp<EventQueryRep> pageResp = new PageResp<EventQueryRep>();
		for (EventEntity en : list) {
			if(en.getTitle().length()>20){
				en.setTitle(en.getTitle().substring(0, 20)+"...");
			}
			EventQueryRep rep = new EventQueryRep();
			BeanUtils.copyProperties(en, rep);
			pageResp.getRows().add(rep);
		}
		return pageResp;
	}

	/**
	 * 获取审核通过的自定义活动集合
	 * @return
	 */
	public PageResp<EventQueryRep> getPassCustomList() {
		List<EventEntity> list = this.eventDao.getPassCustomList();
		PageResp<EventQueryRep> pageResp = new PageResp<EventQueryRep>();
		for (EventEntity en : list) {
			if(en.getTitle().length()>20){
				en.setTitle(en.getTitle().substring(0, 20)+"...");
			}
			EventQueryRep rep = new EventQueryRep();
			BeanUtils.copyProperties(en, rep);
			pageResp.getRows().add(rep);
		}
		return pageResp;
	}
	/**
	 * 审核活动
	 * @param oid
	 * @param status
	 * @return
	 */
	public BaseResp checkEvent(String oid, String status) {
		BaseResp rep = new BaseResp();
		EventEntity entity = this.eventDao.findOne(oid);
		if(status.equals(EventEntity.STATUS_PASSED)){
			if(DateUtil.compare_current_(entity.getStart())&&DateUtil.compare_current(entity.getFinish())){
				entity.setActive(EventEntity.ACTIVE_ON);
			}	
		}
		entity.setStatus(status);
		this.eventDao.save(entity);
		return rep;
	}
	/**
	 * 删除活动规则
	 * @param oid
	 * @return
	 */
	public BaseResp deleteEventRule(String oid) {
		BaseResp rep = new BaseResp();
		List<EventRuleEntity> list = this.eventRuleService.listEventRuleEntityByEID(oid);
		if (!CollectionUtils.isEmpty(list)) {
			String[] rid = Collections3.extractToString(list, "ruleId", ",").split(",");
			eventRuleService.deleteEventRule(oid);
			ruleService.deleteRule(rid);
			ruleItemService.deleteRuleItem(rid);
		}
		return rep;
	}
	/**
	 * 删除活动和活动相关的规则
	 * @param oid
	 * @return
	 */
	public BaseResp deleteEvent(String oid) {
		BaseResp rep = new BaseResp();
		eventDao.deleteEvent(oid);
		deleteEventRule(oid);
		return rep;
	}
	/**
	 * 保存活动
	 * @param entity
	 * @param operator
	 * @return
	 */
	public BaseResp saveEvent(EventAddPojo entity, String operator) {
		BaseResp br = new BaseResp();
		try{
			EventEntity eventEntity = saveOrUpdateEvent(entity, operator);
			for (RewardRuleReq rr : entity.getRrList()) {
				RuleEntity ruleEntity = saveRule(rr.getWeight(), operator,eventEntity,rr);
				saveEventRule(eventEntity.getOid(), ruleEntity.getOid(), operator);
				saveRuleItem(ruleEntity.getOid(), rr.getPropList());
				saveCouponRule(ruleEntity.getOid(), rr.getCouponOids(), operator);
			}
		}catch(GHException e){
			e.printStackTrace();
			br.setErrorCode(-1);
			br.setErrorMessage("保存活动失败!");
		}
		
		
		return br;
	}
	/**
	 * 新增或修改活动
	 * @param rsr
	 * @param operator
	 * @return
	 */
	public EventEntity saveOrUpdateEvent(EventAddPojo rsr, String operator) {
		Timestamp currtime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		EventEntity eventEntity = null;
		if (StringUtils.isNotBlank(rsr.getOid())) {
			deleteEventRule(rsr.getOid());
			eventEntity = eventDao.findOne(rsr.getOid());
			eventEntity.setTitle(rsr.getTitle());
			eventEntity.setDescription(rsr.getContent());
			eventEntity.setStart(rsr.getStart());
			eventEntity.setFinish(rsr.getFinish());
			eventEntity.setUpdateUser(operator);
			eventEntity.setType(rsr.getType());
			eventEntity.setUpdateTime(currtime);
		} else {
			eventEntity = new EventEntity();
			eventEntity.setTitle(rsr.getTitle());
			eventEntity.setDescription(rsr.getContent());
			eventEntity.setStart(rsr.getStart());
			eventEntity.setFinish(rsr.getFinish());
			eventEntity.setCreateUser(operator);
			eventEntity.setUpdateUser(operator);
			eventEntity.setType(rsr.getType());
			eventEntity.setIsdel("no");
		}
		return eventDao.save(eventEntity);
	}
	/**
	 * 保存规则
	 * @param weight
	 * @param operator
	 * @param eventEntity
	 * @param rrEntity
	 * @return
	 */
	private RuleEntity saveRule(String weight, String operator,EventEntity eventEntity,RewardRuleReq rrEntity) {
		Timestamp currtime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		RuleEntity ruleEntity = new RuleEntity();
		ruleEntity.setType(RuleEntity.RULE_TYPE_GET);
		ruleEntity.setWeight(weight);
		ruleEntity.setCreateTime(currtime);
		ruleEntity.setActionName(rrEntity.getActionName());
		ruleEntity=ruleService.createRule(ruleEntity);
		String expression=getExpression(ruleEntity.getOid(),weight,eventEntity,rrEntity);
		ruleEntity.setExpression(expression);
		return ruleService.createRule(ruleEntity);
	}
	/**
	 * 保存规则项
	 * @param ruleOid
	 * @param list
	 */
	private void saveRuleItem(String ruleOid, List<PropRep> list) {
		RuleItemEntity ruleItem = null;
		for (PropRep pr : list) {
			ruleItem = new RuleItemEntity();
			ruleItem.setRuleId(ruleOid);
			ruleItem.setPropId(pr.getOid());
			ruleItem.setValue(pr.getValue());
			ruleItem.setExpression(pr.getExpression());
			ruleItemService.createRuleItem(ruleItem);
		}

	}
	/**
	 * 保存事件规则关系
	 * @param eId
	 * @param rId
	 * @param operator
	 */
	private void saveEventRule(String eId, String rId, String operator) {
		Timestamp currtime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		EventRuleEntity eventRuleEntity = new EventRuleEntity();
		eventRuleEntity.setEventId(eId);
		eventRuleEntity.setRuleId(rId);
		eventRuleEntity.setCreateTime(currtime);
		eventRuleService.createEventRule(eventRuleEntity);
	}
	/**
	 * 保存卡券规则关系
	 * @param ruleId
	 * @param couponEntityList
	 * @param operator
	 */
	private void saveCouponRule(String ruleId, List<CouponEntity> couponEntityList, String operator) {
		Timestamp currtime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		CouponRuleEntity couponRuleEntity = null;
		for (CouponEntity couponEntity : couponEntityList) {
			couponRuleEntity = new CouponRuleEntity();
			couponRuleEntity.setCouponId(couponEntity.getOid());
			couponRuleEntity.setRuleId(ruleId);
			couponRuleEntity.setCreateTime(currtime);
			couponRuleService.save(couponRuleEntity);
		}
	}
	/**
	 * 获取活动信息
	 * @param oid
	 * @return
	 */
	public EventAddPojo getEventInfo(String oid) {
		EventAddPojo rsr = new EventAddPojo();
		EventEntity eventEntity = eventDao.getOne(oid);
		List<EventRuleEntity> eventRuleList = eventRuleService.listEventRuleEntityByEID(oid);
		List<RewardRuleReq> rrList = new ArrayList<RewardRuleReq>();
		RewardRuleReq rr = null;
		for (EventRuleEntity eventRuleEntity : eventRuleList) {
			rr = new RewardRuleReq();
			RuleEntity ruleEntity = ruleService.findRuleByOid(eventRuleEntity.getRuleId());
			List<RuleItemEntity> ruleItemList = ruleItemService.listRuleItemEntityByRID(ruleEntity.getOid());
			List<CouponRuleEntity> couponRuleList = couponRuleService.listCouponRuleEntityByRID(ruleEntity.getOid());
			List<PropRep> propList = new ArrayList<PropRep>();
			List<CouponEntity> couponOids = new ArrayList<CouponEntity>();
			PropRep propRep = null;
			for (RuleItemEntity ruleItemEntity : ruleItemList) {
				propRep = new PropRep();
				propRep.setOid(ruleItemEntity.getPropId());
				propRep.setValue(ruleItemEntity.getValue());
				propRep.setExpression(ruleItemEntity.getExpression());
				propList.add(propRep);
			}
			for (CouponRuleEntity couponRuleEntity : couponRuleList) {
				CouponEntity couponEntity = couponService.findCouponByOid(couponRuleEntity.getCouponId());
				if (couponEntity != null) {
					couponOids.add(couponEntity);
				} 
			}
			rr.setCouponOids(couponOids);
			rr.setPropList(propList);
			rr.setWeight(ruleEntity.getWeight());
			rr.setOid(ruleEntity.getOid());
			rrList.add(rr);
		}
		rsr.setRrList(rrList);
		rsr.setContent(eventEntity.getDescription());
		rsr.setFinish(eventEntity.getFinish());
		rsr.setOid(eventEntity.getOid());
		rsr.setStart(eventEntity.getStart());
		rsr.setTitle(eventEntity.getTitle());
		rsr.setType(eventEntity.getType());
		return rsr;
	}

	
	/**
	 * 获取活动详述
	 * @param oid
	 * @return
	 */
	public Map<String, Object> getEventDetail(String oid) {
		EventEntity eventEntity = eventDao.getOne(oid);
		List<EventRuleEntity> eventRuleList = eventRuleService.listEventRuleEntityByEID(oid);
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (EventRuleEntity eventRuleEntity : eventRuleList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			RuleEntity ruleEntity = ruleService.findRuleByOid(eventRuleEntity.getRuleId());
			List<RuleItemEntity> ruleItemList = ruleItemService.listRuleItemEntityByRID(ruleEntity.getOid());
			List<CouponRuleEntity> couponRuleList = couponRuleService.listCouponRuleEntityByRID(ruleEntity.getOid());
			List<String> jlfsList = new ArrayList<String>();
			List<String> jlmkList = new ArrayList<String>();
			for (RuleItemEntity ruleItemEntity : ruleItemList) {
				RulePropEntity rulePropEntity = rulePropService.findRulePropByOid(ruleItemEntity.getPropId());
				if (rulePropEntity != null) {
					String jlmk = rulePropEntity.getName() + ruleItemEntity.getExpression()+ ruleItemEntity.getValue() + rulePropEntity.getUnitvalue();
					if (rulePropEntity.getUnit().equals("interval")) {
						jlmk = rulePropEntity.getName() + ":" + ruleItemEntity.getValue()+ rulePropEntity.getUnitvalue();
					} else if(rulePropEntity.getUnit().equals("product")){
						String name = findProductName(ruleItemEntity.getValue());
						jlmk = rulePropEntity.getName() + "=" + name + rulePropEntity.getUnitvalue();
					} else if(rulePropEntity.getUnit().equals("group")){
						//用户组，后续扩展
						String name = "";//this.findLabelByCode(ruleItemEntity.getValue());
						jlmk = rulePropEntity.getName() + "=" + name + rulePropEntity.getUnitvalue();
					}
					jlmkList.add(jlmk);
				}
			}
			for (CouponRuleEntity couponRuleEntity : couponRuleList) {
				CouponEntity couponEntity = couponService.findCouponByOid(couponRuleEntity.getCouponId());
				if (null != couponEntity) {
					jlfsList.add(toCouponStr(couponEntity));
				}
			}
			if(null != ruleEntity.getActionName() && !ruleEntity.getActionName().equals("couponAction")){
				jlfsList.add("赠送电影票");
			}
			resultMap.put("jlmkList", jlmkList);
			resultMap.put("jlfsList", jlfsList);
			resultMap.put("qzlx", RuleEntity.WEIGHT_AND.equals(ruleEntity.getWeight()) ? "全部规则" : "任一规则");
			listMap.add(resultMap);
		}
		map.put("jltj", listMap);
		map.put("content", eventEntity.getDescription());
		map.put("title", eventEntity.getTitle());
		map.put("createUser", eventEntity.getCreateUser());
		map.put("createTime", eventEntity.getCreateTime());
		map.put("updateUser", eventEntity.getUpdateUser());
		map.put("updateTime", eventEntity.getUpdateTime());
		map.put("start", eventEntity.getStart());
		map.put("finish", eventEntity.getFinish());
		map.put("status", eventEntity.getStatus());
		map.put("active", eventEntity.getActive());
		map.put("type", eventEntity.getType());
		return map;
	}
	
	public String findProductName(String oid){
		String name = "";
		try {
			ProductSDKReq req=new ProductSDKReq();
			req.setOid(oid);
			MimosaSdkPages<ProductSDKRep> rep=mimoSdk.queryProductList(req);
			List<ProductSDKRep> productList=rep.getRows();
			name = productList.get(0).getName();
		} catch (Exception e) {
			log.info("查看活动详情， 规则【产品id】 异常 ！ ");
		}
		
		return name;
	}
	
	/**
	 * 卡券code转换
	 * @param couponEntity
	 * @return
	 */
	private String toCouponStr(CouponEntity couponEntity) {
		String type = couponEntity.getType();
		String prefix = "";
		if (!StringUtils.isBlank(type)) {
			switch (type) {
			case CouponEntity.COUPON_TYPE_coupon:
				prefix = "赠送代金券:";
				break;
			case CouponEntity.COUPON_TYPE_rateCoupon:
				prefix = "赠送加息券:";
				break;
			case CouponEntity.COUPON_TYPE_redPackets:
				prefix = "赠送红包:";
				break;
			case CouponEntity.COUPON_TYPE_tasteCoupon:
				prefix = "赠送体验金:";
				break;
			case CouponEntity.COUPON_TYPE_cashCoupon:
				prefix = "赠送提现券:";
				break;
			case CouponEntity.COUPON_TYPE_pointsCoupon:
				prefix = "赠送积分券:";
				break;
			default:
				break;
			}
		}
		return prefix + couponEntity.getName();
	}
	
	/**
	 * 生成规则模版
	 * @param ruleId
	 * @param weithg
	 * @param eventEntity
	 * @param rrEntity
	 * @return
	 */
	public String getExpression(String ruleId,String weithg,EventEntity eventEntity,RewardRuleReq rrEntity){
		String content=null;
		BaseEvent event=returnEventByType(eventEntity.getType());
		List<RuleExpression> reList=new ArrayList<RuleExpression>();
		RuleExpression exprS=null;
		RuleExpression exprE=null;
		for(PropRep rr : rrEntity.getPropList()){
			RulePropEntity ruleProp=rulePropService.findRulePropByOid(rr.getOid());
			//数值类型或浮点类型
			if(RulePropEntity.UNIT_NUMBER.equals(ruleProp.getUnit()) || RulePropEntity.UNIT_DOUBLE.equals(ruleProp.getUnit())){
				exprS=new RuleExpression();
				exprS.setType(AttrTypeEnum.NUMBER.getCode());
				exprS.setAttr(ruleProp.getField());
				exprS.setOp(rr.getExpression());
				exprS.setValue(rr.getValue());
				reList.add(exprS);
			}else if(RulePropEntity.UNIT_INTERVAL.equals(ruleProp.getUnit())){//区间类型
				String[] value=rr.getValue().replace("(", "").replace("]", "").split(",");
				RuleExpression exprN=new RuleExpression();
				StringBuffer exprBuffer = new StringBuffer();
				//起始值
				exprBuffer.append(ruleProp.getField());
				exprBuffer.append(">"); //old:>=
				exprBuffer.append(value[0]);
				
				exprBuffer.append("&&");
				//结束值
				exprBuffer.append(ruleProp.getField());
				exprBuffer.append("<=");
				exprBuffer.append(value[1]);
				//最终结果eg:firstInverst>=xx&&firstInverst<=yy
				exprN.setType(AttrTypeEnum.NUMBER.getCode());
				exprN.setAttr(exprBuffer.toString());
				exprN.setOp("");
				exprN.setValue("");
				
				reList.add(exprN);
			}else if(RulePropEntity.UNIT_PRODUCT.equals(ruleProp.getUnit())){//区间类型
				exprS=new RuleExpression();
				exprS.setType(AttrTypeEnum.STR.getCode());
				exprS.setAttr(ruleProp.getField());
				exprS.setOp(rr.getExpression());
				exprS.setValue(rr.getValue());
				reList.add(exprS);
			}else if("group".equals(ruleProp.getUnit())){//区间类型
				exprS=new RuleExpression();
				exprS.setType(AttrTypeEnum.STR.getCode());
				exprS.setAttr(ruleProp.getField());
				exprS.setOp(" " + rr.getExpression() + " "); //field contains value
				exprS.setValue(rr.getValue());
				reList.add(exprS);
			}
			
		}
		@SuppressWarnings("unchecked")
		List<String> couponIds = Collections3.extractToList(rrEntity.getCouponOids(), "oid");
		RuleDefinition ruleDef = RuleDefinition.builder().oid(ruleId).ruleOpt(weithg.equals(RuleEntity.WEIGHT_AND)
                ? RuleEntity.WEIGHT_AND_EXPRESSION : RuleEntity.WEIGHT_OR_EXPRESSION)
				.ruleSet(reList).action(rrEntity.getActionName()).actionParams(StringUtils.join(couponIds, ",")).build();

		InputStream input = this.getClass().getResourceAsStream("/rules.drt");
		content = this.ruleGenerator.applyRuleTemplate(event, ruleDef, input);
		return content;
	}
	/**
	 * 根据活动类型返回活动实体
	 * @param type
	 * @return
	 */
	public BaseEvent returnEventByType(String type) {
        switch (type) {
        case "sign":
            return new SignEvent();
        case "register":
            return new RegisterEvent();
        case "investment":
            return new InvestEvent();
        case "authentication":
        	return new AuthenticationEvent();
        case "redeem":
        	return new RedeemEvent();
        case "bearer":
            return new BearerEvent();
        case "cash":
            return new CashEvent();
        case "refund":
            return new RefundEvent();
        case "firstFriendInvest":
            return new FirstFriendInvestEvent();
        case "forwarded":
            return new ForwardedEvent();
        case "friend":
            return new FriendEvent();
        case "recharge":
            return new RechargeEvent();
        case "bindingCard":
            return new BindingCardEvent();
        case "custom":
            return new CustomEvent();
        case "invalidBids":
            return new InvalidBidsEvent();
        case "birthday":
            return new BirthdayEvent();
        case "schedule":
            return new ScheduleEvent();
        case "employee":
            return new EmployeeEvent();
    	default:
            break;
        }
        return null;
	}
	/**
	 * 活动自动上架
	 */
	public void autoOnEvent(){
		this.eventDao.autoOnEvent();
	}
	/**
	 * 根据活动类型查询活动集合
	 * @param string
	 * @return
	 */
	public List<EventEntity> eventListByType(String type) {
		List<EventEntity> eventList = this.eventDao.eventListByType(type);
		return eventList;
	}
	
	/**
	 * 根据活动Oid查询活动
	 * @param eventId
	 * @return
	 */
	public int findEventByOid(String eventId) {
		return this.eventDao.getPassCustomEventById(eventId);
	}
	
	/**
	 * 根据活动类型和卡券类型查询活动
	 * @param eventType
	 * @param couponType
	 * @return
	 */
	public List<Object[]> getEventCouponInfo(String eventType, String couponType) {
		List<Object[]> list= this.eventDao.getEventCouponInfo(eventType, couponType);
		return list;
	}
	
	/**
	 * 根据活动类型查询活动
	 * @param eventType
	 * @return
	 */
	public List<EventEntity> findEventCouponInfo(String eventType) {
		 
		return this.eventDao.queryEventCouponInfo(eventType);
	}
	
	/**
	 * 根据金额和卡券类型查询活动
	 * @param money
	 * @param couponType
	 * @return
	 */
	public EventEntity getCustomEventId(String money, String couponType) {
		EventEntity entity = this.eventDao.getCustomEventId(money, couponType);
		return entity;
	}
	public EventEntity findCustomEventByOid(String eventId) {
		EventEntity entity = this.eventDao.findCustomEventByOid(eventId);
		if(null == entity){
			log.info("自定义活动异常!eventId={}",eventId);
			throw new GHException("自定义活动异常!");
		}
		return entity;
	}
	
	/**
	 * 获取当前活动需要下发的卡券的剩余可用数量（[0]=卡券id、[1]=卡券名称、[2]卡券类型、[3]卡券剩余数量）
	 * @param eventId
	 * @return
	 */
	public List<Object[]> findCouponInfoByEventId(String eventId) {
		List<Object[]> list= this.eventDao.queryCouponBatchByEventId(eventId, EventConstants.EVENTTYPE_CUSTOM);
		return list;
	}
	
	/**
	 *	检查活动标题是否重复
	 * 
	 * @param title
	 * @param oid
	 * @return
	 */
	public BaseResp checkOnlyEventTitle(String title, String oid){
		BaseResp resp = new BaseResp();
		int count = 0;
		if(StringUtil.isEmpty(oid)){
			count = eventDao.findEventByTitle(title);			
		}else{
			count = eventDao.findEventByTitleOid(title, oid);
		}
		
		if(count == 0){
			resp.setErrorCode(0);
			resp.setErrorMessage(null);
		}else{
			resp.setErrorCode(-1);
			resp.setErrorMessage("活动标题不能重复");
		}
		
		return resp;
	}
}
