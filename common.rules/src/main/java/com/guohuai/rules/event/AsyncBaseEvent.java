package com.guohuai.rules.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsyncBaseEvent {
	BaseEvent baseEvent;
}
