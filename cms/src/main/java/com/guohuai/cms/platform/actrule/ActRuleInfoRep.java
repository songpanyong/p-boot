package com.guohuai.cms.platform.actrule;

import com.guohuai.cms.component.web.BaseRep;

import lombok.EqualsAndHashCode;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ActRuleInfoRep extends BaseRep {

	private String content;
}
