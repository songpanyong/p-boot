package com.guohuai.cms.platform.activity;

import java.sql.Timestamp;


import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ActivityQueryRep {
	
	private String oid, channelOid, title, status, publisher, picUrl, creator,
				   review, reviewRemark, location, linkUrl, toPage, beginTime, endTime;
	
	private Timestamp createTime, publishTime, reviewTime;
	
	private int linkType;
		
}
