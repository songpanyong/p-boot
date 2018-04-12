package com.guohuai.tulip.platform.rule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface RuleDao extends JpaRepository<RuleEntity, String>, JpaSpecificationExecutor<RuleEntity> {	
	
	
	@Modifying
	@Query(value = "DELETE FROM T_TULIP_RULE WHERE oid IN ?1", nativeQuery = true)
	public int deleteRule(String[] eid);

	
	@Query(value = "SELECT b.* FROM T_TULIP_EVENT_RULE a "
			+ "JOIN T_TULIP_RULE b ON a.ruleId=b.oid "
			+ "WHERE a.eventId=?1", nativeQuery = true)
	public List<RuleEntity> findRuleByEventId(String eid);
	
	@Query(value = "SELECT c.*  FROM T_TULIP_COUPON_RULE a "
			+ "JOIN T_TULIP_COUPON b ON a.couponId=b.oid "
			+ "JOIN T_TULIP_RULE c ON a.ruleId=c.oid "
			+ "WHERE b.oid=?1 AND c.type='use'", nativeQuery = true)
	public List<RuleEntity> listByCid(String cid);
	
	/**
	 * 根据卡券id、规则类型查询规则列表
	 * 
	 * @param cid
	 * @param type use使用规则、get领用规则
	 * @param eventType 事件
	 * @return
	 */
	@Query(value = "SELECT c.*  FROM T_TULIP_COUPON_RULE a "
			+ "JOIN T_TULIP_COUPON b ON a.couponId=b.oid "
			+ "JOIN T_TULIP_RULE c ON a.ruleId=c.oid "
			+ "JOIN t_tulip_event_rule er ON er.ruleId=c.oid "
			+ "JOIN t_tulip_event e ON e.oid=er.eventId "
			+ "WHERE b.oid=?1 AND c.type= ?2 AND e.type = ?3 ", nativeQuery = true)
	public List<RuleEntity> findRuleListByCid(String cid, String type, String eventType);
	
	/**
	 * 根据活动类型查询规则oids
	 * @param eventType
	 * @return
	 */
	@Query(value = "SELECT b.* FROM T_TULIP_EVENT e "
			+ "JOIN T_TULIP_EVENT_RULE a ON e.oid = a.eventId "
			+ "JOIN T_TULIP_RULE b ON a.ruleId=b.oid "
			+ "WHERE e.type=?1 AND e.active='on' "
			+ "AND e.isdel='no' AND NOW() BETWEEN e.start AND e.finish ", nativeQuery = true)
	public List<RuleEntity> findRuleListByEventType(String eventType);
	
}
