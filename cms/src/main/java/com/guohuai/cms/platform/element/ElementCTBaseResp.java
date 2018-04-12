package com.guohuai.cms.platform.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class ElementCTBaseResp{

	/** 编号 */
	private String code;
	/** 是否显示 */
	private String isDisplay;
	/** 内容 */
	private String content;
}
