package com.guohuai.cms.platform.share;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.guohuai.basic.component.ext.web.parameter.validation.Enumerations;

import lombok.Data;

@Data
public class ShareConfigAddReq {
	/**
	 * 页面CODE
	 */
	@Enumerations(values = {ShareConfigEntity.SHARECONFIG_pageCode_invite, ShareConfigEntity.SHARECONFIG_pageCode_regist, 
			ShareConfigEntity.SHARECONFIG_pageCode_specialSubject, ShareConfigEntity.SHARECONFIG_pageCode_else}, message = "页面CODE不存在")
	private String pageCode;
	
	/**
	 * 页面类型
	 */
	@NotEmpty(message = "页面类型不能为空")
	@Length(max = 50, message = "页面类型字符长度不能超过50")
	private String pageType;
	
	/**
	 * 页面名称
	 */
	@NotEmpty(message = "页面名称不能为空")
	@Length(max = 50, message = "页面名称字符长度不能超过50")
	private String pageName;
	
	/**
	 * 链接
	 */
	@NotEmpty(message = "链接不能为空")
	@Length(max = 200, message = "链接字符长度不能超过200")
	private String shareUrl;
	
	/**
	 * 分享标题
	 */
	@NotEmpty(message = "分享标题不能为空")
	@Length(max = 50, message = "分享标题字符长度不能超过50")
	private String shareTitle;
	
	/**
	 * 分享文字
	 */
	@NotEmpty(message = "分享文字不能为空")
	@Length(max = 50, message = "分享文字字符长度不能超过50")
	private String shareWords;
}
