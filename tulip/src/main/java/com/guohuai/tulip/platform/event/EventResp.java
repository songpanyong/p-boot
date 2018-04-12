package com.guohuai.tulip.platform.event;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisInfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EventResp extends BaseResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6659199097063617918L;

	/**
	 * 剩余红包个数
	 */
	int remainCount;

	/**
	 * 奖励条件
	 */
	List<EventCouponResp> rrList = new ArrayList<EventCouponResp>();
	
	
	List<UserCouponRedisInfo> couponList = new ArrayList<UserCouponRedisInfo>();

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public List<EventCouponResp> getRrList() {
		return rrList;
	}

	public void setRrList(List<EventCouponResp> rrList) {
		this.rrList = rrList;
	}

	public List<UserCouponRedisInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<UserCouponRedisInfo> couponList) {
		this.couponList = couponList;
	}
	
}
