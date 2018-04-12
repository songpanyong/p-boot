package com.guohuai.tulip.platform.eventAnno;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("定时任务活动")
@Component
public class ScheduleEvent extends CommonRuleProp{
	
	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_SCHEDULE;
	
}
