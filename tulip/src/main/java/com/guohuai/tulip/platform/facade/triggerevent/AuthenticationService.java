package com.guohuai.tulip.platform.facade.triggerevent;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.eventAnno.AuthenticationEvent;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class AuthenticationService implements SceneAction {
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserInvestService userInvestService;
	
	@Override
	public void execute(BaseEvent baseEvent) {
		String biz = JSONObject.toJSONString(baseEvent);
		log.info("==========={}", biz);
		this.onSetRealNameAsynch(biz);
		log.info("======模拟facadeService.onSetRealName()去下发卡券=====");
	}
	
	public void onSetRealNameAsynch(String biz) {
		AuthenticationEvent event = JSONObject.parseObject(biz, AuthenticationEvent.class);
		//设置规则参数
		commonService.setRuleParams(event);
		UserInvestEntity entity = new UserInvestEntity();
		BeanUtils.copyProperties(event, entity);
		userInvestService.updateReferee(entity.getUserId(), entity.getName(), entity.getBirthday());
		//加载规则
		commonService.sendCouponWithDrools(event, event.getEventType());
	}

}
