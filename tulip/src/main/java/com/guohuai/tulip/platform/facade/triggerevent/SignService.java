package com.guohuai.tulip.platform.facade.triggerevent;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.eventAnno.SignEvent;
import com.guohuai.tulip.platform.signin.SignInEntity;
import com.guohuai.tulip.platform.signin.SignInService;
import com.guohuai.tulip.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class SignService implements SceneAction {
	@Autowired
	private CommonService commonService;
	@Autowired
	private SignInService signInService;
	
	@Override
	public void execute(BaseEvent baseEvent) {
		String biz = JSONObject.toJSONString(baseEvent);
		log.info("==========={}", biz);
		this.onSignAsynch(biz);
		log.info("======模拟facadeService.onSetRealName()去下发卡券=====");
	}
	
	public void onSignAsynch(String biz) {
		SignEvent event = JSONObject.parseObject(biz, SignEvent.class);
		//设置规则参数
		commonService.setRuleParams(event);
		SignInEntity entity = new SignInEntity();
		//检测用户是否签到
		if (!checkSignIn(event.getUserId())) {
			entity.setUserId(event.getUserId());
			entity.setSignInTime(DateUtil.getSqlCurrentDate());
			entity.setSignDate(DateUtil.getSqlDate());
			entity = this.signInService.createSignInEntity(entity);
			//加载规则
			commonService.sendCouponWithDrools(event,  event.getEventType());
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
			log.info("=============用户已签到userId={}", userId);
			return true;
		} else {
			return false;
		}
	}
}
