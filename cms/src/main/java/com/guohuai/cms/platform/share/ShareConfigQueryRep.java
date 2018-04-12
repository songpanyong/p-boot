package com.guohuai.cms.platform.share;

import lombok.Data;

@Data
public class ShareConfigQueryRep {
	
	/**
	 * oid
	 */
	private String shareOid;
	
	/**
	 * 页面CODE
	 */
	private String pageCode;
	
	/**
	 * 页面类型
	 */
	private String pageType;
	
	/**
	 * 页面名称
	 */
	private String pageName;
	
	/**
	 * 链接
	 */
	private String shareUrl;
	
	/**
	 * 分享标题
	 */
	private String shareTitle;
	
	/**
	 * 分享文字
	 */
	private String shareWords;
}
