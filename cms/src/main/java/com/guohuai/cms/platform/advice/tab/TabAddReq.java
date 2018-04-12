package com.guohuai.cms.platform.advice.tab;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TabAddReq {

	@NotBlank(message = "标签名称不能为空！")
	@Length(max = 60, message = "标签名称长度不能超过60（包含）！")
	private String name;
	
}
