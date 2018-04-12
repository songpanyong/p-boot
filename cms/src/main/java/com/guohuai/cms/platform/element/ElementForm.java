package com.guohuai.cms.platform.element;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ElementForm {

	private String oid;
	/** 编号 */
	@NotBlank(message="编号不能为空！")
	private String code;
	
	/** 名称 */
	@NotBlank(message="名称不能为空！")
	private String name;
	
	/** 类型 */
	@NotBlank(message="类型不能为空！")
	private String type;
	
	/** 是否显示 */
	@NotBlank(message="是否显示不能为空！")
	private String isDisplay;
	
	/** 内容 */
	private String content;
}
