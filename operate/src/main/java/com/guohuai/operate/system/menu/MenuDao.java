package com.guohuai.operate.system.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuDao extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {

	public Menu findByOidAndSystem(String oid, String system);

}
