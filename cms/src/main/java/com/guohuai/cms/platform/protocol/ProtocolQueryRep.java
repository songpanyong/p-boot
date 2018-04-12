package com.guohuai.cms.platform.protocol;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ProtocolQueryRep {

	private String oid, typeId, content;
	
	private Timestamp createTime, updateTime;
	
}
