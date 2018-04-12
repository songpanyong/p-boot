package com.guohuai.operate.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysDao extends JpaRepository<Sys, String>, JpaSpecificationExecutor<Sys>{

}
