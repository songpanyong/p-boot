package com.guohuai.tulip.platform.coupon.couponOrder;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CouponOrderReq {
	String couponId, userId, status, type, orderCode;
	Date start, finish;
	int page;
	int rows;

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
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
	}

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
	 * 获取 start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * 设置 start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * 获取 finish
	 */
	public Date getFinish() {
		return finish;
	}

	/**
	 * 设置 finish
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
	}

	/**
	 * 获取 page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置 page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 获取 rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 设置 rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

}
