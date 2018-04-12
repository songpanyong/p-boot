package com.guohuai.cms.platform.information;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class InformationQueryRep {
	
	private String oid, channelOid, title, type, status, publisher, url, content, summary, thumbnailUrl, editor, review, origin, reviewRemark;
	
	private Timestamp createTime, publishTime, editTime, reviewTime;
	
	/**
	 * 是否推荐0：否认 1： 是
	 */
	private Integer isHome;

}
