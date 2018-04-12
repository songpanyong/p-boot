package com.guohuai.tulip.platform.rule.ruleProp;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 规则属性
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_RULE_PROP")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RulePropEntity extends UUID {

	public static final String UNIT_NUMBER="number";
	
	public static final String UNIT_DOUBLE="double";
	
	public static final String UNIT_INTERVAL="interval";
	
	public static final String UNIT_PRODUCT="product";
	
	public static final String ISDEL_YES="yes";
	public static final String ISDEL_NO="no";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1972414236116198001L;
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
	 * 获取  field
	 */
	public String getField() {
		return field;
	}
	/**
	 * 设置  field 
	 */
	public void setField(String field) {
		this.field = field;
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
