package com.guohuai.cms.platform.partner;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class PartnerAPPQueryRep {

	private String oid, title, imageUrl, linkUrl , isLink, isNofollow;
	private Integer  sorting;
}
