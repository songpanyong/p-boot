package com.guohuai.tulip.platform.eventAnno;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("生日活动")
@Component
public class BirthdayEvent extends CommonRuleProp{

	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_BIRTHDAY;
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
}
