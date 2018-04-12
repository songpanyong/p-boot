package com.guohuai.cms.component.userUtil;

import lombok.NoArgsConstructor;
@lombok.Data
@NoArgsConstructor
public class RedisUserinfo {
	
	/** 个推设备ID */
	String clientId;
	/** 个推设备系统 ios android */
	String clientSys;
}
