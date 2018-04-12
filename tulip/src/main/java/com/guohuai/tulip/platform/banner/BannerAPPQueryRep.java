package com.guohuai.tulip.platform.banner;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class BannerAPPQueryRep {

	private String oid, title, imageUrl, linkUrl,toPage;
	/**
	 * 区分链接和调整 0：链接  1：跳转
	 */
	private int isLink;
}
