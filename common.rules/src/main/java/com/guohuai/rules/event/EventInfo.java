package com.guohuai.rules.event;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventInfo {
	String eventId;
	String eventClass;
	String eventName;
	List<EventAttr> attrList;
}
