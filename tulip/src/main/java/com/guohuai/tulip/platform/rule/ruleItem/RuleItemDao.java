package com.guohuai.tulip.platform.rule.ruleItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RuleItemDao extends JpaRepository<RuleItemEntity, String>, JpaSpecificationExecutor<RuleItemEntity> {
	@Query(value = "SELECT * FROM T_TULIP_RULE_ITEM WHERE ruleId=?1", nativeQuery = true)
	public List<RuleItemEntity> listRuleItemEntityByRID(String rid);
	
	@Modifying
	@Query(value = "DELETE FROM T_TULIP_RULE_ITEM WHERE ruleId IN ?1", nativeQuery = true)	
	public int deleteRuleItem(String[] eid);
	

	@Query(value = "SELECT c.* FROM T_TULIP_COUPON_RULE a "
			+ "JOIN T_TULIP_RULE b ON a.ruleId=b.oid  "
			+ "JOIN T_TULIP_RULE_ITEM c ON b.oid=c.ruleId "
			+ "WHERE a.couponId=?1", nativeQuery = true)
	public List<RuleItemEntity> listByCid(String cid);
	
	@Query(value = "SELECT c.* FROM T_TULIP_EVENT_RULE a "
			+ "JOIN T_TULIP_EVENT b ON a.eventId=b.oid  "
			+ "JOIN T_TULIP_RULE_ITEM c ON a.ruleId=c.ruleId "
			+ "WHERE b.type=?1 AND b.active='on' AND b.isdel='yes'", nativeQuery = true)
	public List<RuleItemEntity> listByType(String type);
	
	@Query(value = "SELECT c.* FROM T_TULIP_EVENT_RULE a "
			+ "JOIN T_TULIP_EVENT b ON a.eventId=b.oid  "
			+ "JOIN T_TULIP_RULE_ITEM c ON a.ruleId=c.ruleId "
			+ "WHERE b.oid=?1 AND b.active='on' AND b.isdel='yes'", nativeQuery = true)
	public List<RuleItemEntity> listByEventId(String eventId);
	
	@Query(value = "SELECT a.* FROM T_TULIP_RULE_ITEM a "
			+ "JOIN T_TULIP_RULE b ON a.ruleId=b.oid "
			+ "WHERE b.oid=?1", nativeQuery = true)
	public List<RuleItemEntity> listByRuleId(String eventId);
	
	/**
	 * 获取规则属性
	 * @param ruleId
	 * @return
	 */
	@Query(value = "SELECT ri.* FROM t_tulip_rule r "
			+ "JOIN  t_tulip_rule_item ri ON r.oid = ri.ruleId "
			+ "WHERE ri.ruleId = ?1 ORDER BY propId ASC ", nativeQuery = true)
	public List<RuleItemEntity> findRuleListByRuleId(String ruleId);
	
}
