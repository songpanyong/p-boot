package com.guohuai.cms.platform.actrule;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActRuleAddReq {

	private String oid;
	
	@NotBlank(message = "活动规则类型不能为空！")
	private String typeId;
	
	@NotBlank(message = "活动规则内容不能为空！")
	private String content;
}
