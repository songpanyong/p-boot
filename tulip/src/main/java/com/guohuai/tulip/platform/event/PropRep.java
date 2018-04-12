package com.guohuai.tulip.platform.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 规则属性
 * @author suzhicheng
 *
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PropRep implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4135966523956991870L;
	String oid;
	String value;
	String expression;
	/**
	 * 获取 value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置 value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取 expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * 设置 expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	/**
	 * 获取 oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * 设置 oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
}
