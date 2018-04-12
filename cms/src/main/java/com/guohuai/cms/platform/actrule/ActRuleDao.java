package com.guohuai.cms.platform.actrule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ActRuleDao extends JpaRepository<ActRuleEntity, String>, JpaSpecificationExecutor<ActRuleEntity> {

	@Query(value = "FROM ActRuleEntity WHERE actRuleType.id = ?1")
	public ActRuleEntity findByTypeId(String typeId);
}
