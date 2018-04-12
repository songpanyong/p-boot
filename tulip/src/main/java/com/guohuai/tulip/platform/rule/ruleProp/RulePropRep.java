package com.guohuai.tulip.platform.rule.ruleProp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RulePropRep {

	private String oid;
	/** 属性名称 */
	String name;
	/** 字段 */
	String field;
	/** 单位 */
	String unit;
	/** 属性应用类型 */
	String type;
	/** 单位值 */
	String unitvalue;
	/** 状态 */
	String isdel;
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
	/**
	 * 获取 field
	 */
	public String getField() {
		return field;
	}
	/**
	 * 设置 field
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * 获取 unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * 设置 unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取 unitvalue
	 */
	public String getUnitvalue() {
		return unitvalue;
	}
	/**
	 * 设置 unitvalue
	 */
	public void setUnitvalue(String unitvalue) {
		this.unitvalue = unitvalue;
	}
	/**
	 * 获取 name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 isdel
	 */
	public String getIsdel() {
		return isdel;
	}
	/**
	 * 设置 isdel
	 */
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
}
