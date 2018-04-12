package com.guohuai.cms.platformext.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface MailExtDao  extends JpaRepository<MailExtEntity, String>, JpaSpecificationExecutor<MailExtEntity>{

}
