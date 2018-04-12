package com.guohuai.cms.platform.push;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class PushQueryRep {
	
	private String oid, title, status, pusher, url, creator, review,
				   reviewRemark, type, summary, pushType, pushUserOid, pushUserAcc, labelCode, labelCodeName;
	
	private Timestamp createTime, pushTime, reviewTime;

}
