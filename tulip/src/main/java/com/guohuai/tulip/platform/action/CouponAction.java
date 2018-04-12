package com.guohuai.tulip.platform.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.rules.action.Action;
import com.guohuai.rules.action.ActionAnno;
import com.guohuai.rules.action.RuleFiredEvent;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;
import com.guohuai.tulip.platform.facade.FacadeNewService;
import com.guohuai.tulip.platform.rule.ruleItem.RuleItemService;
import com.guohuai.tulip.platform.rule.userrule.UserExecuteRuleService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

import lombok.extern.slf4j.Slf4j;
/**
 * 默认的spring bean名称为: couponAction, 规则中就用这个id来找到对应的对象.
 * @author suzhicheng
 */
@Slf4j
@Component
@ActionAnno(value = "发放卡券")
public class CouponAction implements Action {

	@Autowired
	private FacadeNewService facadeNewService;
	@Autowired
	private UserInvestService userInvesService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private RedisTemplate<String, String> redis;
	@Autowired
	private RuleItemService ruleItemService;
	@Autowired
	private UserExecuteRuleService userExecuteRuleService;
	
	/** 累计推荐人数量 **/
	private static final String RULE_PROP_CUMU_FRI = "R001";
	/** 累计投资额度 **/
	private static final String RULE_PROP_CUMU_TOL = "R002";
	
	@Override
	public void execute(RuleFiredEvent ruleFired) {
		log.info("RuleFiredEvent action,ruleFired:{}",JSONObject.toJSON(ruleFired));
		List<CouponEntity> coupons=couponService.findCouponsByCouponIds(ruleFired.getActionParams().split(","));
		BaseEvent eventAnno=ruleFired.getInputEvent();
		//获取用户
		String userId=null;
		String eventType=null;
		try {
			userId = BeanUtils.getProperty(eventAnno, "userId");
			eventType = BeanUtils.getProperty(eventAnno, "eventType");
		} catch (Exception e) {
			log.error("异常：{}", e);
		}
		if(CollectionUtils.isEmpty(coupons)){
			log.info("为当前用户={} 赠送的剩余可用卡券批次={} 为0，不再为此用户下发卡券", userId, ruleFired.getActionParams());
			return;
		}
		
		//将卡券规则存入redis，便于匹配次数，对于累计的规则只执行1次
		if(!this.checkRuleOfCumulative(userId, ruleFired.getRuleId())){
			log.info("=================规则属性(累计推荐人、累计投资额度) 已经执行过一次，不会再次下发卡券而是直接返回===============");
			return;
		}
		
		UserInvestEntity userInvestEntity = new UserInvestEntity();
		String data = UserCouponRedisUtil.getStr(redis, userId);
		if(StringUtil.isEmpty(data)){
			userInvestEntity = userInvesService.findUserInvestByUserId(userId);
		}else {
			userInvestEntity = JSONObject.parseObject(data, UserInvestEntity.class);
		}
		//下发卡券
		for(CouponEntity coupon : coupons){
			try {
				if (CouponEntity.COUPON_TYPE_redPackets.equals(coupon.getType()) 
						&& CouponEntity.AMOUNTTYPE_percentage.equals(coupon.getAmountType()) 
						&& null != coupon.getRemainAmount()) {
					//比例红包生成佣金订单
					facadeNewService.generateCommissionOrder(userInvestEntity, eventType , coupon);
				}else{
					log.info("赠送优惠券，userInvestEntity={},CouponEntity={}", userId, coupon.getOid());
					this.facadeNewService.generateCoupon(userInvestEntity, eventType, coupon);
				}
			} catch (Exception e) {
				log.error("当前卡券批次下发异常，为不影响其他卡券批次下发，捕获异常：{}", e.getMessage());
			}
		}

	}
	
	/**
	 * 检查用户累计规则是否执行过
	 * 
	 * @param userId
	 * @param ruleId
	 * @return
	 */
	public boolean checkRuleOfCumulative(String userId, String ruleId){
		boolean checkRuleCount = true;
		Map<String, Object> ruleItemMap = ruleItemService.findRuleItem(ruleId);
		if(!ruleItemMap.isEmpty()){
			//当前规则设置了规则属性
			String item = null;
			if(null != ruleItemMap.get(CouponAction.RULE_PROP_CUMU_FRI)){
				item = ruleItemMap.get(CouponAction.RULE_PROP_CUMU_FRI).toString();
			} else if(null != ruleItemMap.get(CouponAction.RULE_PROP_CUMU_TOL)){
				item = ruleItemMap.get(CouponAction.RULE_PROP_CUMU_TOL).toString();
			}
			
			if(!StringUtil.isEmpty(item)){
				//如果是累计的规则属性，去和DB中匹配对比
				log.info("=======================累计有关的属性值={}", item);
				checkRuleCount = userExecuteRuleService.checkUserRuleOfCumulative(userId, ruleId, item);
			}
		}
		
		return checkRuleCount;
	}
	
}
