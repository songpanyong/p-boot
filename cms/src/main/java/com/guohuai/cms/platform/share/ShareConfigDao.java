package com.guohuai.cms.platform.share;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShareConfigDao extends JpaRepository<ShareConfigEntity, String>, JpaSpecificationExecutor<ShareConfigEntity> {
	
	ShareConfigEntity findByPageCode(String pageCode);
	
}
