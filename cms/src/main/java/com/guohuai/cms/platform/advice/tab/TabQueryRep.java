package com.guohuai.cms.platform.advice.tab;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class TabQueryRep {

	private String oid, name;
}
