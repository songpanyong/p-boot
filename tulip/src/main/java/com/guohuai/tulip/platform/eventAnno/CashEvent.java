package com.guohuai.tulip.platform.eventAnno;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 提现事件
 * 
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("提现事件")
@Component
public class CashEvent extends CommonRuleProp {
	/** 卡券Id */
	private String couponId;
	/** 订单类型 */
	private String orderType;
	/** 用户支付金额 */
	private BigDecimal userAmount=BigDecimal.ZERO;
	/** 订单时间 */
	private Timestamp createTime;
	/** 订单编号 */
	private String orderCode;
	/** 订单状态 */
	private String orderStatus;
	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_CASH;

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(BigDecimal userAmount) {
		this.userAmount = userAmount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取 eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 设置 eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}
