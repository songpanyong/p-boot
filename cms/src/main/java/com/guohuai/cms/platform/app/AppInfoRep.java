package com.guohuai.cms.platform.app;

import com.guohuai.cms.component.web.BaseRep;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class AppInfoRep extends BaseRep {

	private Object info;
}
