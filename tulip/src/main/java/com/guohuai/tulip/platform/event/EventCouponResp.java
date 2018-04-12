package com.guohuai.tulip.platform.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EventCouponResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6659199097063617918L;

	/**
	 * 发放个数
	 */
	int count;
	
	/**
	 * 类型
	 */
	String couponType;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	
	
}
