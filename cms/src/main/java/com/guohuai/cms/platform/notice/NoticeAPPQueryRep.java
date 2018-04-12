package com.guohuai.cms.platform.notice;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class NoticeAPPQueryRep {

	private String oid, title, linkUrl, linkHtml, subscript, sourceFrom, page, top;
	/**
	 * 发布时间
	 */
	private String releaseTime, releaseTimeFull;
	
	// 上架时间
	private String onShelfTime;
}
