package com.guohuai.tulip.platform.coupon.userCoupon;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserCouponRep {

	private String userId, couponBatch, status, name, description, amountType, type,oid,products,rules,eventId,eventTitle,eventType,phone;
	private BigDecimal amount,investAmount,maxRateAmount, writeOffAmount;
	private Timestamp leadTime, settlement,useTime,start, finish;
	private Integer validPeriod,investTime;
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
	public String getCouponBatch() {
		return couponBatch;
	}
	/**
	 * 设置 batch
	 */
	public void setCouponBatch(String couponBatch) {
		this.couponBatch = couponBatch;
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
	public Timestamp getStart() {
		return start;
	}
	/**
	 * 设置 start
	 */
	public void setStart(Timestamp start) {
		this.start = start;
	}
	/**
	 * 获取 finish
	 */
	public Timestamp getFinish() {
		return finish;
	}
	/**
	 * 设置 finish
	 */
	public void setFinish(Timestamp finish) {
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
	 * 设置 eventId
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * 获取 eventId 
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	/**
	 * 设置 eventTitle
	 */
	public String getEventTitle() {
		return eventTitle;
	}
	/**
	 * 设置 eventTitle 
	 */
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
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
	public BigDecimal getWriteOffAmount() {
		return writeOffAmount;
	}
	public void setWriteOffAmount(BigDecimal writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}
	
}
