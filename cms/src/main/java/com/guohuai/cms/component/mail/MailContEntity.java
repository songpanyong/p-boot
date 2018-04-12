package com.guohuai.cms.component.mail;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class MailContEntity{
	private String smsTitle;
	private String smsContent;
}
