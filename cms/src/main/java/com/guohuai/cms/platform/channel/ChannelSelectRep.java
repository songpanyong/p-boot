package com.guohuai.cms.platform.channel;

import lombok.EqualsAndHashCode;

@lombok.Data
@lombok.Builder
@EqualsAndHashCode(callSuper = false)
public class ChannelSelectRep {
	
	private String id, text;
}
