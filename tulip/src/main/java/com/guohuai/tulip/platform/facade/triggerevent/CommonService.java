package com.guohuai.tulip.platform.facade.triggerevent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.rules.config.BizRule;
import com.guohuai.rules.config.DroolsContainerHolder;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.event.EventService;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.platform.rule.RuleEntity;
import com.guohuai.tulip.platform.rule.RuleService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;
import com.guohuai.tulip.util.Collections3;
import com.guohuai.tulip.util.KeyRedisUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class CommonService {
	
	@Autowired
	private RedisTemplate<String, String> redis;
	@Autowired
	DroolsContainerHolder containerHolder;
	@Autowired
	private UserInvestService userInvestService;
	@Autowired
	private EventService eventService;
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 设置当前用户的规则参数，最终进入drools匹配规则
	 * 
	 * @param event
	 */
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
			
//			//TODO 模拟数据当前用户所在多个组
//			userGroup.add("003");
//			userGroup.add("002");
			org.apache.commons.beanutils.BeanUtils.setProperty(event, "userGroup", userGroup);
		} catch (Exception e) {
			log.error("设置用户规则属性值异常：{}", e.getMessage());
		}
		
	}
	
	/**
	 * 根据规则去下发卡券（对内存中的初始化规则信息更新，命中多台机器场景）
	 * 
	 * @param event
	 * @param eventType
	 */
	public void sendCouponWithDrools(BaseEvent event, String eventType){
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
			}
			log.info("=========hugo========containerHolder.fire.begin={} ", JSONObject.toJSONString(event));
			this.containerHolder.fire(event);
			log.info("=========hugo========containerHolder.fire.end ");
		} else {
			KeyRedisUtils.loadEventRuleList.remove(eventType);
			log.info("=========hugo========rules= null, {}", JSONObject.toJSONString(eventType));
		}
	}
}
