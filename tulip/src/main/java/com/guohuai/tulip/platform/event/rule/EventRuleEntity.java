package com.guohuai.tulip.platform.event.rule;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 事件规则关系实体
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_EVENT_RULE")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventRuleEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 3297827842886697187L;
	/** 活动编号 */
	private String eventId;
	/** 规则编号 */
	private String ruleId;
	/** 创建时间 */
	private Timestamp createTime;
	/**
	 * 获取 eventId
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * 设置 eventId
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
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
	
}
