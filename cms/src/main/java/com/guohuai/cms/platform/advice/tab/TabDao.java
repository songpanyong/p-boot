package com.guohuai.cms.platform.advice.tab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TabDao extends JpaRepository<TabEntity, String>, JpaSpecificationExecutor<TabEntity>{

	public TabEntity findByName(String name);
}
