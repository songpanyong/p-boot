package com.guohuai.cms.platform.notice;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class NoticeQueryRep {

	private String oid, channelOid, title, linkUrl, linkHtml, subscript, sourceFrom, page, top, approveStatus, remark, releaseStatus,
	   operator, approveOpe, releaseOpe;	

	private Timestamp approveTime, releaseTime, updateTime;
	
	private Date onShelfTime;
}
