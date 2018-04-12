package com.guohuai.cms.platform.advice;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AdviceRemarkReq {
	
	@NotBlank(message = "意见主键不能为空！")
	private String oid;
	
	@NotBlank(message = "备注不能为空！")
	@Length(max = 250, message = "备注长度不能超过250（包含）！")
	private String remark;
}
