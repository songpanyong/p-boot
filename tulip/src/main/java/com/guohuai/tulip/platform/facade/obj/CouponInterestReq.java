package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponInterestReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 42416409100668187L;
	/** 用户ID */
	String userId;
	/** 卡券ID */
	String couponId;
	/** 订单编号 */
	String orderCode;
	/** 天数 */
	Integer days=0;
	/** 年的天数 */
	Integer yearDay=0;
	/** 订单金额 */
	BigDecimal orderAmount=BigDecimal.ZERO;

	/**
	 * 获取 orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 设置 orderCode
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * 获取 days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * 设置 days
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * 获取 yearDay
	 */
	public Integer getYearDay() {
		return yearDay;
	}

	/**
	 * 设置 yearDay
	 */
	public void setYearDay(Integer yearDay) {
		this.yearDay = yearDay;
	}

	/**
	 * 获取 orderAmount
	 */
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	/**
	 * 设置 orderAmount
	 */
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
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

	/**
	 * 获取 couponId
	 */
	public String getCouponId() {
		return couponId;
	}

	/**
	 * 设置 couponId
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

}
