package com.guohuai.cms.platform.advice.tab;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class TabSelectRep {
	
	private String id, text;
}
