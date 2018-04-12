package com.guohuai.cms.platformext.push;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class PushExtQueryRep {
	
	private String oid, title, status, pusher, url, creator, review,
				   reviewRemark, type, summary, pushType, pushUserOid, pushUserAcc,labelCode;
	
	private Timestamp createTime, pushTime, reviewTime;

}
