package com.guohuai.cms.platform.mail;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@lombok.Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MailBTAddReq {
	
	private String oid;
	
	private String phone;
	
	@NotBlank(message = "类型不能为空！")	
	private String mailType;
	
	@NotBlank(message = "标题不能为空！")	
	private String mesTitle;
	
	@NotBlank(message = "内容不能为空！")	
	private String mesContent;
	
	private String remark;	
	
	private String labelCode;
}
