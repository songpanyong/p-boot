package com.guohuai.cms.platform.information.type;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class InformationTypeSelectRep {

	private String id, text;
	
}
