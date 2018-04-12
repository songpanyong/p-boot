package com.guohuai.cms.platform.advice;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class AdviceQueryRep {

	private String oid, tabOid, userID, userName, phoneType, content, operator, remark, dealStatus;
	
	private Timestamp createTime, dealTime;
}
