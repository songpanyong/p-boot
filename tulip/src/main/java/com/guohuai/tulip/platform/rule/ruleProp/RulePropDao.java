package com.guohuai.tulip.platform.rule.ruleProp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RulePropDao extends JpaRepository<RulePropEntity, String>, JpaSpecificationExecutor<RulePropEntity> {
	
	@Query(value="UPDATE T_TULIP_RULE_PROP SET isdel = ?2 "
			+ "WHERE oid = ?1 AND isdel=?3",nativeQuery=true)
	@Modifying
	public int activeRuleprop(String oid,String toStatus,String fromStatus);

}
