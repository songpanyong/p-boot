package com.guohuai.cms.platform.actrule.type;

import lombok.EqualsAndHashCode;

@lombok.Data
@lombok.Builder
@EqualsAndHashCode(callSuper = false)
public class ActRuleTypeSelectRep {
	
	private String id, text;
}
