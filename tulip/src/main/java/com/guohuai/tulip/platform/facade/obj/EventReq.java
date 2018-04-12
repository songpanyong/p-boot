package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import com.guohuai.basic.component.ext.web.BaseResp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EventReq extends BaseResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 279678675333809785L;
	/**
	 * 活动类型
	 */
	private String eventType;
	/** 卡券类型 */
	private String couponType;
	/**
	 * 设置 eventType
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * 获取 eventType 
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * 设置 couponType
	 */
	public String getCouponType() {
		return couponType;
	}
	/**
	 * 获取 couponType 
	 */
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	
}
