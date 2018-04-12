package com.guohuai.cms.platform.share;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.guohuai.basic.component.ext.web.parameter.validation.Enumerations;

import lombok.Data;

@Data
public class ShareConfigModifyReq {
	
	
	/**
	 * 分享OID
	 */
	@NotEmpty(message = "分享OID不能为空")
	@Length(max = 32, message = "分享OID长度不能超过32")
	private String shareOid;
	
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
