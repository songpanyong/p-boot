package com.guohuai.operate.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.operate.system.Sys;

public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {

	public Admin findByAccountAndSystem(String account, Sys system);

	public List<Admin> findByOidIn(String[] oids);

	public List<Admin> findByOidInAndStatusIn(String[] oids, String[] status);

}
