package com.guohuai.cms.platform.protocol.type;

import lombok.EqualsAndHashCode;

@lombok.Data
@lombok.Builder
@EqualsAndHashCode(callSuper = false)
public class ProtocolTypeSelectRep {
	
	private String id, text;
}
