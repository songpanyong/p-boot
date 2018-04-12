package com.guohuai.cms.platform.mail;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class MailNoNumCTResp extends BaseResp{
	
	private int num;
}
