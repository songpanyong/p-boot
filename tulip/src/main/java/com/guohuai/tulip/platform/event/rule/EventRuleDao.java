package com.guohuai.tulip.platform.event.rule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventRuleDao extends JpaRepository<EventRuleEntity, String>, JpaSpecificationExecutor<EventRuleEntity> {
	
	@Query(value = "SELECT * FROM T_TULIP_EVENT_RULE WHERE eventId=?1",nativeQuery = true)
	public List<EventRuleEntity> listEventRuleEntityByEID(String eid);
	
	@Modifying
	@Query(value = "DELETE FROM T_TULIP_EVENT_RULE WHERE eventId = ?1", nativeQuery = true)
	public int deleteEventRule(String eid);
}
