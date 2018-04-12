package com.guohuai.cms.platform.protocol;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ProtocolAddReq {

	private String oid;
	
	@NotBlank(message = "协议类型不能为空！")
	private String typeId;
	
	@NotBlank(message = "协议内容不能为空！")
	private String content;
}
