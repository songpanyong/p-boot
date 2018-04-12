package com.guohuai.operate.admin.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminLogDao extends JpaRepository<AdminLog, String>, JpaSpecificationExecutor<AdminLog>{

}
