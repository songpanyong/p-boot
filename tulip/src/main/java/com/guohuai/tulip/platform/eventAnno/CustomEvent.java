package com.guohuai.tulip.platform.eventAnno;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义事件
 * 
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("自定义事件")
@Component
public class CustomEvent extends CommonRuleProp {
	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_CUSTOM;
	/** 事件Id */
	private String eventId;
	
	/**
	 * 获取 eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 设置 eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

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

}
