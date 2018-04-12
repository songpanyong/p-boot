package com.guohuai.cms.platform.element;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ElementDao extends JpaRepository<ElementEntity, String>, JpaSpecificationExecutor<ElementEntity> {

	ElementEntity findByCode(String code);
}
