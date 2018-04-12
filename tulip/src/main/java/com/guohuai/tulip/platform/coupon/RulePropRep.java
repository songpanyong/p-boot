package com.guohuai.tulip.platform.coupon;


import com.guohuai.tulip.platform.event.PropRep;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RulePropRep extends PropRep {
	private static final long serialVersionUID = 8010905828030095679L;
	private String desc;
	/**
	 * 获取 desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置 desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
