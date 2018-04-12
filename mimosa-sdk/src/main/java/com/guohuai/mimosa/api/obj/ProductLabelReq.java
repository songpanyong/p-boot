package com.guohuai.mimosa.api.obj;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 产品表情请求参数
 * 
 * @author suzhicheng
 *
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductLabelReq extends MimosaSDKReq{
	
	
	private static final long serialVersionUID = -6199927427820970510L;
	private String labelType;

	/**
	 * 获取 labelType
	 */
	public String getLabelType() {
		return labelType;
	}

	/**
	 * 设置 labelType
	 */
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

}
