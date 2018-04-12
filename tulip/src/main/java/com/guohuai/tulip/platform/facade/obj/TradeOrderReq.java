package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.tulip.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TradeOrderReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8641845292917621437L;
	/** 用户ID */
	private String userId;
	/** 卡券ID */
	private String couponId;
	/** 订单号 */
	private String orderCode;
	/** 产品ID */
	private String productId;
	/** 订单状态 */
	private String orderStatus;
	/** 产品时间 */
	private String productName;
	/** 订单类型 */
	private String orderType;
	/** 用哦过户支付金额 */
	private BigDecimal userAmount=BigDecimal.ZERO;
	/** 卡券抵扣金额 */
	private BigDecimal discount=BigDecimal.ZERO;
	/** 订单金额 */
	private BigDecimal orderAmount=BigDecimal.ZERO;
	/** 产品收益 */
	private BigDecimal rateinvestment=BigDecimal.ZERO;
	/** 投资时间 */
	private Integer dueTime = 0;
	/** 创建时间 */
	private Timestamp createTime = DateUtil.getSqlCurrentDate();

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
	 * 获取 discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * 设置 discount
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	 * 获取 rateinvestment
	 */
	public BigDecimal getRateinvestment() {
		return rateinvestment;
	}

	/**
	 * 设置 rateinvestment
	 */
	public void setRateinvestment(BigDecimal rateinvestment) {
		this.rateinvestment = rateinvestment;
	}

	/**
	 * 获取 dueTime
	 */
	public Integer getDueTime() {
		return dueTime;
	}

	/**
	 * 设置 dueTime
	 */
	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
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
