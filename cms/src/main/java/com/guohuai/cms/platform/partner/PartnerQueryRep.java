package com.guohuai.cms.platform.partner;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class PartnerQueryRep {

	private String oid, channelOid, title, imageUrl, linkUrl, approveStatus, releaseStatus,
	 			   operator, approveOpe, releaseOpe, remark;	
	
	private Timestamp approveTime, releaseTime, updateTime;
	/**
	 * 区分链接和调整 0：链接  1：跳转
	 */
	private String isLink,isNofollow;
}
