package com.guohuai.cms.platform.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MailDao extends JpaRepository<MailEntity, String>, JpaSpecificationExecutor<MailEntity>{
	
}
