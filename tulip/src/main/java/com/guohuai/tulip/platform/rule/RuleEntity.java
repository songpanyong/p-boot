package com.guohuai.tulip.platform.rule;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动-实体
 * 
 * @author tidecc
 */
@Entity
@Table(name = "T_TULIP_RULE")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RuleEntity extends UUID{

	private static final long serialVersionUID = 1972414236116198001L;
	
	/** 权重--逻辑与 */
	public static final String WEIGHT_AND = "and";
	/** 权重--逻辑与 表达式 */
	public static final String WEIGHT_AND_EXPRESSION = "&&";
	/** 权重--逻辑或 表达式*/
	public static final String WEIGHT_OR_EXPRESSION = "||";
	/** 权重--逻辑或 */
	public static final String WEIGHT_OR = "or";
	
	/** 规则类型-- 领用规则 */
	public static final String RULE_DESC_GET = "领用规则";
	/** 规则类型-- 使用规则 */
	public static final String RULE_DESC_USE = "使用规则";
	/** 规则类型-- use */
	public static final String RULE_TYPE_USE = "use";
	/** 规则类型-- get */
	public static final String RULE_TYPE_GET = "get";
	
	/** 类型 */
	String type;
	/** 规则权重 */
	String weight;
	/** 规则表达式 */
	String expression;
	/** 创建时间 */
	Timestamp createTime=new Timestamp(System.currentTimeMillis());
	/** Action名称 */
	String  actionName;
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
	 * 获取 weight
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * 设置 weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
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
	 * 获取 createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * 设置 actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
}
