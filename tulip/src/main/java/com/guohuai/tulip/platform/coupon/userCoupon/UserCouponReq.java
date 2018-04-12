package com.guohuai.tulip.platform.coupon.userCoupon;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class UserCouponReq {

	private String oid, userId, batch, status, name, description, amountType, type,productId,couponBatch,phone,eventType;
	private Date start, finish;
	private BigDecimal amount;
	private Timestamp leadTime, settlement,useTime;
	private Integer investTime;
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
	 * 获取 couponBatch
	 */
	public String getCouponBatch() {
		return couponBatch;
	}
	/**
	 * 设置 couponBatch
	 */
	public void setCouponBatch(String couponBatch) {
		this.couponBatch = couponBatch;
	}
	/**
	 * 获取 phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置 phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * 获取 investTime
	 */
	public Integer getInvestTime() {
		return investTime;
	}
	/**
	 * 设置 investTime
	 */
	public void setInvestTime(Integer investTime) {
		this.investTime = investTime;
	}
	
	
}
