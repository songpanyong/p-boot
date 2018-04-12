package com.guohuai.cms.platform.share;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShareConfigSingleRep extends BaseResp{
	
	
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
