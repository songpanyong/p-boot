package com.guohuai.rules.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent {
	/** 是否异步,true:异步，false:同步 */
	boolean async=true;
}
