package com.guohuai.tulip.platform.coupon.couponOrder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动-实体
 * 
 * @author suzhicheng
 */
@Entity
@Table(name = "T_TULIP_COUPON_ORDER")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CouponOrderEntity extends UUID {
	
	private static final long serialVersionUID = -7409239546231897797L;
	/** 订单类型--投资 */
	public static final String ORDERTYPE_invest="invest";
	/** 订单类型--赎回 */
	public static final String ORDERTYPE_redeem="redeem";
	
	/** 订单状态--成功 */
	public static final String ORDERSTATUS_success="success";
	/** 订单状态--失败 */
	public static final String ORDERSTATUS_fail="fail";
	
	/** 已--生成佣金订单 */
	public static final String isMakedCommisOrder_yes="yes";
	/** 未--生成佣金订单 */
	public static final String isMakedCommisOrder_no="no";
	
	/** 卡券Id */
	String couponId;
	/** 产品Id */
	String productId;
	/** 产品名称 */
	String productName;
	/** 用户Id */
	String userId;
	/** 订单类型 */
	String orderType;
	/** 期限 */
	int dueTime;
	/** 卡券抵扣金额 */
	BigDecimal discount=BigDecimal.ZERO;
	/** 用户支付金额 */
	BigDecimal userAmount=BigDecimal.ZERO;
	/** 订单金额 */
	BigDecimal orderAmount=BigDecimal.ZERO;
	/** 产品收益 */
	BigDecimal rateinvestment=BigDecimal.ZERO;
	/** 订单时间 */
	Timestamp createTime;
	/** 订单号 */
	String orderCode;
	/** 订单状态 */
	String orderStatus;
	
	/** 是否已经生成佣金订单 */
	String isMakedCommisOrder;

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
	 * 获取 dueTime
	 */
	public int getDueTime() {
		return dueTime;
	}

	/**
	 * 设置 dueTime
	 */
	public void setDueTime(int dueTime) {
		this.dueTime = dueTime;
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

	public String getIsMakedCommisOrder() {
		return isMakedCommisOrder;
	}

	public void setIsMakedCommisOrder(String isMakedCommisOrder) {
		this.isMakedCommisOrder = isMakedCommisOrder;
	}

}
