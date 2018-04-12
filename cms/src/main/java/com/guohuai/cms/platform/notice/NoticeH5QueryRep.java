package com.guohuai.cms.platform.notice;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class NoticeH5QueryRep {

	private String oid, title, subscript, linkUrl;
	/**
	 * 发布时间
	 */
	private String releaseTime;
}
