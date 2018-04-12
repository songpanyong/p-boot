package com.guohuai.tulip.platform.facade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.guohuai.tulip.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.mvel2.MVEL;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.rules.config.BizRule;
import com.guohuai.rules.config.DroolsContainerHolder;
import com.guohuai.rules.event.AsyncBaseEvent;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.component.api.InvestorInfoResp;
import com.guohuai.tulip.component.api.InvestorLabelResp;
import com.guohuai.tulip.component.api.InvestorlabelAPI;
import com.guohuai.tulip.enums.ErrorCodeEnum;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderService;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeEntity;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponEntity;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.event.EventEntity;
import com.guohuai.tulip.platform.event.EventService;
import com.guohuai.tulip.platform.eventAnno.AuthenticationEvent;
import com.guohuai.tulip.platform.eventAnno.BearerEvent;
import com.guohuai.tulip.platform.eventAnno.BindingCardEvent;
import com.guohuai.tulip.platform.eventAnno.CashEvent;
import com.guohuai.tulip.platform.eventAnno.CustomEvent;
import com.guohuai.tulip.platform.eventAnno.EmployeeEvent;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.eventAnno.FirstFriendInvestEvent;
import com.guohuai.tulip.platform.eventAnno.FriendEvent;
import com.guohuai.tulip.platform.eventAnno.InvalidBidsEvent;
import com.guohuai.tulip.platform.eventAnno.InvestEvent;
import com.guohuai.tulip.platform.eventAnno.RechargeEvent;
import com.guohuai.tulip.platform.eventAnno.RedeemEvent;
import com.guohuai.tulip.platform.eventAnno.RefundEvent;
import com.guohuai.tulip.platform.eventAnno.RegisterEvent;
import com.guohuai.tulip.platform.eventAnno.SignEvent;
import com.guohuai.tulip.platform.facade.obj.CouponInterestRep;
import com.guohuai.tulip.platform.facade.obj.CouponInterestReq;
import com.guohuai.tulip.platform.facade.obj.EventRep;
import com.guohuai.tulip.platform.facade.obj.EventReq;
import com.guohuai.tulip.platform.facade.obj.MyCouponRep;
import com.guohuai.tulip.platform.facade.obj.MyCouponReq;
import com.guohuai.tulip.platform.facade.triggerevent.SceneAction;
import com.guohuai.tulip.platform.rule.RuleEntity;
import com.guohuai.tulip.platform.rule.RuleService;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemEntity;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemService;
import com.guohuai.tulip.platform.signin.SignInEntity;
import com.guohuai.tulip.platform.signin.SignInService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestRep;
import com.guohuai.tulip.platform.userinvest.UserInvestService;
import com.guohuai.tulip.schedule.notify.AsynchEventEntity;
import com.guohuai.tulip.schedule.notify.AsynchEventService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class FacadeService {

	@Value("${async.pool.size:10}")
	int asyncPoolSize;
	@Value("${event.async:false}")
	boolean eventAsync;
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	DroolsContainerHolder containerHolder;
	@Autowired
	private CouponService couponService;
	@Autowired
	private UserCouponService userCouponService;
	@Autowired
	private CouponOrderService couponOrderService;
	@Autowired
	private RuleItemService ruleItemService;
	@Autowired
	private CouponRangeService couponRangeService;
	@Autowired
	private EventService eventService;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private FacadeNewService facadeNewService;
	@Autowired
	private SignInService signInService;
	@Autowired
	private UserInvestService userInvestService;
	@Autowired
	private AsynchEventService asynchEventService;
	@Autowired
	private InvestorlabelAPI investorlabelApi;
	@Autowired
	ApplicationContext context;
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	 static Comparator<MyCouponRep> myCouponSortByASC=new Comparator<MyCouponRep>(){
		 @Override
       public int compare(MyCouponRep o1, MyCouponRep o2) {
			 if(o1.getFinish().getTime() < o2.getFinish().getTime()){
				 return -1;
			 }else if(o1.getFinish().getTime() > o2.getFinish().getTime()){
				 return 1;
			 }
			 return 0;
       }
	};
	/**
	 * 查询用户申购可用卡券列表
	 *
	 * @param myCouponReq
	 * @return
	 */
	public PageResp<MyCouponRep> getCouponList(MyCouponReq myCouponReq) {
		log.info("[tulip] 查询可使用卡券列表getCouponList, request {}", JSONObject.toJSONString(myCouponReq));
		PageResp<MyCouponRep> pageResp = new PageResp<MyCouponRep>();
		List<UserCouponEntity> enchs = this.userCouponService.getCouponList(myCouponReq.getUserId());// 查找所有卡券
//		Set<UserCouponEntity> returnList = new HashSet<UserCouponEntity>();//如果一次性发放多张相同的卡券，则oid除外其他字段值都相同，set会判断是重复的
		List<UserCouponEntity> returnList = new ArrayList<UserCouponEntity>();
		if (CollectionUtils.isEmpty(enchs)) {
			pageResp.setTotal(0);
			return pageResp;
		}
		UserInvestEntity user = userInvestService.findUserInvestByUserId(myCouponReq.getUserId());
		for (UserCouponEntity userCouponEntity : enchs) {
			log.debug("[tulip] 查询可使用卡券列表getCouponList, 待过滤的用户卡券  {} ，过滤条件包括：产品标签是否设置 或在范围内、卡券使用规则是否设置或满足规则", JSONObject.toJSONString(userCouponEntity));
			if (!CollectionUtils.isEmpty(myCouponReq.getLabelCodes())) {
				List<CouponRangeEntity> couponRangeList = couponRangeService.findByCouponId(userCouponEntity.getCouponBatch());// 是否卡券为全场使用
				if (!CollectionUtils.isEmpty(couponRangeList)) {
					//产品标签、具体产品都在一起；二选一，因此要么就是一条具体产品，要么就是多条产品标签
					//1.分开产品标签和具体产品
					List<CouponRangeEntity> productLabelList = new ArrayList<CouponRangeEntity>(10);
					List<CouponRangeEntity> productList = new ArrayList<CouponRangeEntity>(10);
					for (CouponRangeEntity couponRangeEntity : couponRangeList) {
						if(CouponRangeEntity.PRODUCT_TYPE_LABEL.equals(couponRangeEntity.getType())){
							productLabelList.add(couponRangeEntity);
						} else {
							productList.add(couponRangeEntity);
						}
					}
					
					//2.判断产品标签范围
					for (String labelCode : myCouponReq.getLabelCodes()) {
						if (Collections3.extractToMap(productLabelList, "labelCode", "labelCode").containsKey(labelCode)) {// 剔除不在产品使用范围内的卡券
							log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券 {} 在产品标签范围内使用, 计入可使用列表，但是需要继续判断使用规则", userCouponEntity.getCouponBatch());
							checkCouponUseRule(returnList, userCouponEntity, user);
						}
					}
					/** 目前产品标签、具体产品是二选一radio 后续再考虑 多选情况 **/
					//3.判断具体产品
					if(null != productList && productList.size() > 0 && !StringUtil.isEmpty(myCouponReq.getProductId())){
						//在具体产品内
						if (Collections3.extractToMap(productList, "labelCode", "labelCode").containsKey(myCouponReq.getProductId())) {
							log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券 {} 在产品标签范围内使用, 计入可使用列表，但是需要继续判断使用规则", userCouponEntity.getCouponBatch());
							checkCouponUseRule(returnList, userCouponEntity, user);
						}
					}
				} else {
					checkCouponUseRule(returnList, userCouponEntity, user);
				}
			}else {
				log.debug("[tulip] 查询可使用卡券列表getCouponList, 如果出现这条日志则异常，因为当前投资的产品，一定会有产品标签的。");
			}
		}
		if (CollectionUtils.isEmpty(returnList)) {
			pageResp.setTotal(0);
			return pageResp;
		}
		log.debug("=========================available coupone List returnList：{} ", JSONObject.toJSONString(returnList));
		for (UserCouponEntity entity : returnList) {
			MyCouponRep rep = new MyCouponRep();
			BeanUtils.copyProperties(entity, rep);
			rep.setCouponId(entity.getOid());
			pageResp.getRows().add(rep);
		}
		//排序
		Collections.sort(pageResp.getRows(), myCouponSortByASC);
		pageResp.setTotal(pageResp.getRows().size());
		log.debug("=========================available coupone List：{} ", JSONObject.toJSONString(pageResp));
		return pageResp;
	}
	
	
	/**
	 * 查询卡券是否满足使用规则
	 * @param returnList 满足使用规则的卡券
	 * @param userCouponEntity 判断当前卡券
	 * @param str
	 */
	public void checkCouponUseRule(List<UserCouponEntity> returnList, UserCouponEntity userCouponEntity, UserInvestEntity user){
		log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券 {} 在没有选择产品标签时，需要继续判断卡券使用规则", userCouponEntity.getCouponBatch());
		List<RuleEntity> ruleList = ruleService.listByCid(userCouponEntity.getCouponBatch());
		if (!CollectionUtils.isEmpty(ruleList)) {
			RuleEntity ruleEntity = ruleList.get(0);
			List<RuleItemEntity> ruleItemList = ruleItemService.listByRuleId(ruleEntity.getOid());// 根据事件编码查询下发规则
			if (!CollectionUtils.isEmpty(ruleItemList)) {// 存在规则约束的情况
				List<String> expressionList = new ArrayList<String>();// 表达式列表
				List<String> propList = new ArrayList<String>();// 属性列表
				Map<String, Object> map = new HashMap<String, Object>();
				for (RuleItemEntity ruleItemEntity : ruleItemList) {
					String expression = toExpression(ruleItemEntity.getValue(), ruleItemEntity.getExpression(), ruleItemEntity.getPropId());
					expressionList.add(expression);
					propList.add(ruleItemEntity.getPropId());
				}
				String expression = StringUtils.join(expressionList, ruleEntity.getWeight().equals(RuleEntity.WEIGHT_AND) ? RuleEntity.WEIGHT_AND_EXPRESSION : RuleEntity.WEIGHT_OR_EXPRESSION);// 组装卡券规则表达式
				for (String string : propList) {
					String value = getIssuedPropValue(string, user.getUserId(), user);
					map.put(string, StringUtils.isNotBlank(value) ? value : "0");
				}
				if (compareExpression(expression, map)) {// 剔除卡券规则校验不通过的卡券
					log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券 {} 在满足卡券使用规则【investCount累计投资次数】, 计入可使用列表", userCouponEntity.getCouponBatch());
					returnList.add(userCouponEntity);
				}
			} else {
				log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券[代金券或加息券]{} 有使用规则基本信息，但是没有设置卡券使用规则属性, 直接计入可使用列表");
				returnList.add(userCouponEntity);
			}
		} else {
			log.debug("[tulip] 查询可使用卡券列表getCouponList, 当前卡券 {}没有设置卡券使用规则, 直接计入可使用列表", userCouponEntity.getCouponBatch());
			returnList.add(userCouponEntity);	
		}
	}
	
	/**
	 * 获取用户卡券数量
	 * @return
	 */
	public MyCouponRep getMyCouponNum(MyCouponReq myCoupoonReq){
		MyCouponRep rep =new MyCouponRep();
		rep.setNotUsedNum(userCouponService.countNotUsedNum(myCoupoonReq.getUserId()));
		rep.setUsedNum(userCouponService.countUsedNum(myCoupoonReq.getUserId()));
		rep.setExpiredNum(userCouponService.countExpiredNum(myCoupoonReq.getUserId()));
		return rep;
	}
	/**
	 * 查询用户卡券列表
	 *
	 * @param myCouponReq
	 * @return
	 */
	public PageResp<MyCouponRep> getMyCouponList(MyCouponReq myCouponReq) {
		Specification<UserCouponEntity> spec = new Specification<UserCouponEntity>() {
			@Override
			public Predicate toPredicate(Root<UserCouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.and(cb.equal(root.get("userId").as(String.class), myCouponReq.getUserId()));
			}
		};
		if (!StringUtil.isEmpty(myCouponReq.getType())) {
			Specification<UserCouponEntity> stateSpec = new Specification<UserCouponEntity>() {
				@Override
				public Predicate toPredicate(Root<UserCouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.and(cb.equal(root.get("type").as(String.class), myCouponReq.getType()));
				}
			};
			spec = Specifications.where(spec).and(stateSpec);
		}
		if (!StringUtil.isEmpty(myCouponReq.getStatus())) {
			Specification<UserCouponEntity> statusSpec = new Specification<UserCouponEntity>() {
				@Override
				public Predicate toPredicate(Root<UserCouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate p = null;
					if(UserCouponEntity.COUPON_STATUS_USED.equals(myCouponReq.getStatus())){
						p = cb.or(cb.equal(root.get("status").as(String.class), UserCouponEntity.COUPON_STATUS_USED),
								cb.equal(root.get("status").as(String.class), UserCouponEntity.COUPON_STATUS_INVALID),
								cb.equal(root.get("status").as(String.class), UserCouponEntity.COUPON_STATUS_WRITEOFF));
					}else{
						p = cb.and(cb.equal(root.get("status").as(String.class), myCouponReq.getStatus()));
					}
					return p;
				}
			};
			spec = Specifications.where(spec).and(statusSpec);
		}

		//做排序
		Order sortOrder = new Order(Direction.DESC, "finish");
		if(UserCouponEntity.COUPON_STATUS_NOUSED.equals(myCouponReq.getStatus())){
			sortOrder = new Order(Direction.DESC, "leadTime"); //未使用，领取时间倒序：最新领取的在最上面
		} else if(UserCouponEntity.COUPON_STATUS_USED.equals(myCouponReq.getStatus())){
			sortOrder = new Order(Direction.DESC, "useTime"); //已使用，使用时间倒序：最新使用的在最上面
		}
		
		PageResp<MyCouponRep> pageResp = new PageResp<MyCouponRep>();
		Pageable pageable = new PageRequest(myCouponReq.getPage() - 1, myCouponReq.getRows(), new Sort(sortOrder));
		Page<UserCouponEntity> enchs = this.userCouponService.findAll(spec, pageable);// 查找所有卡券
		for (UserCouponEntity userCouponEntity : enchs) {
			MyCouponRep userRep = new MyCouponRep();
			BeanUtil.copyProperties(userCouponEntity, userRep);
			if (UserCouponEntity.COUPON_STATUS_INVALID.equals(userCouponEntity.getStatus()) || UserCouponEntity.COUPON_STATUS_WRITEOFF.equals(userCouponEntity.getStatus())) {
				userRep.setStatus(UserCouponEntity.COUPON_STATUS_USED);
			}
			userRep.setCouponId(userCouponEntity.getOid());
			pageResp.getRows().add(userRep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}

	/**
	 * 查询用户卡券信息
	 * @param myCouponReq
	 * @return
	 */
	public MyCouponRep findUserCoupon(MyCouponReq myCouponReq) {
		UserCouponEntity entity = this.userCouponService.findUserCouponByOid(myCouponReq.getCouponId());// 查找所有卡券
		if (null == entity ) {
			throw new GHException(110000);//对应的卡券不存在
		}
		MyCouponRep rep = new MyCouponRep();
		BeanUtils.copyProperties(entity, rep);
		rep.setCouponId(entity.getOid());
		return rep;
	}

	/**
	 * 使用卡券
	 *
	 * @param userCouponId
	 * @return
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public MyCouponRep useUserCoupon(MyCouponReq req) {
		MyCouponRep rep = new MyCouponRep();
		int num=userCouponService.useUserCoupon(UserCouponEntity.COUPON_STATUS_USED, req.getCouponId(), req.getUserId());
		if (num < 1) {
			rep.setErrorCode(40001);
			rep.setErrorMessage("卡券已使用");
			return rep;
		}
		couponService.updateUseCount(req.getCouponId(), 1);
		UserCouponEntity entity = userCouponService.findUserCouponByOid(req.getCouponId());
		BeanUtils.copyProperties(entity, rep);
		rep.setCouponId(entity.getOid());
		return rep;
	}
	/**
	 * 重置卡券
	 * 将已使用的卡券状态设为未使用
	 * @param req
	 * @return
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public MyCouponRep resetUserCoupon(MyCouponReq req){
		MyCouponRep rep=new MyCouponRep();
		int num = userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, req.getCouponId(), req.getUserId());
		couponService.updateUseCount(req.getCouponId(), -1);
		if(num < 1){
			throw new GHException("重置卡券异常!");
		}
		UserCouponEntity entity = this.userCouponService.findUserCouponByOid(req.getCouponId());
		BeanUtils.copyProperties(entity, rep);
		rep.setCouponId(entity.getOid());
		return rep;
	}

	/**
	 * 查询可使用卡券详述
	 *
	 * @param myCouponReq
	 * @return
	 */
	public MyCouponRep getCouponDetail(MyCouponReq myCouponReq) {
		UserCouponEntity entity = this.userCouponService.findUserCouponByOid(myCouponReq.getCouponId());
		MyCouponRep rep = new MyCouponRep();
		if (entity != null) {
			BeanUtils.copyProperties(entity, rep);
			rep.setCouponId(entity.getOid());
		}
		//查询订单状态为成功的订单
		CouponOrderEntity couponOrderEntity = couponOrderService.findOrderByCouponId(myCouponReq.getCouponId());
		if (null != couponOrderEntity) {
			rep.setOrderCode(couponOrderEntity.getOrderCode());
		}
		return rep;
	}
	
	public void onInvestment(String biz){
		if(!eventAsync){
			onInvestmentAsynch(biz);
		}
		log.info("==========申购事件 保存数据库成功， 之后由定时异步处理=========");
	}
	
	public void onInvestmentAsynch(String biz){
		String systemType = StringUtil.getString(biz, "systemType");
		switch (systemType) {
		case InvestEvent.systemType_ZY:
			onZhangYueInvestment(biz);
			break;
		case InvestEvent.systemType_JZ:
			onJinZhuInvestment(biz);
			break;
		case InvestEvent.systemType_TJS:
			onTianJinSuoInvestment(biz);
			break;
		default:
			onGHInvestment(biz);
			break;
		}
	}
	
	/**
	 * 掌悦申购事件
	 *
	 * @param biz
	 */
	private void onZhangYueInvestment(String biz) {
		InvestEvent investEvent = JSONObject.parseObject(biz, InvestEvent.class);
		//设置规则参数
		setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FriendEvent friendEvent = new FriendEvent();
		FirstFriendInvestEvent firstFriendInvestEvent = new FirstFriendInvestEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId = entity.getUserId();
		Long num = couponOrderService.countByUserId(userId);
		couponOrderService.createCouponOrder(entity);
		if (entity.getOrderStatus().equals("success")) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			if (num < 1) {
				List<BizRule> ruleList = new ArrayList<BizRule>();
				//推荐人存在就给推荐人发送卡券
				if (!StringUtils.isEmpty(userEntity.getFriendId())) {
					//加载推荐人规则
					ruleList.addAll(loadBizRuleByEventType(EventConstants.EVENTTYPE_FRIEND));
					//加载推荐人首次投资规则
					ruleList.addAll(loadBizRuleByEventType(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST));
					friendEvent.setUserId(userEntity.getFriendId());
					firstFriendInvestEvent.setUserId(userEntity.getUserId());
					this.sendCouponWithDrools(friendEvent, friendEvent.getEventType());
					this.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
				//加载投资申购规则
				this.sendCouponWithDrools(investEvent, investEvent.getEventType());
			}
			userInvestService.updateInvestAmount(entity.getOrderAmount(), entity.getUserId());
		}else{
			if (!StringUtil.isEmpty(entity.getCouponId())) {
				CouponOrderEntity couponOrder=couponOrderService.findOrderByCouponIdAndUserId(entity.getCouponId(),entity.getUserId());
				if(null ==couponOrder){
					userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, entity.getCouponId(), userId);// 解锁标识未使用
					couponService.updateUseCount(entity.getCouponId(), -1);
				}
			}
		}
	}
	/**
	 * 金猪申购事件
	 *
	 * @param biz
	 */
	private void onJinZhuInvestment(String biz) {
		InvestEvent investEvent = JSONObject.parseObject(biz, InvestEvent.class);
		//设置规则参数
		setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FriendEvent friendEvent = new FriendEvent();
		FirstFriendInvestEvent firstFriendInvestEvent = new FirstFriendInvestEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId = entity.getUserId();
		Long num = couponOrderService.countByUserId(userId);
		couponOrderService.createCouponOrder(entity);
		if (entity.getOrderStatus().equals("success")) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			List<BizRule> ruleList = new ArrayList<BizRule>();
			if (num < 1) {
				//推荐人存在就给推荐人发送卡券
				if (!StringUtils.isEmpty(userEntity.getFriendId())) {
					//加载推荐人规则
					ruleList.addAll(loadBizRuleByEventType(EventConstants.EVENTTYPE_FRIEND));
					//加载推荐人首次投资规则
					ruleList.addAll(loadBizRuleByEventType(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST));
					friendEvent.setUserId(userEntity.getFriendId());
					firstFriendInvestEvent.setUserId(userEntity.getUserId());
					this.sendCouponWithDrools(friendEvent, friendEvent.getEventType());
					this.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
			}
			this.sendCouponWithDrools(investEvent, investEvent.getEventType());
			userInvestService.updateInvestAmount(entity.getOrderAmount(), entity.getUserId());
		}else{
			if (!StringUtil.isEmpty(entity.getCouponId())) {
				CouponOrderEntity couponOrder=couponOrderService.findOrderByCouponIdAndUserId(entity.getCouponId(),entity.getUserId());
				if(null ==couponOrder){
					userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, entity.getCouponId(), userId);// 解锁标识未使用
					couponService.updateUseCount(entity.getCouponId(), -1);
				}
			}
		}
	}
	/**
	 * 天金所申购事件
	 *
	 * @param biz
	 */
	private void onTianJinSuoInvestment(String biz) {
		InvestEvent investEvent = JSONObject.parseObject(biz, InvestEvent.class);
		//设置规则参数
		setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FriendEvent friendEvent = new FriendEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId=investEvent.getUserId();
		couponOrderService.createCouponOrder(entity);
		if (entity.getOrderStatus().equals("success")) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			//推荐人存在就给推荐人发送卡券
			if (!StringUtils.isEmpty(userEntity.getFriendId())) {
				UserInvestEntity friendEntity = userInvestService.findUserInvestByUserId(userEntity.getFriendId());
				friendEvent.setUserId(friendEntity.getUserId());
				friendEvent.setFirstInvestAmount(friendEntity.getFirstInvestAmount());
				friendEvent.setFriends(friendEntity.getFriends());
				friendEvent.setInvestAmount(userEntity.getInvestAmount().add(entity.getOrderAmount()));
				this.sendCouponWithDrools(friendEvent, friendEvent.getEventType());				
			}
			//加载投资申购规则
			this.sendCouponWithDrools(investEvent, investEvent.getEventType());
			userInvestService.updateInvestAmount(entity.getOrderAmount(), entity.getUserId());
		}else{
			if (!StringUtil.isEmpty(entity.getCouponId())) {
				CouponOrderEntity couponOrder=couponOrderService.findOrderByCouponIdAndUserId(entity.getCouponId(),entity.getUserId());
				if(null ==couponOrder){
					userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, entity.getCouponId(), userId);// 解锁标识未使用
					couponService.updateUseCount(entity.getCouponId(), -1);
				}
			}
		}
	}
	/**
	 * 寓锡申购事件
	 * @param biz
	 */
	public void onGHInvestment(String biz) {
		InvestEvent event = JSONObject.parseObject(biz, InvestEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(event.getOrderCode(),event.getOrderType());
		if(null != findOrder){
			log.info("重复的订单：{}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		couponOrderService.createCouponOrder(entity);
		log.info("创建优惠券定单 CouponOrderEntity={}",JSONObject.toJSON(entity));
		Long num = couponOrderService.countByUserId(event.getUserId());
		if (CouponOrderEntity.ORDERSTATUS_success.equals(entity.getOrderStatus())) {// 订单成功
			//加载投资申购规则
			log.info("执行申购event={}",JSONObject.toJSON(event));
			if (num.intValue() <= 1) {
				UserInvestEntity userInvestEntity = new UserInvestEntity();
				String data = UserCouponRedisUtil.getStr(redis, event.getUserId());
				userInvestEntity = JSONObject.parseObject(data, UserInvestEntity.class);
				//推荐人存在就给推荐人发送卡券
				if (!StringUtils.isEmpty(userInvestEntity.getFriendId())) {
					FirstFriendInvestEvent firstFriendInvestEvent = new FirstFriendInvestEvent();
					firstFriendInvestEvent.setUserId(userInvestEntity.getFriendId());
					setRuleParams(firstFriendInvestEvent);
					this.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
			}
			userInvestService.updateInvestAmount(entity.getOrderAmount(), entity.getUserId());
			this.sendCouponWithDrools(event, event.getEventType());
		}else{
			if (!StringUtil.isEmpty(entity.getCouponId())) {
				CouponOrderEntity couponOrder=couponOrderService.findOrderByCouponIdAndUserId(entity.getCouponId(),entity.getUserId());
				if(null ==couponOrder){
					userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, entity.getCouponId(), entity.getUserId());// 解锁标识未使用
					couponService.updateUseCount(entity.getCouponId(), -1);
				}
			}
		}
		log.info("{}=end to async", event.getEventType());
	}
	/**
	 * 注册事件
	 * 
	 * @param biz 传递的json参数
	 */
	public void onRegister(String biz) {
		onRegisterAsynch(biz);
		log.info("==========注册事件保存数据库成功， 之后由定时异步处理=========");
	}
	
	public void onRegisterAsynch(String biz) {
		RegisterEvent event = JSONObject.parseObject(biz, RegisterEvent.class);
		FriendEvent friendEvent=new FriendEvent();
		UserInvestEntity entity = new UserInvestEntity();
		BeanUtils.copyProperties(event, entity, "investCount");
		if (StringUtils.isNotBlank(entity.getFriendId())) {// 如果存在推荐人的情况
			friendEvent.setUserId(entity.getFriendId());
			setRuleParams(friendEvent);
			this.sendCouponWithDrools(friendEvent, friendEvent.getEventType());
		}
		UserCouponRedisUtil.setStr(redis, entity.getUserId(), JSONObject.toJSONString(entity));
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
		log.info("{}=end to async", event.getEventType());
	}

	/**
	 * 推荐人事件
	 *
	 * @param biz 传递的json参数
	 */
	public void onFriend(String biz) {
		this.onFriendAsynch(biz);
		log.info("===========推荐人事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onFriendAsynch(String biz) {
		FriendEvent event = JSONObject.parseObject(biz, FriendEvent.class);
		//设置规则参数
		setRuleParams(event);
		UserInvestEntity entity = new UserInvestEntity();
		BeanUtils.copyProperties(event, entity);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 实名制监听
	 *
	 * @param biz
	 */
	public void onSetRealName(String biz) {
		onSetRealNameAsynch(biz);
		log.info("===========实名认证事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onSetRealNameAsynch(String biz) {
		AuthenticationEvent event = JSONObject.parseObject(biz, AuthenticationEvent.class);
		//设置规则参数
		setRuleParams(event);
		UserInvestEntity entity = new UserInvestEntity();
		BeanUtils.copyProperties(event, entity);
		userInvestService.updateReferee(entity.getUserId(), entity.getName(), entity.getBirthday());
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 赎回监听
	 *
	 * @param biz
	 */
	public void onRedeem(String biz) {
		onRedeemAsynch(biz);
		log.info("===========赎回事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onRedeemAsynch(String biz) {
		RedeemEvent event = JSONObject.parseObject(biz, RedeemEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setOrderType("redeem");
		couponOrderService.createCouponOrder(entity);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 到期兑付
	 *
	 * @param biz
	 */
	public void onBearer(String biz) {
		onBearerAsynch(biz);
		log.info("===========到期兑付事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onBearerAsynch(String biz) {
		BearerEvent event = JSONObject.parseObject(biz, BearerEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setOrderType("bearer");
		couponOrderService.createCouponOrder(entity);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 退款监听
	 *
	 * @param biz
	 */
	public void onRefund(String biz) {
		onRefundAsynch(biz);
		log.info("===========退款事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onRefundAsynch(String biz) {
		RefundEvent event = JSONObject.parseObject(biz, RefundEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setOrderType("refund");
		couponOrderService.createCouponOrder(entity);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 提现事件
	 *
	 * @param biz
	 */
	public void onCash(String biz) {
		onCashAsynch(biz);
		log.info("==========提现事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onCashAsynch(String biz) {
		CashEvent event = JSONObject.parseObject(biz, CashEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		entity.setCouponId(event.getCouponId());
		entity.setUserId(event.getUserId());
		entity.setUserAmount(event.getUserAmount());
		entity.setOrderAmount(event.getOrderAmount());
		entity.setCreateTime(event.getCreateTime());
		entity.setOrderCode(event.getOrderCode());
		entity.setOrderStatus(event.getOrderStatus());
		entity.setOrderType("cash");
		couponOrderService.createCouponOrder(entity);
		String couponStatus = UserCouponEntity.COUPON_STATUS_NOUSED;
		if (entity.getOrderStatus().equals("success")) {
			if (StringUtils.isNotBlank(entity.getCouponId())) {// 使用卡券的订单
				couponStatus = UserCouponEntity.COUPON_STATUS_USED;
				userCouponService.useUserCoupon(couponStatus, entity.getCouponId(), event.getUserId());// 解锁
				couponService.updateUseCount(entity.getCouponId(), 1);
			}
			//加载规则
			this.sendCouponWithDrools(event, event.getEventType());
		} else {
			if (StringUtils.isNotBlank(entity.getCouponId())) {// 使用卡券的订单
				userCouponService.resetUserCoupon(couponStatus, entity.getCouponId(), event.getUserId());// 解锁
				couponService.updateUseCount(entity.getCouponId(), -1);
			}
		}
	}

	/**
	 * 充值事件
	 *
	 * @param biz
	 */
	public void onRecharge(String biz) {
		onRechargeAsynch(biz);
		log.info("==========充值事件 保存数据库成功， 只有由定时异步处理=========");

	}
	
	public void onRechargeAsynch(String biz) {
		RechargeEvent event = JSONObject.parseObject(biz, RechargeEvent.class);
		//设置规则参数
		setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setOrderType("recharge");
		couponOrderService.createCouponOrder(entity);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 绑卡事件(暂时没使用，只使用实名认证)
	 *
	 * @param biz
	 */
	public void onBindingCard(String biz) {
		onBindingCardAsynch(biz);
		log.info("==========绑定事件保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onBindingCardAsynch(String biz) {
		BindingCardEvent event = JSONObject.parseObject(biz, BindingCardEvent.class);
		//设置规则参数
		setRuleParams(event);
		//加载规则
		this.sendCouponWithDrools(event, event.getEventType());
	}

	/**
	 * 签到事件
	 *
	 * @param biz
	 */
	public void onSign(String biz) {
		onSignAsynch(biz);
		log.info("==========绑定事件保存数据库成功， 只有由定时异步处理=========");
	}
	
	public void onSignAsynch(String biz) {
		SignEvent event = JSONObject.parseObject(biz, SignEvent.class);
		//设置规则参数
		setRuleParams(event);
		SignInEntity entity = new SignInEntity();
		//检测用户是否签到
		if (!checkSignIn(event.getUserId())) {
			entity.setUserId(event.getUserId());
			entity.setSignInTime(DateUtil.getSqlCurrentDate());
			entity.setSignDate(DateUtil.getSqlDate());
			entity = this.signInService.createSignInEntity(entity);
			//加载规则
			this.sendCouponWithDrools(event,  event.getEventType());
		}else{
			throw new GHException("用户userId={"+event.getUserId()+"}已经签到!");
		}
	}

	/**
	 * 检测用户是否签到
	 *
	 * @param userId
	 * @return
	 */
	public Boolean checkSignIn(String userId) {
		//判断当日是否已经签到
		SignInEntity signEntity = this.signInService.findByUserIdAndSignDate(userId, DateUtil.getSqlDate());
		if ( null != signEntity ) {
			log.info("用户已签到userId={}", userId);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 流标事件
	 *
	 * @param biz
	 */
	public void onInvalidBids(String biz) {
		onInvalidBidsAsynch(biz);
		log.info("==========流标事件 保存数据库成功， 只有由定时异步处理=========");
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public void invalidBidsGenerateCoupon(CouponOrderEntity coEntity,EventEntity eventEntity,List<EventEntity> eventList){

		UserInvestEntity user=userInvestService.findUserInvestByUserId(coEntity.getUserId());
		CouponEntity couponEntity = couponService.findCouponByOid(coEntity.getCouponId());
		UserCouponEntity userCoupon = new UserCouponEntity();
		BeanUtils.copyProperties(couponEntity, userCoupon);
		BigDecimal amount = BigDecimal.valueOf(facadeNewService.toRandInt(couponEntity, coEntity.getUserId()));
		userCoupon.setAmount(amount);
		userCoupon.setCouponBatch(couponEntity.getOid());
		userCoupon.setStatus(UserCouponEntity.COUPON_STATUS_NOUSED);
		userCoupon.setUserId(coEntity.getUserId());
		userCoupon.setLeadTime(DateUtil.getSqlCurrentDate());
		userCoupon.setStart(DateUtil.getSqlCurrentDate());
		userCoupon.setPhone(user.getPhone());
		Date finish = new Date();
		if (couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_DAY)) {
			finish = DateUtil.getEndDateTime(DateUtil.addSQLDays(DateUtil.getSqlCurrentDate(), couponEntity.getDisableDate()));
		} else if (couponEntity.getDisableType().equals(CouponEntity.DISABLETYPE_HOUR)) {
			Date date = DateUtil.addSQLDays(DateUtil.getSqlCurrentDate(), couponEntity.getDisableDate());
			finish= DateUtil.parseDate(DateUtil.format(date,DateUtil.datetimePattern), DateUtil.datetimePattern);
		}
		userCoupon.setFinish(new Timestamp(finish.getTime()));
		userCoupon.setEventType(EventConstants.EVENTTYPE_INVALIDBIDS);
		userCouponService.createUserCoupon(userCoupon);
		couponService.updateRemainCount(couponEntity.getOid(), amount);
		log.info("下发卡券成功,用户:{}已经领取活动:{}下发的卡券:{}!", coEntity.getUserId(), eventEntity.getOid(), couponEntity.getOid());
	
	}
	
	public void onInvalidBidsAsynch(String biz) {
		List<EventEntity> eventList = eventService.eventListByType(StringUtil.getString(biz, "eventType"));// 根据事件编码查询活动事件
		if (!CollectionUtils.isEmpty(eventList) && eventList.size() == 1) {// 存在活动且流标活动只有一个，多个表示有问题
			InvalidBidsEvent entity = JSONObject.parseObject(biz, InvalidBidsEvent.class);
			EventEntity eventEntity = eventList.get(0);
			List<CouponOrderEntity> couponOrderList = couponOrderService.findUsedCouponByProductId(entity.getProductId());
			if (!CollectionUtils.isEmpty(couponOrderList)) {// 存在活动的情况
				//流标之后把该产品使用的卡券作废
				List<String> couponIds = Collections3.extractToList(couponOrderList, "couponId");
				String[] arr = (String[]) couponIds.toArray(new String[couponIds.size()]);
				userCouponService.updateCouponForInvalid(arr);

				for (CouponOrderEntity coEntity : couponOrderList) {
					invalidBidsGenerateCoupon(coEntity,eventEntity,eventList);
				}
			}
		}
	}
	
	/**
	 * 下发卡券
	 * 自定义下发：是根据eventId来下发的，而不是根据eventType，这里需要单独的方法，而不是公共的
	 * @param biz
	 */
	public void onCustom(String biz) {
		CustomEvent event = JSONObject.parseObject(biz, CustomEvent.class);
		UserInvestEntity user = userInvestService.findUserInvestByUserId(event.getUserId());
		UserCouponRedisUtil.setStr(redis, user.getUserId(), JSONObject.toJSONString(user));
		
		//this.sendCouponWithDrools(event, event.getEventType());
		this.sendCouponWithDroolsByEventId(event, event.getEventId(), event.getUserId());
	}
	
	public void onEmployeeNew(String biz){
		log.info("进入新的employeenew");
		EmployeeEvent event = JSONObject.parseObject(biz, EmployeeEvent.class);
		setRuleParams(event);
		
		this.sendCouponWithDrools(event, event.getEventType());
		log.info("{}=end to async", event.getEventType());
	}
	
	/**
	 * 根据规则去下发卡券（对内存中的初始化规则信息更新，命中多台机器场景）
	 * 
	 * @param event
	 * @param eventType
	 */
	private void sendCouponWithDrools(BaseEvent event, String eventType){
		//获取当前活动类型的规则列表
		List<String> rules = new ArrayList<String>();
		List<RuleEntity> ruleList = ruleService.findRuleListByEventType(eventType);
		List<BizRule> bizRuleList = new ArrayList<BizRule>();
		BizRule bizRule = null;
		for (RuleEntity ruleEntity : ruleList) {
			rules.add(ruleEntity.getOid());
			bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType(ruleEntity.getType()).content(ruleEntity.getExpression()).build();
	 		bizRuleList.add(bizRule);
		}
		//获取的规则为空，则不fire卡券
		if(!Collections3.isEmpty(rules)){
			List<String> memoryRules = KeyRedisUtils.loadEventRuleList.get(eventType);
			if(null == memoryRules){
				memoryRules = new ArrayList<String>();
			}
			log.info("memoryRules={}, db_rules={}", memoryRules, rules);
			if(!Arrays.equals(memoryRules.toArray(), rules.toArray())){
				log.info("命中更新内存中的规则数据，需要reload，此时会影响卡券下发效率");
				KeyRedisUtils.loadEventRuleList.clear();
				this.containerHolder.reload(eventService.initEventList());
//				KeyRedisUtils.loadEventRuleList.put(eventType, rules);
			}
			log.info("=========hugo========containerHolder.fire.begin={} ", JSONObject.toJSONString(event));
			this.containerHolder.fire(event);
			log.info("=========hugo========containerHolder.fire.end ");
		} else {
			KeyRedisUtils.loadEventRuleList.remove(eventType);
			log.info("=========hugo========rules= null, {}", JSONObject.toJSONString(eventType));
		}
	}
	
	/**
	 * 自定义下发卡券
	 * @param event
	 * @param eventId
	 * @param userId
	 */
	private void sendCouponWithDroolsByEventId(BaseEvent event, String eventId, String userId){
		//获取当前活动类型的规则列表
		List<String> rules = new ArrayList<String>();
		List<BizRule> bizRuleList = loadBizRuleByEventId(eventId);
		for (BizRule rule : bizRuleList) {
			rules.add(rule.getRuleId());
		}
		//获取的规则为空，则不fire卡券
		if(!Collections3.isEmpty(rules)){
			List<String> memoryRules = KeyRedisUtils.loadEventRuleList.get(EventConstants.EVENTTYPE_CUSTOM);
			if(null == memoryRules){
				memoryRules = new ArrayList<String>();
			}
			log.info("memoryRules={}, db_rules={}", memoryRules, rules);
			if(!Arrays.equals(memoryRules.toArray(), rules.toArray())){
				log.info("命中更新内存中的规则数据，需要reload，此时会影响卡券下发效率");
				KeyRedisUtils.loadEventRuleList.clear();
				this.containerHolder.reload(eventService.initEventList());
			}
			this.containerHolder.fire(event);
		} else {
			KeyRedisUtils.loadEventRuleList.remove(EventConstants.EVENTTYPE_CUSTOM);
		}
	}
	
	/**
	 * 员工认证事件
	 * @param biz
	 */
	private void onEmployee(String biz) {
		log.info("进入old的employee");
		AsynchEventEntity entity = new AsynchEventEntity();
		entity.setEventStr(biz);
		entity.setEventType(EventConstants.EVENTTYPE_EMPLOYEE);
		entity.setEventStatus(AsynchEventEntity.EVENTSTATUS_BEENSENT);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		asynchEventService.saveEvent(entity);
		EmployeeEvent event = JSONObject.parseObject(biz, EmployeeEvent.class);
		setRuleParams(event);
	}

	/**
	 * 保存触发的事件流水/日志
	 * 
	 * @param biz
	 */
	public void saveEventLog(String biz, String eventCode) {
		AsynchEventEntity entity = new AsynchEventEntity();
		entity.setEventStr(biz);
		entity.setEventType(eventCode);
		if(!eventAsync){
			entity.setEventStatus(AsynchEventEntity.EVENTSTATUS_BEENSENT);
		}else{
			entity.setEventStatus(AsynchEventEntity.EVENTSTATUS_UNSENT);		
		}
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		asynchEventService.saveEvent(entity);
	}
	
	
//	@Async("asyncTaskExecutor")
//	@EventListener
	public void onAsyncBaseEvent(BaseEvent event) {
		log.info("=======================监听收获事件数据:{}", JSONObject.toJSONString(event));
		
		try {
			String eventType = org.apache.commons.beanutils.BeanUtils.getProperty(event, "eventType");
			this.saveEventLog(JSONObject.toJSONString(event), eventType);
			String actionName = eventType + "Service";
			SceneAction actionBean = (SceneAction) this.context.getBean(actionName);
			actionBean.execute(event);
			
		} catch (Exception e) {
			log.error("===================触发场景异常：{}", e.getMessage());
		}
	}
	
	/**
	 * 同步事件监听
	 *
	 * @param biz
	 */
	@EventListener
	public void onBizEvent(String biz) {
		log.info("监听收获事件数据:{}", biz);
		String eventCode = StringUtil.getString(biz, "eventType");
		this.saveEventLog(biz, eventCode);
//		String actionName = eventCode + "Service";
//		SceneAction actionBean = (SceneAction) this.context.getBean(actionName);
//		actionBean.execute(biz);
		
		if(eventAsync){
			//异步，直接返回，由定时处理
			return;
		}
		// 如果是异步事件, 这里不直接处理
		String isSync = StringUtil.getString(biz, "async");
		if (Boolean.parseBoolean(isSync)) {
			BaseEvent event = objectConvert(biz);
			this.eventPublisher.publishEvent(AsyncBaseEvent.builder()
					.baseEvent(event).build());
			return;
		}
		if (StringUtils.isNotBlank(eventCode)) {
			switch (eventCode) {
				//注册
				case EventConstants.EVENTTYPE_REGISTER:
					this.onRegister(biz);
					break;
				//推荐人
				case EventConstants.EVENTTYPE_FRIEND:
					this.onFriend(biz);
					break;
				//实名认证
				case EventConstants.EVENTTYPE_AUTHENTICATION:
					this.onSetRealName(biz);
					break;
				//投资
				case EventConstants.EVENTTYPE_INVESTMENT:
					this.onInvestment(biz);
					break;
				//赎回
				case EventConstants.EVENTTYPE_REDEEM:
					this.onRedeem(biz);
					break;
				//到期兑付
				case EventConstants.EVENTTYPE_BEARER:
					this.onBearer(biz);
					break;
				//提现事件
				case EventConstants.EVENTTYPE_CASH:
					this.onCash(biz);
					break;
				//退款
				case EventConstants.EVENTTYPE_REFUND:
					this.onRefund(biz);
					break;
				//绑卡
				case EventConstants.EVENTTYPE_BINDINGCARD:
					this.onBindingCard(biz);
					break;
				//充值
				case EventConstants.EVENTTYPE_RECHARGE:
					this.onRecharge(biz);
					break;
				//签到
				case EventConstants.EVENTTYPE_SIGN:
					this.onSign(biz);
					break;
				//流标
				case EventConstants.EVENTTYPE_INVALIDBIDS:
					this.onInvalidBids(biz);
					break;
				//下发卡券
				case EventConstants.EVENTTYPE_CUSTOM:
					this.onCustom(biz);
					break;
				//员工认证
				case EventConstants.EVENTTYPE_EMPLOYEE:
//					this.onEmployee(biz);
					this.onEmployeeNew(biz);
					break;
				default:
					break;
			}
		}
	}

	@Bean(name = "asyncTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(
				"async-pool-%d").build();
		return Executors.newFixedThreadPool(this.asyncPoolSize, factory);
	}

	@Async("asyncTaskExecutor")
	@EventListener
	public void synchBizEvent(AsyncBaseEvent event) {
		String biz = JSON.toJSONString(event.getBaseEvent());
		String eventCode = StringUtil.getString(biz, "eventType");
		if (StringUtils.isNotBlank(eventCode)) {
			switch (eventCode) {
				//注册
				case EventConstants.EVENTTYPE_REGISTER:
					this.onRegister(biz);
					break;
				//推荐人
				case EventConstants.EVENTTYPE_FRIEND:
					this.onFriend(biz);
					break;
				//实名认证
				case EventConstants.EVENTTYPE_AUTHENTICATION:
					this.onSetRealName(biz);
					break;
				//投资
				case EventConstants.EVENTTYPE_INVESTMENT:
					this.onInvestment(biz);
					break;
				//赎回
				case EventConstants.EVENTTYPE_REDEEM:
					this.onRedeem(biz);
					break;
				//到期兑付
				case EventConstants.EVENTTYPE_BEARER:
					this.onBearer(biz);
					break;
				//提现事件
				case EventConstants.EVENTTYPE_CASH:
					this.onCash(biz);
					break;
				//退款
				case EventConstants.EVENTTYPE_REFUND:
					this.onRefund(biz);
					break;
				//绑卡
				case EventConstants.EVENTTYPE_BINDINGCARD:
					this.onBindingCard(biz);
					break;
				//充值
				case EventConstants.EVENTTYPE_RECHARGE:
					this.onRecharge(biz);
					break;
				//签到
				case EventConstants.EVENTTYPE_SIGN:
					this.onSign(biz);
					break;
				//流标
				case EventConstants.EVENTTYPE_INVALIDBIDS:
					this.onInvalidBids(biz);
					break;
				//下发卡券
				case EventConstants.EVENTTYPE_CUSTOM:
					this.onCustom(biz);
					break;
					//员工认证
				case EventConstants.EVENTTYPE_EMPLOYEE:
//					this.onEmployee(biz);
					this.onEmployeeNew(biz);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 表达式校验
	 */
	public boolean compareExpression(String expression, Map<String, Object> map) {
		if (StringUtils.isBlank(expression)) {
			return true;
		}
		Boolean result = (Boolean) MVEL.eval(expression, map);
		return result.booleanValue();
	}

	/**
	 * 根据属性Id获取属性字段
	 *
	 * @param propId
	 * @param userInvestEntity
	 * @param investmentAmount
	 * @return
	 */
	public String getPropValue(String propId, UserInvestEntity userInvestEntity, BigDecimal investmentAmount) {
		switch (propId) {
			case "R001":
				return objectToString(userInvestEntity.getFriends());
			case "R002":
				return objectToString(userInvestEntity.getInvestAmount());
			case "R003":
				return investmentAmount.toString();
			case "R004":
				break;
			case "R005":
				break;
			case "R006":
				return objectToString(DurationFormatUtils.formatPeriod(System.currentTimeMillis(), userInvestEntity.getFirstInvestTime().getTime(), "d"));
			case "R007":
				return objectToString(userInvestEntity.getInvestCount());
			case "R008":
				return userInvestEntity.getFirstInvestAmount() == null
						? investmentAmount == null ? "0" : investmentAmount.toString() : "0";
			default:
				break;
		}
		return null;
	}

	/**
	 * 根据属性id获取值
	 *
	 * @param propId
	 * @param userId
	 * @param userInvestEntity
	 * @return
	 */
	public String getIssuedPropValue(String propId, String userId, UserInvestEntity userInvestEntity) {
		switch (propId) {
			case "R001":
				return objectToString(userInvestEntity.getFriends());
			case "R002":
				return objectToString(userInvestEntity.getInvestAmount());
			case "R003":
				break;
			case "R004":
				break;
			case "R005":
				break;
			case "R006":
				if(null != userInvestEntity.getFirstInvestTime()){
					return objectToString(DurationFormatUtils.formatPeriod(System.currentTimeMillis(), userInvestEntity.getFirstInvestTime().getTime(), "d"));
				}
				return "";
			case "R007":
				return objectToString(userInvestEntity.getInvestCount());
			case "R008":
				return objectToString(userInvestEntity.getFirstInvestAmount());
			case "R012":
				return getMyGroupSdk(userId).toString();
			default:
				break;
		}
		return null;
	}
	
	private <T> String objectToString(T t) {
		return t == null ? "" : t + "";
	}

	/**
	 * 计算卡券利息
	 *
	 * @param couponInterestReq
	 * @return
	 */
	public CouponInterestRep couponInterest(CouponInterestReq couponInterestReq) {
		UserCouponEntity enchs = this.userCouponService.findUserCouponByOid(couponInterestReq.getCouponId());
		Integer day = enchs.getValidPeriod() == null ? couponInterestReq.getDays() : enchs.getValidPeriod() > couponInterestReq.getDays() ? couponInterestReq.getDays() : enchs.getValidPeriod();
		BigDecimal amount = enchs.getAmount().multiply(couponInterestReq.getOrderAmount()).multiply(new BigDecimal(day)).divide(new BigDecimal(couponInterestReq.getYearDay()), 2);
		CouponInterestRep rep = new CouponInterestRep();
		rep.setCouponAmount(enchs.getMaxRateAmount() == null ? amount : amount.compareTo(enchs.getMaxRateAmount()) == -1 ? amount : enchs.getMaxRateAmount());
		return rep;
	}

	/**
	 * 核销卡券
	 *
	 * @param param
	 */
	@SuppressWarnings("unchecked")
	public void verificationCoupon(List<MyCouponReq> coupons) {
		List<String> couponIds = Collections3.extractToList(coupons, "couponId");
		String[] arr = (String[]) couponIds.toArray(new String[couponIds.size()]);
		userCouponService.updateCouponForWriteOff(arr);
	}

	/**
	 * 格式化表达式
	 *
	 * @param value
	 * @param expression
	 * @param propId
	 * @return
	 */
	private String toExpression(String value, String expression, String propId) {
		String a[] = {};
		String result;
		if (value.indexOf("[") != -1) {
			a = value.replaceAll("\\[", "").replaceAll("\\]", "").split("\\,");
			result = propId + ">" + a[0] + "&&" + propId + "<" + a[1];
		} else if (value.indexOf("[") == -1 && expression.equals("=")) {
			result = propId + expression + expression + value;
		} else {
			result = propId +" "+ expression +" "+ value;
		}
		return result;
	}

	/**
	 * 根据活动类型加载规则
	 *
	 * @param eventType
	 * @return
	 */
	private List<BizRule> loadBizRuleByEventType(String eventType) {//用户id主动下发
		List<EventEntity> eventList = eventService.eventListByType(eventType);// 根据事件编码查询活动事件
		return this.loadBizRuleByEventType(eventList);
	}

	/**
	 * 根据用户生日下发卡券
	 */
	public void issuedCouponByBirthDay() {//生日
		List<EventEntity> eventList = eventService.eventListByType("birthday");// 根据事件编码查询活动事件
		List<UserInvestEntity> userList = userInvestService.findUserByBirthDay();
		this.autoIssuedCoupon(eventList, userList);
	}

	/**
	 * 根据定时任务下发卡券
	 */
	public void issuedCouponBySchedule() {//调度活动
		List<EventEntity> eventList = eventService.eventListByType("schedule");// 根据事件编码查询活动事件
		List<UserInvestEntity> userList = userInvestService.findAllUser();
		this.autoIssuedCoupon(eventList, userList);
	}

	/**
	 * 根据事件Id下发卡券
	 *
	 * @param userId
	 * @param eventId
	 */
	public void issuedCouponByEventId(String userId, String eventId) {//用户id主动下发
		
		CustomEvent event = new CustomEvent();
		event.setEventId(eventId);
		event.setUserId(userId);
		event.setAsync(true);
		eventPublisher.publishEvent(JSONObject.toJSON(event).toString());
	}

	/**
	 * 根据活动下发卡券
	 *
	 * @param eventList
	 */
	private List<BizRule> loadBizRuleByEventType(List<EventEntity> eventList) {
		List<BizRule> list = new ArrayList<BizRule>();
		if (!CollectionUtils.isEmpty(eventList)) {// 存在活动的情况
			for (EventEntity eventEntity : eventList) {
				List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventEntity.getOid());
				if (!CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况,并加载规则
					BizRule bizRule = null;
					for (RuleEntity ruleEntity : ruleList) {
						bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType(ruleEntity.getType()).content(ruleEntity.getExpression()).build();
						list.add(bizRule);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 根据活动ID下发卡券
	 *
	 * @param eventId
	 * @return
	 */
	public List<BizRule> loadBizRuleByEventId(String eventId) {
		List<BizRule> list = new ArrayList<BizRule>();
		List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventId);
		if (!CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况,并加载规则
			BizRule bizRule = null;
			for (RuleEntity ruleEntity : ruleList) {
				bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType(ruleEntity.getType()).content(ruleEntity.getExpression()).build();
				list.add(bizRule);
			}
		}
		return list;
	}

	/**
	 * 自动任务下发卡券
	 */
	public void autoIssuedCoupon(List<EventEntity> eventList, List<UserInvestEntity> userList) {
		if (!CollectionUtils.isEmpty(eventList)) {// 存在活动的情况
			for (EventEntity eventEntity : eventList) {
				List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventEntity.getOid());
				if (!CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况
					for (RuleEntity ruleEntity : ruleList) {
						List<RuleItemEntity> ruleItemList = ruleItemService.listByRuleId(ruleEntity.getOid());// 根据事件编码查询下发规则
						if (!CollectionUtils.isEmpty(ruleItemList)) {// 存在规则约束的情况
							List<String> expressionList = new ArrayList<String>();// 表达式列表
							List<String> propList = new ArrayList<String>();// 属性列表
							Map<String, Object> map = new HashMap<String, Object>();
							for (RuleItemEntity ruleItemEntity : ruleItemList) {
								String expression = toExpression(ruleItemEntity.getValue(),
										ruleItemEntity.getExpression(), ruleItemEntity.getPropId());
								expressionList.add(expression);
								propList.add(ruleItemEntity.getPropId());
							}
							String expression = StringUtils.join(expressionList,
									ruleEntity.getWeight().equals(RuleEntity.WEIGHT_AND)
											? RuleEntity.WEIGHT_AND_EXPRESSION : RuleEntity.WEIGHT_OR_EXPRESSION);// 组装卡券规则表达式
							for (Iterator<UserInvestEntity> it = userList.iterator(); it.hasNext(); ) {// 用户筛选
								UserInvestEntity str = (UserInvestEntity) it.next();
								for (String string : propList) {
									String value = getIssuedPropValue(string, str.getUserId(), str);
									map.put(string, StringUtils.isNotBlank(value) ? value : "0");
								}
								if (!compareExpression(expression, map)) {// 如果校验通过的情况下
									it.remove();
								}
							}
							List<CouponEntity> couponList = couponService.listByRid(ruleEntity.getOid(), "get");// 获取下发卡券列表
							if (!CollectionUtils.isEmpty(couponList)) {
								for (UserInvestEntity user : userList) {// 用户列表
									List<CouponEntity> availbcouponList = this.avaibleCoupon(couponList);
									for (CouponEntity couponEntity : availbcouponList) {
										facadeNewService.generateCoupon(user, eventEntity.getType(), couponEntity);
									}
								}
							} else {
								log.info("自动下发卡券时,活动:{}没有对应的下发卡券!", eventEntity.getOid());
							}
						} else {
							log.info("自动下发卡券时,活动:{}没有对应的约束规则子项!", eventEntity.getOid());
							List<CouponEntity> couponList = couponService.listByRid(ruleEntity.getOid(), "get");// 获取下发卡券列表
							if (!CollectionUtils.isEmpty(couponList)) {
								for (UserInvestEntity user : userList) {// 用户列表
									List<CouponEntity> availbcouponList = this.avaibleCoupon(couponList);
									for (CouponEntity couponEntity : availbcouponList) {
										facadeNewService.generateCoupon(user, eventEntity.getType(), couponEntity);
									}
								}
							} else {
								log.info("下发卡券时,活动:{}没有对应的下发卡券!", eventEntity.getOid());
							}
							log.info("下发卡券时,活动:{}没有对应的约束规则子项!", eventEntity.getOid());
						}
					}
				} else {
					log.info("自动下发卡券时,活动:{}没有对应的规则约束!", eventEntity.getOid());
				}
			}
		} else {
			log.info("不存在自动触发活动");
		}
	}
	
	/**
	 * 生日和派发活动赠送卡券前，需要过滤掉已赠送完的卡券批次，剩余可用卡券>0的卡券才可以赠送出去
	 * 
	 * @param list
	 * @return
	 */
	private List<CouponEntity> avaibleCoupon(List<CouponEntity> list){
		
		List<CouponEntity> coupons = couponService.findCouponsByCouponIds(this.getIds(list));
		
		return coupons;
	}
	
	private String[] getIds(List<CouponEntity> list){
		List<String> ids = new ArrayList<String>();
		for (CouponEntity entity : list) {
			ids.add(entity.getOid());
		}
		int size = ids.size();  
		
		return (String[])ids.toArray(new String[size]);
	}

    public void autoIssuedCoupon(Map<RuleEntity,RuleExpressionProp> ruleList, UserInvestEntity userInvestEntity) {
        for (RuleEntity ruleEntity : ruleList.keySet()) {
            List<CouponEntity> couponList = couponService.listByRid(ruleEntity.getOid(), "get");
            String expression = ruleList.get(ruleEntity).getExpression();
            List<String> expressionList =  ruleList.get(ruleEntity).getExpressionList();
            List<String> propList = ruleList.get(ruleEntity).getPropList();
            //有规则根据根据规则匹配发放
            if (!CollectionUtils.isEmpty(expressionList)){
                Map<String, Object> map = new HashMap<String, Object>();
                for (String string : propList) {
                    String value = getIssuedPropValue(string, userInvestEntity.getUserId(), userInvestEntity);
                    map.put(string, StringUtils.isNotBlank(value) ? value : "0");
                }
                if (compareExpression(expression, map)) {// 如果校验通过的情况下
                    for (CouponEntity couponEntity : couponList) {
                        facadeNewService.generateCoupon(userInvestEntity, EventConstants.EVENTTYPE_CUSTOM, couponEntity);
                    }
                }
            }else{
                //无规则，之间根据卡券发放
                for (CouponEntity couponEntity : couponList) {
                    facadeNewService.generateCoupon(userInvestEntity, EventConstants.EVENTTYPE_CUSTOM, couponEntity);
                }
            }
        }
    }

    /**
     * 构建规则匹配需要的表达式和属性
     * @param ruleEntityList
     * @param result
     */
    private Map<RuleEntity,RuleExpressionProp> buildRuleExpressionProp(List<RuleEntity> ruleEntityList){
        Map<RuleEntity,RuleExpressionProp> result = new HashMap<>();
        for (RuleEntity ruleEntity : ruleEntityList) {
            List<RuleItemEntity> ruleItemList = ruleItemService.listByRuleId(ruleEntity.getOid());// 根据事件编码查询下发规则
            List<String> expressionList = new ArrayList<String>();// 表达式列表
            List<String> propList = new ArrayList<String>();// 属性列表
            Map<String, Object> map = new HashMap<String, Object>();
            for (RuleItemEntity ruleItemEntity : ruleItemList) {
                String expression = toExpression(ruleItemEntity.getValue(), ruleItemEntity.getExpression(), ruleItemEntity.getPropId());
                expressionList.add(expression);
                propList.add(ruleItemEntity.getPropId());
            }
            // 组装卡券规则表达式
            String expression = StringUtils.join(expressionList, ruleEntity.getWeight().equals(RuleEntity.WEIGHT_AND)
                            ? RuleEntity.WEIGHT_AND_EXPRESSION : RuleEntity.WEIGHT_OR_EXPRESSION);

            RuleExpressionProp ruleExpressionProp = new RuleExpressionProp();
            ruleExpressionProp.setExpression(expression);
            ruleExpressionProp.setExpressionList(expressionList);
            ruleExpressionProp.setPropList(propList);

            result.put(ruleEntity,ruleExpressionProp);
        }
        return result;
    }

	/**
	 * 自定义发送卡券（直接发送，不走drools，因为现在drools的规则是全量更新，不能指定单个活动的规则）
	 * 
	 * @param eventId
	 * @param userList
	 */
	public void autoIssuedCouponByEventId(String eventId, List<UserInvestEntity> userList) {
        List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventId);
        if (!CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况
            for (RuleEntity ruleEntity : ruleList) {
                List<RuleItemEntity> ruleItemList = ruleItemService.listByRuleId(ruleEntity.getOid());// 根据事件编码查询下发规则
                if (!CollectionUtils.isEmpty(ruleItemList)) {// 存在规则约束的情况
                    List<String> expressionList = new ArrayList<String>();// 表达式列表
                    List<String> propList = new ArrayList<String>();// 属性列表
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (RuleItemEntity ruleItemEntity : ruleItemList) {
                        String expression = toExpression(ruleItemEntity.getValue(),
                                ruleItemEntity.getExpression(), ruleItemEntity.getPropId());
                        expressionList.add(expression);
                        propList.add(ruleItemEntity.getPropId());
                    }
                    String expression = StringUtils.join(expressionList,
                            ruleEntity.getWeight().equals(RuleEntity.WEIGHT_AND)
                                    ? RuleEntity.WEIGHT_AND_EXPRESSION : RuleEntity.WEIGHT_OR_EXPRESSION);// 组装卡券规则表达式
                    for (Iterator<UserInvestEntity> it = userList.iterator(); it.hasNext(); ) {// 用户筛选
                        UserInvestEntity str = (UserInvestEntity) it.next();
                        for (String string : propList) {
                            String value = getIssuedPropValue(string, str.getUserId(), str);
                            map.put(string, StringUtils.isNotBlank(value) ? value : "0");
                        }
                        if (!compareExpression(expression, map)) {// 如果校验通过的情况下
                            it.remove();
                        }
                    }
                    List<CouponEntity> couponList = couponService.listByRid(ruleEntity.getOid(), "get");// 获取下发卡券列表
                    if (!CollectionUtils.isEmpty(couponList)) {
                        for (UserInvestEntity user : userList) {// 用户列表
                            for (CouponEntity couponEntity : couponList) {
                                facadeNewService.generateCoupon(user, EventConstants.EVENTTYPE_CUSTOM, couponEntity);
                            }
                        }
                    } else {
                        log.info("自动下发卡券时,活动:{}没有对应的下发卡券!", eventId);
                    }
                } else {
                    log.info("自动下发卡券时,活动:{}没有对应的约束规则子项!", eventId);
                    List<CouponEntity> couponList = couponService.listByRid(ruleEntity.getOid(), "get");// 获取下发卡券列表
                    if (!CollectionUtils.isEmpty(couponList)) {
                        log.info("=====================自定义活动赠送卡券直接发放，不走drools，没有在tulip_async_event表做记录===================");
                        for (UserInvestEntity user : userList) {// 用户列表
                            for (CouponEntity couponEntity : couponList) {
                                facadeNewService.generateCoupon(user, EventConstants.EVENTTYPE_CUSTOM, couponEntity);
                            }
                        }
                    } else {
                        log.info("下发卡券时,活动:{}没有对应的下发卡券!", eventId);
                    }
                    log.info("下发卡券时,活动:{}没有对应的约束规则子项!", eventId);
                }
            }
        } else {
            log.info("自动下发卡券时,活动:{}没有对应的规则约束!", eventId);
        }
	}

	/**
	 * 获取活动奖励金额的信息
	 *
	 * @return
	 */
	public EventRep getEventCouponMoneyInfo(EventReq param) {
		EventRep rep = new EventRep();
		List<Object[]> result = null;
		List<Object[]> investResult = null;
		if (param.getEventType().equals("newUser")) {
			result = eventService.getEventCouponInfo("register", param.getCouponType());
			investResult = eventService.getEventCouponInfo("investment", param.getCouponType());
		} else {
			result = eventService.getEventCouponInfo(param.getEventType(), param.getCouponType());
		}
		if (result.size() > 0) {
			Double d = 0d;
			for (Object[] obj : result) {
				d = d + Double.valueOf(obj[0].toString());
			}
			if (param.getEventType().equals("newUser")) {
				Object[] investObj = investResult.get(0);
				d = d + Double.valueOf(investObj[0].toString());
			}
			rep.setMoney(new BigDecimal(d));
		} else {
			rep.setErrorCode(-1);
			rep.setErrorMessage("活动异常或奖励方式异常！");
		}
		return rep;
	}

	/**
	 * 根据用户ID获取推荐人ID
	 *
	 * @param uid
	 * @return
	 */
	public UserInvestRep getFriendIdByUid(String uid) {
		UserInvestRep rep = new UserInvestRep();
		UserInvestEntity obj = userInvestService.findUserInvestByUserId(uid);
		rep.setFriendId(obj.getFriendId());
		return rep;
	}

	/**
	 * 根据奖励金额和奖励卡券类型获取事件ID
	 *
	 * @param money
	 * @param couponType
	 * @return
	 */
	@SuppressWarnings("unused")
	public EventRep getCustomEventId(String money, String couponType) {
		EventRep eventRep = new EventRep();
		EventEntity eventEntity = eventService.getCustomEventId(money, couponType);
		if (null == eventEntity) {
			eventRep.setErrorCode(-1);
			eventRep.setErrorMessage("未查到该奖励属性的活动!");
		} else {
			eventRep.setEventId(eventEntity.getOid());
		}
		return eventRep;
	}

	private BaseEvent objectConvert(String biz) {
		String eventCode = StringUtil.getString(biz, "eventType");
		if (StringUtils.isNotBlank(eventCode)) {
			switch (eventCode) {
				//注册
				case "register":
					return JSON.parseObject(biz, RegisterEvent.class);
				//推荐人
				case "referee":
					return JSON.parseObject(biz, FriendEvent.class);
				//实名认证
				case "authentication":
					return JSON.parseObject(biz, AuthenticationEvent.class);
				//投资
				case "investment":
					return JSON.parseObject(biz, InvestEvent.class);
				//赎回
				case "redeem":
					return JSON.parseObject(biz, RedeemEvent.class);
				//到期兑付
				case "bearer":
					return JSON.parseObject(biz, BearerEvent.class);
				//提现事件
				case "cash":
					return JSON.parseObject(biz, CashEvent.class);
				//退款
				case "refund":
					return JSON.parseObject(biz, RefundEvent.class);
				//绑卡
				case "bindingCard":
					return JSON.parseObject(biz, BindingCardEvent.class);
				//充值
				case "recharge":
					return JSON.parseObject(biz, RechargeEvent.class);
				//签到
				case "sign":
					return JSON.parseObject(biz, SignEvent.class);
				case "employee":
					return JSON.parseObject(biz, EmployeeEvent.class);
				//下发卡券
				case "custom":
					return JSON.parseObject(biz, CustomEvent.class);
				default:
					break;
			}
		}
		return null;
	}

	/**
	 * 检查传入参数
	 */
	public BaseResp checkIssuedCoupon(String eventId, List<String> phones, String type) {
		if (StringUtil.isEmpty(eventId)) {
			return new BaseResp(-1, ErrorCodeEnum.EVENT_ID_ISEMPTY.getMessage());
		}
		if (StringUtil.isEmpty(type) || !StringUtil.inWithIgnoreCase(type, "one", "all")) {
			return new BaseResp(-1, ErrorCodeEnum.EVENT_USERTYPE_ERROR.getMessage());
		}
		if (phones == null || type.equalsIgnoreCase("one") && phones.size() == 0) {
			return new BaseResp(-1, ErrorCodeEnum.EVENT_USER_ISEMPTY.getMessage());
		}
		
		int checked = eventService.findEventByOid(eventId);
		if (checked == 0) {
			return new BaseResp(-1, ErrorCodeEnum.EVENT_ISOFF.getMessage());
		}
		
		// 判断phones存在的情况,，如果缺少则返回
		if (type.equalsIgnoreCase("one") && phones.size() > 0) {
			List<UserInvestEntity> userInvestByPhones = userInvestService.findUserInvestByPhones(phones);
			//转换成map，方便比较时获取数据
			Map<String, UserInvestEntity> map = new HashMap<String, UserInvestEntity>();
			for (UserInvestEntity entity : userInvestByPhones) {
				map.put(entity.getPhone(), entity);
			}
			for (String phone : phones) {
				if (null == map.get(phone)) {
					return new BaseResp(-1, phone + ErrorCodeEnum.EVENT_USER_PHONE_NOTEXIST);
				}
			}
		}
		
		return new BaseResp();
	}
	
	/**
	 * 检查下发卡券数量
	 * 
	 * @param eventId
	 * @param phones
	 * @param type
	 * @return
	 */
	public BaseResp checkIssuedCouponCount(String eventId, List<String> phones, String type) {

		BaseResp baseResp = checkIssuedCoupon(eventId, phones, type);
		log.info("卡券下发校验结果：{}", baseResp);
		if (baseResp.getErrorCode() != 0) {
			return baseResp;
		}
        int userCount = 0;
        if (type.equalsIgnoreCase("one")) {
            userCount = userInvestService.findUserInvestByPhones(phones).size();
        } else {
            userCount = userInvestService.count();
        }
        log.info("卡券下发用户条数：{}", userCount);
        this.checkCouponBatch(eventId, userCount, baseResp);
		
		return baseResp;
	}

	/**
	 * 后台赠送卡券
	 *
	 * @param eventId 活动Id
	 * @param phones  手机号，当<code>type</code>=one时生效
	 * @param type    all：所有用户,one：单个用户
	 * @return BaseResp
	 */
	public BaseResp issuedCoupon(String operateOid, String eventId, List<String> phones, String type) {
        BaseResp baseResp = new BaseResp();

        //检查是否有未处理完的记录
        Map<String,String> rediscache = HashRedisUtil.hgetall(redis, HashRedisUtil.ISSUE_RECODE);
        if (rediscache != null){
            for (String s : rediscache.keySet()) {
                BatchIssueCouponRedisRecord couponRedisRecord =
                        JSONObject.parseObject(rediscache.get(s), BatchIssueCouponRedisRecord.class);
                if(couponRedisRecord.getStatus().equals("processing")){
                    baseResp.setErrorCode(-1);
                    baseResp.setErrorMessage("有正在处理中的卡券发放，请稍后再试");
                    return baseResp;
                }
            }
        }

        baseResp = checkIssuedCoupon(eventId, phones, type);
        log.info("卡券下发校验结果：{}", baseResp);
        if (baseResp.getErrorCode() != 0) {
            return baseResp;
        }

        List<RuleEntity> ruleList = ruleService.findRuleByEventId(eventId);
        if (CollectionUtils.isEmpty(ruleList)) {// 存在规则约束的情况
            baseResp.setErrorCode(-1);
            baseResp.setErrorMessage("活动:"+ eventId +"没有对应的规则约束");
            return baseResp;
        }

        //根据规则配置将表达式和属性都缓存到map中
        Map<RuleEntity,RuleExpressionProp> ruleExpressionPropMap = buildRuleExpressionProp(ruleList);

        int userCount = 0;
        List<UserInvestEntity> userInvestByPhones = new ArrayList<>();
        if (type.equalsIgnoreCase("one")) {
            userInvestByPhones = userInvestService.findUserInvestByPhones(phones);
            userCount = userInvestByPhones.size();
        } else {
            userCount = userInvestService.count();
        }
        log.info("卡券下发用户条数：{}", userCount);

        boolean isIssued = this.checkCouponBatch(eventId, userCount, baseResp);
        if(!isIssued){
            return baseResp;
        }

        BatchIssueCouponRedisRecord redisRecord = new BatchIssueCouponRedisRecord();
        redisRecord.setEventOid(eventId);
        redisRecord.setOperateOid(operateOid);
        redisRecord.setStartDate(new Date());
        redisRecord.setStatus("processing");
        redisRecord.setIssueCount(userCount);

        String recordOid = UUID.randomUUID().toString();
        Map<String,Object> redisMap = new HashMap<>();
        redisMap.put(recordOid, redisRecord);

        HashRedisUtil.hmset(redis,HashRedisUtil.ISSUE_RECODE, redisMap);
        if(type.equalsIgnoreCase("one")){
            for (UserInvestEntity userInvestByPhone : userInvestByPhones) {
                try {
                    autoIssuedCoupon(ruleExpressionPropMap, userInvestByPhone);
                }catch (Exception e){
                    log.error("自定义卡券下发失败{}",userInvestByPhone.getOid());
                }
            }
        }else{
            String lastOid = "0";
            while(true){
                userInvestByPhones = userInvestService.batchFindUser(lastOid);
                if (CollectionUtils.isEmpty(userInvestByPhones)) {
                    break;
                }
                for (UserInvestEntity userInvestByPhone : userInvestByPhones) {
                    try {
                        autoIssuedCoupon(ruleExpressionPropMap, userInvestByPhone);
                    }catch (Exception e){
                        log.error("自定义卡券下发失败{}",userInvestByPhone.getOid());
                    }
                    lastOid = userInvestByPhone.getOid();
                }
            }
        }
        redisRecord.setEndDate(new Date());
        redisRecord.setStatus("processed");
        HashRedisUtil.hmset(redis, HashRedisUtil.ISSUE_RECODE, redisMap);
        log.info("卡券下发完成");
        return new BaseResp();
	}

	/**
	 * 为投资组下发卡券
	 * @param eventId 下发的卡券id
	 * @param labelCode 组code
	 * @param type 
	 * @return
	 */
	public BaseResp issuedGroupCoupon(String eventId, String labelCode) {
		String type = "one";
		log.info("根据投资人标签下发卡券， 卡券{}， 投资人标签{} ", eventId, labelCode);
		if(StringUtil.isEmpty(labelCode)){
			return new BaseResp(-1, "投资人标签为空，不能下发卡券");
		}
		PageResp<InvestorInfoResp> pages = investorlabelApi.getInvestorByLabelCode(labelCode);
		if(null == pages){
			return new BaseResp(-1, "根据投资人标签查询投资人为空，不能下发卡券");
		}
		if(null != pages && CollectionUtils.isEmpty(pages.getRows())){
			return new BaseResp(-1, "根据投资人标签查询投资人为空，不能下发卡券");
		}
		
		List<InvestorInfoResp> infoList = pages.getRows();
		List<String> phones = new ArrayList<String>();
		for (InvestorInfoResp info : infoList) {
			if(!StringUtil.isEmpty(info.getPhoneNum())){
				phones.add(info.getPhoneNum());				
			}
		}
		
		BaseResp baseResp = checkIssuedCoupon(eventId, phones, type);
		log.info("卡券下发校验结果：{}", baseResp);
		if (baseResp.getErrorCode() != 0) {
			return baseResp;
		}
		List<UserInvestEntity> userInvestByPhones;
		if (type.equalsIgnoreCase("one")) {
			userInvestByPhones = userInvestService.findUserInvestByPhones(phones);
		} else {
			userInvestByPhones = userInvestService.findAllUser();
		}
		log.info("卡券下发用户条数：{}", userInvestByPhones.size());
		// 派发卡券
		for (UserInvestEntity entity : userInvestByPhones) {
			CustomEvent event = new CustomEvent();
			event.setEventId(eventId);
			event.setUserId(entity.getUserId());
			event.setAsync(true);
			eventPublisher.publishEvent(JSONObject.toJSON(event).toString());
		}
		log.info("卡券下发完成！");
		return new BaseResp();
	}
	
	/**
	 * 获取投资人标签列表
	 * @return
	 */
	public List<InvestorLabelResp> getInvestorlabelList(){
		PageResp<InvestorLabelResp> resp = new PageResp<InvestorLabelResp>();
		List<InvestorLabelResp> list = new ArrayList<InvestorLabelResp>();
		try {
			resp = investorlabelApi.getInvestorlabelList();
//			InvestorLabelResp ss = new InvestorLabelResp();
//			ss.setLabelCode("001");
//			ss.setLabelName("dsfdsfsd");
//			list.add(ss);
//			
//			InvestorLabelResp ss1 = new InvestorLabelResp();
//			ss1.setLabelCode("002");
//			ss1.setLabelName("dsfdsfsd2");
//			list.add(ss1);
			
			list = resp.getRows();
		} catch (Exception e) {
			log.error("tulip sdk 访问 mimosa 投资人列表接口异常： ", e);
		}
		
		return list;
	}
	
	public void setRuleParams(BaseEvent event){
		String userId;
		List<String> userGroup = new ArrayList<String>();
//		userGroup.add("001");
		try {
			String firstInvestAmount = "0";
			userId = org.apache.commons.beanutils.BeanUtils.getProperty(event, "userId");
			UserInvestEntity user=userInvestService.findUserInvestByUserId(userId);
			UserCouponRedisUtil.setStr(redis, userId, JSONObject.toJSONString(user));
			//当前投资额度
			String currentOrderAmount = org.apache.commons.beanutils.BeanUtils.getProperty(event, "orderAmount");
			if(user.getFirstInvestAmount().compareTo(BigDecimal.ZERO) == 0){
				//判断首投列表里为空则表示首投，加入首投规则：当前投资额度==首投额度
				firstInvestAmount = currentOrderAmount;
			}
			org.apache.commons.beanutils.BeanUtils.setProperty(event, "firstInvestAmount", firstInvestAmount);
			String eventType = org.apache.commons.beanutils.BeanUtils.getProperty(event, "eventType");
			int friends = 0;
			if(eventType.equals(EventConstants.EVENTTYPE_FIRSTFRIENDINVEST) || eventType.equals(EventConstants.EVENTTYPE_FRIEND)){
				//算上当前的一个被推荐人
				friends = user.getFriends().intValue() + 1;
				log.debug("======================算上当前的一个被推荐人数: 之前有{}, 加上当前1个之后有{}==========", user.getFriends(), friends);
			} else {
				friends = user.getFriends().intValue();
			}
			org.apache.commons.beanutils.BeanUtils.setProperty(event, "friends", friends);
			BigDecimal newOrderAmount = new BigDecimal(currentOrderAmount);
			newOrderAmount = newOrderAmount.add(user.getInvestAmount());
			log.debug("==================当前投资额度={}， 之前的累计额度={}， 得到最新的累计额度={} ==================", currentOrderAmount, user.getInvestAmount(), newOrderAmount);
			org.apache.commons.beanutils.BeanUtils.setProperty(event, "investAmount", newOrderAmount);
			PageResp<InvestorLabelResp> investLabelList = investorlabelApi.getInvestorlabellistByUserId(userId);
			if(null != investLabelList){
				for (InvestorLabelResp label : investLabelList.getRows()) {
					userGroup.add(label.getLabelCode());
				}
			}
//			//TODO 模拟数据当前用户所在多个组
//			userGroup.add("003");
//			userGroup.add("002");
			org.apache.commons.beanutils.BeanUtils.setProperty(event, "userGroup", userGroup);
		} catch (Exception e) {
			log.error("设置用户规则属性值异常：{}", e.getMessage());
		}
		
	}
	
	/**
	 * 获取当前用户所属组（多个）<br>
	 * 通过sdk接口访问mimosa获取组数据
	 * @param userId
	 * @return
	 */
	public List<String> getMyGroupSdk(String userId){
		List<String> userGroup = new ArrayList<String>();
		
		try {
			PageResp<InvestorLabelResp> investLabelList = investorlabelApi.getInvestorlabellistByUserId(userId);
			if(null != investLabelList){
				for (InvestorLabelResp label : investLabelList.getRows()) {
					userGroup.add(label.getLabelCode());
				}
			}
		} catch (Exception e) {
			log.error("sdk获取mimosa接口的用户组（发放对象）异常");
		}
		log.debug("=============用户={}获取的组={}", userId, JSONObject.toJSONString(userGroup));
		return userGroup;
	}
	
	/**
	 * 判断下发的卡券数是否大于用户数
	 * @param eventId
	 * @param investorTotal
	 * @param baseResp
	 * @return
	 */
	private boolean checkCouponAccount(String eventId, int investorTotal, BaseResp baseResp){
		boolean checkaccount = true;
		List<Object[]> couponList = eventService.findCouponInfoByEventId(eventId);
		for (Object[] objects : couponList) {
			int couponAccount = Integer.parseInt(objects[3].toString());
			if(couponAccount < investorTotal){
				baseResp.setErrorCode(-1);
				baseResp.setErrorMessage("卡券张数["+ couponAccount +"]小于用户人数["+ investorTotal +"], 请补充卡券");
				checkaccount = false;
				break;
			}
		}
		log.debug("判断下发的卡券数是否大于用户数: {}", checkaccount);
		return checkaccount;
	}
	
	/**
	 * 检查自定义活动下发的卡券信息
	 * 
	 * @param eventId
	 * @param investorTotal
	 * @param baseResp
	 * @return
	 */
	public boolean checkCouponBatch(String eventId, int investorTotal, BaseResp baseResp){
		boolean checkaccount = true;
		List<Object[]> couponList = eventService.findCouponInfoByEventId(eventId);
		for (Object[] objects : couponList) {
			int couponAccount = Integer.parseInt(objects[3].toString());
			String couponStatus = objects[4].toString();
			if("yes".equals(couponStatus)){
				baseResp.setErrorCode(-1);
				baseResp.setErrorMessage("此次赠送卡券失败, 下发的卡券: " + objects[1].toString() + " 已失效");
				checkaccount = false;
				break;
			}
			if(couponAccount < investorTotal){
				baseResp.setErrorCode(-1);
				baseResp.setErrorMessage("卡券张数["+ couponAccount +"]小于用户人数["+ investorTotal +"], 请补充卡券");
				checkaccount = false;
				break;
			}
		}
		log.debug("判断下发的卡券数是否大于用户数: {}", checkaccount);
		return checkaccount;
	}
	
	public void testReload(String userId){
		RegisterEvent event = new RegisterEvent();
		event.setUserId(userId);
		event.setInvestCount(0);
		long creatTime = System.currentTimeMillis();
		event.setCreateTime(new Timestamp(creatTime));
		event.setPhone("15000052431");
		
		UserInvestEntity userEntity = new UserInvestEntity();
		BeanUtils.copyProperties(event, userEntity, "investCount");
		userEntity.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		userEntity.setType(UserInvestEntity.TYPE_REFEREE);
		userInvestService.createUserInvestEntity(userEntity);
		
		List<RuleEntity> ruleList = ruleService.findRuleListByEventType(event.getEventType());
		List<BizRule> bizRuleList = new ArrayList<BizRule>();
		BizRule bizRule = null;
		for (RuleEntity ruleEntity : ruleList) {
			bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType(ruleEntity.getType()).content(ruleEntity.getExpression()).build();
	 		bizRuleList.add(bizRule);
		}
		this.containerHolder.reload(bizRuleList);
		this.containerHolder.fire(event);
		log.info("testReload container end");
	}
}