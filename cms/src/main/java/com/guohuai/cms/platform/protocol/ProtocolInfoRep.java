package com.guohuai.cms.platform.protocol;

import com.guohuai.cms.component.web.BaseRep;

import lombok.EqualsAndHashCode;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ProtocolInfoRep extends BaseRep {

	private String content;
}
