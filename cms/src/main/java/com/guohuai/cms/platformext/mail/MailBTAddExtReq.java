package com.guohuai.cms.platformext.mail;

import com.guohuai.cms.platform.mail.MailBTAddReq;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@lombok.Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MailBTAddExtReq extends MailBTAddReq{

	private String labelCode;	
}
