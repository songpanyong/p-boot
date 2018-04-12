package com.guohuai.tulip.platform.facade.obj;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IssuedCouponReq {
	
	/** 用户ID */
	private String userId;
	/** 活动ID */
	private String eventId;
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
	 * 获取 userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
