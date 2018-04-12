package com.guohuai.cms.platform.actrule.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActRuleTypeDao extends JpaRepository<ActRuleTypeEntity, String>, JpaSpecificationExecutor<ActRuleTypeEntity> {

}
