package com.guohuai.tulip.platform.rule.userrule;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.tulip.util.DateUtil;

@Service
public class UserExecuteRuleService {
	
	@Autowired
	private UserExecuteRuleDao userExecuteRuleDao;
	
	/**
	 * 保存用户触发规则信息
	 * @param entity
	 */
	@Transactional
	public void saveUserExecuteRule(UserExecuteRuleEntity entity){
		userExecuteRuleDao.save(entity);
	}
	
	/**
	 * 检查用户是否执行过相关的规则属性
	 * <p> 返回true：未执行过; false：执行过
	 * @param userId
	 * @param ruleId
	 * @param itemValue
	 * @return
	 */
	public boolean checkUserRuleOfCumulative(String userId, String ruleId, String itemValue){
		boolean checkcount = false;
		int count = userExecuteRuleDao.countUserRules(userId, ruleId, itemValue);
		if(count == 0){
			//未执行过，可以save记录
			UserExecuteRuleEntity entity = new UserExecuteRuleEntity();
			entity.setUserId(userId);
			entity.setRuleId(ruleId);
			entity.setItemValue(itemValue);
			entity.setIsdel("no");
			entity.setCreateTime(DateUtil.getSqlCurrentDate());
			this.saveUserExecuteRule(entity);
			checkcount = true;
		} else {
			checkcount = false;
		}
		
		return checkcount;
	}
}
