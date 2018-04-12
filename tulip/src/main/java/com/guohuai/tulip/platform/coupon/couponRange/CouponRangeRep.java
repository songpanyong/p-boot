package com.guohuai.tulip.platform.coupon.couponRange;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CouponRangeRep {

	private String batch, productId, productName, type;
	
	private int durationPeriodDays;

	/**
	 * 获取 batch
	 */
	public String getBatch() {
		return batch;
	}

	/**
	 * 设置 batch
	 */
	public void setBatch(String batch) {
		this.batch = batch;
	}

	/**
	 * 获取 productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * 设置 productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 获取 productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置 productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDurationPeriodDays() {
		return durationPeriodDays;
	}

	public void setDurationPeriodDays(int durationPeriodDays) {
		this.durationPeriodDays = durationPeriodDays;
	}
	
}
