package com.guohuai.cms.platform.channel;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChannelAddReq {

	private String oid;
	
	@NotBlank(message = "渠道编号不能为空！")
	@Length(max = 60, message = "渠道编号长度不能超过30（包含）！")
	private String code;
	
	@NotBlank(message = "渠道名称不能为空！")
	@Length(max = 100, message = "渠道名称长度不能超过30（包含）！")
	private String name;
	
}
