package com.guohuai.operate.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.operate.system.Sys;

public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

	public List<Role> findByNameAndSystem(String name, Sys system);

	public List<Role> findByOidIn(String[] oids);

}
