package com.guohuai.tulip.platform.gatewayrequestlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GatewayRequestLogDao extends JpaRepository<GatewayRequestLogEntity, String>, JpaSpecificationExecutor<GatewayRequestLogEntity> {
	
}
