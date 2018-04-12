package com.guohuai.cms.platform.legal.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.cms.platform.legal.LegalEntity;

public interface LegalFileDao extends JpaRepository<LegalFileEntity, String>, JpaSpecificationExecutor<LegalFileEntity> {

	public List<LegalFileEntity> findByTypeAndStatus(LegalEntity type, String status);
	
	public List<LegalFileEntity> findByName(String name);
	
	public List<LegalFileEntity> findByType(LegalEntity type);
}
