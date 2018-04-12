package com.guohuai.tulip.platform.eventAnno;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;
import com.guohuai.rules.event.BaseEvent;
import com.guohuai.rules.event.EventAnno;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 流标事件
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@EventAnno("流标事件")
@Component
public class InvalidBidsEvent extends BaseEvent{
	/** 产品ID */
	private String productId;
	/** 产品名称 */
	private String productName;
	/** 用户Id */
	private String userId;
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
	private String eventType = EventConstants.EVENTTYPE_INVALIDBIDS;
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
	 * 获取 orderType
	 */
	public String getOrderType() {
		return orderType;
	}
	/**
	 * 设置 orderType
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	/**
	 * 获取 userAmount
	 */
	public BigDecimal getUserAmount() {
		return userAmount;
	}
	/**
	 * 设置 userAmount
	 */
	public void setUserAmount(BigDecimal userAmount) {
		this.userAmount = userAmount;
	}
	/**
	 * 获取 createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * 获取 orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置 orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
