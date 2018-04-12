package com.guohuai.tulip.platform.facade.triggerevent;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.rules.config.BizRule;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponEntity;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.eventAnno.AuthenticationEvent;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.eventAnno.FirstFriendInvestEvent;
import com.guohuai.tulip.platform.eventAnno.FriendEvent;
import com.guohuai.tulip.platform.eventAnno.InvestEvent;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;
import com.guohuai.tulip.util.CommonConstants;
import com.guohuai.tulip.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class InvestmentService implements SceneAction {
	@Autowired
	private RedisTemplate<String, String> redis;
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserInvestService userInvestService;
	@Autowired
	private CouponOrderService couponOrderService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private UserCouponService userCouponService;
	
	@Override
	public void execute(BaseEvent baseEvent) {
		String biz = JSONObject.toJSONString(baseEvent);
		log.info("==========={}", biz);
		this.onInvestmentAsynch(biz);
		log.info("===========模拟facadeService.onSetRealName()去下发卡券=====");
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
		commonService.setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("=============重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FirstFriendInvestEvent firstFriendInvestEvent = new FirstFriendInvestEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId = entity.getUserId();
		Long num = couponOrderService.countByUserId(userId);
		couponOrderService.createCouponOrder(entity);
		if (CommonConstants.ORDER_STATUS.equals(entity.getOrderStatus())) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			if (num < 1) {
				//推荐人存在就给推荐人发送卡券
				if (!StringUtils.isEmpty(userEntity.getFriendId())) {
					firstFriendInvestEvent.setUserId(userEntity.getFriendId());
					commonService.setRuleParams(firstFriendInvestEvent);
					commonService.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
				//加载投资申购规则
				commonService.sendCouponWithDrools(investEvent, investEvent.getEventType());
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
		commonService.setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("================重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FirstFriendInvestEvent firstFriendInvestEvent = new FirstFriendInvestEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId = entity.getUserId();
		Long num = couponOrderService.countByUserId(userId);
		couponOrderService.createCouponOrder(entity);
		if (CommonConstants.ORDER_STATUS.equals(entity.getOrderStatus())) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			if (num < 1) {
				//推荐人存在就给推荐人发送卡券
				if (!StringUtils.isEmpty(userEntity.getFriendId())) {
					firstFriendInvestEvent.setUserId(userEntity.getUserId());
					commonService.setRuleParams(firstFriendInvestEvent);
					commonService.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
			}
			commonService.sendCouponWithDrools(investEvent, investEvent.getEventType());
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
		commonService.setRuleParams(investEvent);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(investEvent.getOrderCode(),investEvent.getOrderType());
		if(null != findOrder){
			log.info("=============重复的订单: {}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		FriendEvent friendEvent = new FriendEvent();
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(investEvent, entity);
		//用户ID
		String userId=investEvent.getUserId();
		couponOrderService.createCouponOrder(entity);
		if (CommonConstants.ORDER_STATUS.equals(entity.getOrderStatus())) {// 订单成功
			UserInvestEntity userEntity = userInvestService.findUserInvestByUserId(userId);
			//推荐人存在就给推荐人发送卡券
			if (!StringUtils.isEmpty(userEntity.getFriendId())) {
				UserInvestEntity friendEntity = userInvestService.findUserInvestByUserId(userEntity.getFriendId());
				friendEvent.setUserId(friendEntity.getUserId());
				friendEvent.setFirstInvestAmount(friendEntity.getFirstInvestAmount());
				friendEvent.setFriends(friendEntity.getFriends());
				friendEvent.setInvestAmount(userEntity.getInvestAmount().add(entity.getOrderAmount()));
				commonService.sendCouponWithDrools(friendEvent, friendEvent.getEventType());				
			}
			//加载投资申购规则
			commonService.sendCouponWithDrools(investEvent, investEvent.getEventType());
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
		commonService.setRuleParams(event);
		CouponOrderEntity findOrder=couponOrderService.findOrderByOrderCode(event.getOrderCode(),event.getOrderType());
		if(null != findOrder){
			log.info("==========重复的订单：{}", JSONObject.toJSONString(findOrder));
			throw new GHException("订单重复!");
		}
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		couponOrderService.createCouponOrder(entity);
		log.info("==========创建优惠券定单 CouponOrderEntity={}",JSONObject.toJSON(entity));
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
					commonService.setRuleParams(firstFriendInvestEvent);
					commonService.sendCouponWithDrools(firstFriendInvestEvent, firstFriendInvestEvent.getEventType());
				}
			}
			userInvestService.updateInvestAmount(entity.getOrderAmount(), entity.getUserId());
			commonService.sendCouponWithDrools(event, event.getEventType());
		}else{
			if (!StringUtil.isEmpty(entity.getCouponId())) {
				CouponOrderEntity couponOrder=couponOrderService.findOrderByCouponIdAndUserId(entity.getCouponId(),entity.getUserId());
				if(null ==couponOrder){
					userCouponService.resetUserCoupon(UserCouponEntity.COUPON_STATUS_NOUSED, entity.getCouponId(), entity.getUserId());// 解锁标识未使用
					couponService.updateUseCount(entity.getCouponId(), -1);
				}
			}
		}
		log.info("==========={}=end to async", event.getEventType());
	}

}
