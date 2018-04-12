package com.guohuai.tulip.platform.rule.ruleItem;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 规则项关系表
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_RULE_ITEM")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RuleItemEntity extends UUID {

	private static final long serialVersionUID = 1972414236116198001L;
	/** 规则编号 */
	String ruleId;
	/** 属性编号  */
	String propId;
	/** 属性值 */
	String value;
	/** 表达式 */
	String expression;
	/**
	 * 获取 ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}
	/**
	 * 设置 ruleId
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	/**
	 * 获取 propId
	 */
	public String getPropId() {
		return propId;
	}
	/**
	 * 设置 propId
	 */
	public void setPropId(String propId) {
		this.propId = propId;
	}
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

}
