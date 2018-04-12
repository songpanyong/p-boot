package com.guohuai.tulip.platform.facade.triggerevent;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.eventAnno.FriendEvent;
import com.guohuai.tulip.platform.eventAnno.RegisterEvent;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class RegisterService implements SceneAction {
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public void execute(BaseEvent baseEvent) {
		String biz = JSONObject.toJSONString(baseEvent);
		log.info("==========={}", biz);
		this.onRegisterAsynch(biz);
		log.info("======模拟facadeService.onRegister()去下发卡券=====");
	}

	public void onRegisterAsynch(String biz) {
		RegisterEvent event = JSONObject.parseObject(biz, RegisterEvent.class);
		FriendEvent friendEvent=new FriendEvent();
		UserInvestEntity entity = new UserInvestEntity();
		BeanUtils.copyProperties(event, entity, "investCount");
		if (StringUtils.isNotBlank(entity.getFriendId())) {// 如果存在推荐人的情况
			friendEvent.setUserId(entity.getFriendId());
			commonService.setRuleParams(friendEvent);
			commonService.sendCouponWithDrools(friendEvent, friendEvent.getEventType());
		}
		UserCouponRedisUtil.setStr(redis, entity.getUserId(), JSONObject.toJSONString(entity));
		//加载规则
		commonService.sendCouponWithDrools(event, event.getEventType());
		log.info("=============={}=end to async", event.getEventType());
	}
}
