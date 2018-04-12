package com.guohuai.cms.platform.legal;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LegalAddReq {

	private String oid;
	
	/** 类型名称 */
	@NotBlank(message="类型名称不能为空！")
	private String name;
}
