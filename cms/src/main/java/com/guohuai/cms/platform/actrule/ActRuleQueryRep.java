package com.guohuai.cms.platform.actrule;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ActRuleQueryRep {

	private String oid, typeId, content;
	
	private Timestamp createTime, updateTime;
	
}
