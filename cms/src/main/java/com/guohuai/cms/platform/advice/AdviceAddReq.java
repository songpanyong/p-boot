package com.guohuai.cms.platform.advice;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AdviceAddReq {

	@NotBlank(message = "手机号不能为空！")
	@Length(max = 11, message = "手机号长度不对！")
	private String userID;
	
	@Length(min = 0, max = 15, message = "姓名长度不对！")
	private String userName;
	
	@Length(min = 0, max = 20, message = "手机类型长度不对！")
	private String phoneType;
	
	@NotBlank(message = "意见反馈内容不能为空！")
	@Length(max = 250, message = "意见反馈内容长度不能超过250个字！")
	private String content;
}
