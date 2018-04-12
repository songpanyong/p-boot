package com.guohuai.cms.platform.notice;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.guohuai.cms.component.web.parameter.validation.Enumerations;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class NoticeAddReq {

	private String oid;
	
	@NotBlank(message = "渠道不能为空！")
	private String channelOid;
	
	@NotBlank(message = "标题不能为空！")
	@Length(max = 60, message = "标题长度不能超过60（包含）！")
	private String title;
	
//	@NotBlank(message = "链接不能为空！")
	@Length(max = 250, message = "链接长度不能超过250（包含）！")
	private String linkUrl;
	
	@NotBlank(message = "角标不能为空！")
	@Enumerations(values = {"无","New","Hot"}, message = "角标参数有误！")
	private String subscript;
	
//	@NotBlank(message = "发布来源不能为空！")
	@Length(max = 60, message = "发布来源长度不能超过60（包含）！")
	private String sourceFrom;
	
	/**
	 * 跳转的html
	 */
	private String linkHtml;
}
