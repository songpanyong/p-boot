package com.guohuai.tulip.platform.gatewayrequestlog;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GatewayRequestLogService {
	@Autowired
	GatewayRequestLogDao gatewayRequestLogDao;
	
	public GatewayRequestLogEntity saveGatewayRequestLog(GatewayRequestLogEntity entity){
		return this.gatewayRequestLogDao.save(entity);
	}
}
