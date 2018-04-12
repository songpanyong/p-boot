package com.guohuai.tulip.platform.coupon.userCoupon;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponDetailResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String phone, name, eventType, status, type;
	
	Timestamp useTime_start, useTime_finish;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getUseTime_start() {
		return useTime_start;
	}

	public void setUseTime_start(Timestamp useTime_start) {
		this.useTime_start = useTime_start;
	}

	public Timestamp getUseTime_finish() {
		return useTime_finish;
	}

	public void setUseTime_finish(Timestamp useTime_finish) {
		this.useTime_finish = useTime_finish;
	}
	
	
}
