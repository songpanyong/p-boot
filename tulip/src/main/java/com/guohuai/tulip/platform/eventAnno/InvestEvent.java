package com.guohuai.tulip.platform.eventAnno;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;
import com.guohuai.tulip.platform.coupon.couponOrder.CouponOrderEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 投资申购事件
 * 
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("投资申购事件")
@Component
public class InvestEvent extends CommonRuleProp {
	/** 国槐基线 */
	public static final String systemType_GH="GH";
	/** 掌悦理财 */
	public static final String systemType_ZY="ZY";
	/** 金猪理财 */
	public static final String systemType_JZ="JZ";
	/** 天金所 */
	public static final String systemType_TJS="TJS";
	
	/** 卡券Id */
	private String couponId;
	/** 产品名称 */
	private String productName;
	/** 订单类型 */
	private String orderType;
	/** 期限 */
	private int dueTime;
	/** 卡券抵扣金额 */
	private BigDecimal discount=BigDecimal.ZERO;
	/** 用户支付金额 */
	private BigDecimal userAmount=BigDecimal.ZERO;
	/** 产品收益 */
	private BigDecimal rateinvestment=BigDecimal.ZERO;
	/** 订单时间 */
	private Timestamp createTime;
	/** 订单编号 */
	private String orderCode;
	/** 订单状态 */
	private String orderStatus;
	
	/** 是否已经生成佣金订单 */
	private String isMakedCommisOrder=CouponOrderEntity.isMakedCommisOrder_no;

	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_INVESTMENT;
	
	/** 系统类型 */
	private String systemType="GH";
	
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public int getDueTime() {
		return dueTime;
	}

	public void setDueTime(int dueTime) {
		this.dueTime = dueTime;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(BigDecimal userAmount) {
		this.userAmount = userAmount;
	}

	public BigDecimal getRateinvestment() {
		return rateinvestment;
	}

	public void setRateinvestment(BigDecimal rateinvestment) {
		this.rateinvestment = rateinvestment;
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

	public String getIsMakedCommisOrder() {
		return isMakedCommisOrder;
	}

	public void setIsMakedCommisOrder(String isMakedCommisOrder) {
		this.isMakedCommisOrder = isMakedCommisOrder;
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
	 * 获取 systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * 设置 systemType
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

}
