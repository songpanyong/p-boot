package com.guohuai.tulip.platform.banner;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class BannerQueryRep {

	private String oid, channelOid, title, imageUrl, linkUrl, approveStatus, releaseStatus,
	 			   operator, approveOpe, releaseOpe, remark, toPage;	
	
	private Timestamp approveTime, releaseTime, updateTime;
	/**
	 * 区分链接和调整 0：链接  1：跳转
	 */
	private int isLink;
}
