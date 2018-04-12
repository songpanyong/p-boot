package com.guohuai.tulip.platform.facade.triggerevent;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponEntity;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.eventAnno.CashEvent;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.util.CommonConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class CashService implements SceneAction {
	@Autowired
	private CommonService commonService;
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
		this.onCashAsynch(biz);
		log.info("======模拟facadeService.onCash()去下发卡券=====");
	}
	
	public void onCashAsynch(String biz) {
		CashEvent event = JSONObject.parseObject(biz, CashEvent.class);
		//设置规则参数
		commonService.setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		entity.setCouponId(event.getCouponId());
		entity.setUserId(event.getUserId());
		entity.setUserAmount(event.getUserAmount());
		entity.setOrderAmount(event.getOrderAmount());
		entity.setCreateTime(event.getCreateTime());
		entity.setOrderCode(event.getOrderCode());
		entity.setOrderStatus(event.getOrderStatus());
		entity.setOrderType(EventConstants.EVENTTYPE_CASH);
		couponOrderService.createCouponOrder(entity);
		String couponStatus = UserCouponEntity.COUPON_STATUS_NOUSED;
		if (CommonConstants.ORDER_STATUS.equals(entity.getOrderStatus())) {
			if (StringUtils.isNotBlank(entity.getCouponId())) {// 使用卡券的订单
				couponStatus = UserCouponEntity.COUPON_STATUS_USED;
				userCouponService.useUserCoupon(couponStatus, entity.getCouponId(), event.getUserId());// 解锁
				couponService.updateUseCount(entity.getCouponId(), 1);
			}
			//加载规则
			commonService.sendCouponWithDrools(event, event.getEventType());
		} else {
			if (StringUtils.isNotBlank(entity.getCouponId())) {// 使用卡券的订单
				userCouponService.resetUserCoupon(couponStatus, entity.getCouponId(), event.getUserId());// 解锁
				couponService.updateUseCount(entity.getCouponId(), -1);
			}
		}
	}
	
}
