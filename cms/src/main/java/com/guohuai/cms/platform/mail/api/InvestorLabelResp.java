package com.guohuai.cms.platform.mail.api;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvestorLabelResp extends BaseResp {
	/**
	 * 标签编码
	 */
	private String labelCode;
	/**
	 * 标签名称
	 */
	private String labelName;
	/**
	 * 标签描述
	 */
	private String labelDesc;
	/**
	 * // 标签是否可用 YES 代表可用 NO 代表不可用
	 */
	private String isOk;
}
