package com.guohuai.tulip.platform.coupon.couponOrder;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CouponOrderRep {
	/** 用户编号、卡券批次号、卡券状态、卡券名称、卡券描述、卡券金额类型、卡券类型、卡券编号、产品使用范围、使用规则说明 */
	private String userId, batch, status, name, description, amountType, type, oid, products, rules;
	/** 卡券有效开始时间、结束时间 */
	private Date start, finish;
	/** 卡券金额、投资限额、最大优惠金额 */
	private BigDecimal amount, investAmount, maxRateAmount;
	/** 领用时间、核销时间、使用时间 */
	private Timestamp leadTime, settlement, useTime;
	/** 最大收益天数 */
	private Integer validPeriod;
	/** 卡券编号 */
	private String couponId;
	/** 产品编码 */
	private String productId;
	/** 产品名称 */
	private String productName;
	/** 订单类型 */
	private String orderType;
	private int dueTime;
	/** 卡券折扣金额 */
	private BigDecimal discount;
	/** 用户支付金额 */
	private BigDecimal userAmount;
	/** 订单金额 */
	private BigDecimal orderAmount;
	private BigDecimal rateinvestment;
	/** 订单生成时间 */
	private Timestamp createTime;
	/** 订单编码 */
	private String orderCode;
	/** 订单状态 */
	private String orderStatus;

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
	 * 获取 name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取 amountType
	 */
	public String getAmountType() {
		return amountType;
	}

	/**
	 * 设置 amountType
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
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
	 * 获取 oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置 oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 获取 products
	 */
	public String getProducts() {
		return products;
	}

	/**
	 * 设置 products
	 */
	public void setProducts(String products) {
		this.products = products;
	}

	/**
	 * 获取 rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * 设置 rules
	 */
	public void setRules(String rules) {
		this.rules = rules;
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
	 * 获取 amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置 amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取 investAmount
	 */
	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	/**
	 * 设置 investAmount
	 */
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	/**
	 * 获取 maxRateAmount
	 */
	public BigDecimal getMaxRateAmount() {
		return maxRateAmount;
	}

	/**
	 * 设置 maxRateAmount
	 */
	public void setMaxRateAmount(BigDecimal maxRateAmount) {
		this.maxRateAmount = maxRateAmount;
	}

	/**
	 * 获取 leadTime
	 */
	public Timestamp getLeadTime() {
		return leadTime;
	}

	/**
	 * 设置 leadTime
	 */
	public void setLeadTime(Timestamp leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * 获取 settlement
	 */
	public Timestamp getSettlement() {
		return settlement;
	}

	/**
	 * 设置 settlement
	 */
	public void setSettlement(Timestamp settlement) {
		this.settlement = settlement;
	}

	/**
	 * 获取 useTime
	 */
	public Timestamp getUseTime() {
		return useTime;
	}

	/**
	 * 设置 useTime
	 */
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	/**
	 * 获取 validPeriod
	 */
	public Integer getValidPeriod() {
		return validPeriod;
	}

	/**
	 * 设置 validPeriod
	 */
	public void setValidPeriod(Integer validPeriod) {
		this.validPeriod = validPeriod;
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

}
