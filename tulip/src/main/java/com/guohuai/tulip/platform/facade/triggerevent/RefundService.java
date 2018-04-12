package com.guohuai.tulip.platform.facade.triggerevent;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderService;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.eventAnno.RedeemEvent;
import com.guohuai.tulip.platform.eventAnno.RefundEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class RefundService implements SceneAction {
	@Autowired
	private CommonService commonService;
	@Autowired
	private CouponOrderService couponOrderService;
	
	@Override
	public void execute(BaseEvent baseEvent) {
		String biz = JSONObject.toJSONString(baseEvent);
		log.info("==========={}", biz);
		this.onRefundAsynch(biz);
		log.info("======模拟facadeService.onRefund()去下发卡券=====");
	}
	
	public void onRefundAsynch(String biz) {
		RefundEvent event = JSONObject.parseObject(biz, RefundEvent.class);
		//设置规则参数
		commonService.setRuleParams(event);
		CouponOrderEntity entity = new CouponOrderEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setOrderType(EventConstants.EVENTTYPE_REFUND);
		couponOrderService.createCouponOrder(entity);
		//加载规则
		commonService.sendCouponWithDrools(event, event.getEventType());
	}
	
}
