package com.guohuai.cms.platform.information;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class InformationAddReq {
	
	private String oid;
	
	@NotBlank(message = "渠道不能为空！")
	private String channelOid;
	
	@NotBlank(message = "资讯标题不能为空！")
	@Length(max = 100, message = "资讯标题长度不能超过100（包含）！")
	private String title;
	
	@NotBlank(message = "资讯类型不能为空！")
	@Length(max = 50, message = "资讯类型长度不能超过50（包含）！")
	private String type;
	
	@NotBlank(message = "资讯摘要不能为空！")
	@Length(max = 200, message = "资讯摘要长度不能超过200（包含）！")
	private String summary;
	
	/**
	 * 跳转的html
	 */
	private String content;
	
	private String thumbnailUrl;
	
	@Length(max = 200, message = "文章来源长度不能超过200（包含）！")
	private String origin;
	
	@Length(max = 200, message = "链接长度不能超过200（包含）！")
	private String url;
	
}
