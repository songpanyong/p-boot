package com.guohuai.cms.platform.partner;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.guohuai.cms.component.web.parameter.validation.Enumerations;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PartnerAddReq {
	
	private String oid;
	
	@NotBlank(message = "渠道不能为空！")
	private String channelOid;
	
	@NotBlank(message = "标题不能为空！")
	@Length(max = 60, message = "标题长度不能超过60（包含）！")
	private String title;
	
	@NotBlank(message = "链接不能为空！")
	@Length(max = 250, message = "链接长度不能超过250（包含）！")
	private String linkUrl;
	
	@NotBlank(message = "图片不能为空！")	
	private String imageUrl;
	
	@NotBlank(message = "是否跳转链接不能为空！")
	@Enumerations(values = {"is","no"}, message = "是否跳转链接参数有误！")
	private String isLink;
	
	@NotBlank(message = "是否跟踪链接不能为空！")
	@Enumerations(values = {"is","no"}, message = "是否跟踪链接参数有误！")
	private String isNofollow;
}
