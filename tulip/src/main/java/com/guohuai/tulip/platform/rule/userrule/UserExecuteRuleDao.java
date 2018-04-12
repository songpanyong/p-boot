package com.guohuai.tulip.platform.rule.userrule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserExecuteRuleDao extends JpaRepository<UserExecuteRuleEntity, String>, JpaSpecificationExecutor<UserExecuteRuleEntity> {	

	
	/**
	 * 获取用户触发规则的次数（目的：判断执行次数）
	 * 
	 * @param userId
	 * @param ruleId
	 * @param itemValue
	 * @return
	 */
	@Query(value = "SELECT COUNT(*) FROM t_tulip_user_execute_rule "
			+ "WHERE userId = ?1 AND ruleId = ?2 AND itemValue = ?3 ", nativeQuery = true)
	public int countUserRules(String userId, String ruleId, String itemValue);
	
	
}
