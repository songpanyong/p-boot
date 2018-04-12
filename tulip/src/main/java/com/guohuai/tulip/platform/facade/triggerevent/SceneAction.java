package com.guohuai.tulip.platform.facade.triggerevent;

import com.guohuai.rules.event.BaseEvent;

/**
 * 活动场景触发
 * @author mr_gu
 *
 */
public interface SceneAction {
	
	/**
	 * 场景触发公共方法
	 * @param baseEvent
	 */
	void execute(BaseEvent baseEvent);
}
