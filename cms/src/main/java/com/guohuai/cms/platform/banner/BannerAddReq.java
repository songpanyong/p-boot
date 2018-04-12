package com.guohuai.cms.platform.banner;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BannerAddReq {
	
	private String oid;
	
	@NotBlank(message = "渠道不能为空！")
	private String channelOid;
	
	@NotBlank(message = "标题不能为空！")
	@Length(max = 60, message = "标题长度不能超过60（包含）！")
	private String title;
	
	@Length(max = 250, message = "链接长度不能超过250（包含）！")
	private String linkUrl;
	
	@NotBlank(message = "图片不能为空！")	
	private String imageUrl;
	/**
	 * 区分链接和调整 0：链接  1：跳转
	 */
	@NotNull(message = "链接类型不能为空！")
	private int isLink;
	
	private String toPage;
}
