package com.guohuai.cms.platform.information.type;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class InformationTypeAddReq {
	/**
	 * 资讯名称
	 */
	@NotBlank(message = "资讯类型名称不能为空！")
	@Length(max = 50, message = "资讯类型名称长度不能超过50（包含）！")
	private String name;

}
