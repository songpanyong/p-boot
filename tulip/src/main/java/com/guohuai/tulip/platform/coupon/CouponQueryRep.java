package com.guohuai.tulip.platform.coupon;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CouponQueryRep {

	private String oid, name, description, type, active, createUser, updateUser, payflag, overlap, isdel,amountType,rules,products,weight,disableType;
	private Timestamp createTime, updateTime;
	private BigDecimal couponAmount,upperAmount,lowerAmount,maxRateAmount,investAmount,totalAmount,remainAmount, writeOffTotalAmount;
	private Integer count,useCount,issueCount,disableDate,validPeriod,investTime;//发行总量\使用总量\领用总量\领用多少天后失效
	private Date disableTime;
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
	 * 获取 active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 设置 active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 获取 createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置 createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取 updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置 updateUser
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取 payflag
	 */
	public String getPayflag() {
		return payflag;
	}
	/**
	 * 设置 payflag
	 */
	public void setPayflag(String payflag) {
		this.payflag = payflag;
	}
	/**
	 * 获取 overlap
	 */
	public String getOverlap() {
		return overlap;
	}
	/**
	 * 设置 overlap
	 */
	public void setOverlap(String overlap) {
		this.overlap = overlap;
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
	 * 获取 upperAmount
	 */
	public BigDecimal getUpperAmount() {
		return upperAmount;
	}
	/**
	 * 设置 upperAmount
	 */
	public void setUpperAmount(BigDecimal upperAmount) {
		this.upperAmount = upperAmount;
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
	 * 获取 weight
	 */
	public String getWeight() {
		return weight;
	}
	/**
	 * 设置 weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
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
	 * 获取 updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置 updateTime
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取 lowerAmount
	 */
	public BigDecimal getLowerAmount() {
		return lowerAmount;
	}
	/**
	 * 设置 lowerAmount
	 */
	public void setLowerAmount(BigDecimal lowerAmount) {
		this.lowerAmount = lowerAmount;
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
	 * 获取 totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	/**
	 * 设置 totalAmount
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * 获取 remainAmount
	 */
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	/**
	 * 设置 remainAmount
	 */
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	/**
	 * 获取 count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置 count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取 useCount
	 */
	public Integer getUseCount() {
		return useCount;
	}
	/**
	 * 设置 useCount
	 */
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	/**
	 * 获取 issueCount
	 */
	public Integer getIssueCount() {
		return issueCount;
	}
	/**
	 * 设置 issueCount
	 */
	public void setIssueCount(Integer issueCount) {
		this.issueCount = issueCount;
	}
	/**
	 * 获取 disableDate
	 */
	public Integer getDisableDate() {
		return disableDate;
	}
	/**
	 * 设置 disableDate
	 */
	public void setDisableDate(Integer disableDate) {
		this.disableDate = disableDate;
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
	 * 获取 couponAmount
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}
	/**
	 * 设置 couponAmount
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}
	/**
	 * 获取 isdel
	 */
	public String getIsdel() {
		return isdel;
	}
	/**
	 * 设置 isdel
	 */
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	/**
	 * 获取 disableType
	 */
	public String getDisableType() {
		return disableType;
	}
	/**
	 * 设置 disableType
	 */
	public void setDisableType(String disableType) {
		this.disableType = disableType;
	}
	/**
	 * 获取 disableTime
	 */
	public Date getDisableTime() {
		return disableTime;
	}
	/**
	 * 设置 disableTime
	 */
	public void setDisableTime(Date disableTime) {
		this.disableTime = disableTime;
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
	public BigDecimal getWriteOffTotalAmount() {
		return writeOffTotalAmount;
	}
	public void setWriteOffTotalAmount(BigDecimal writeOffTotalAmount) {
		this.writeOffTotalAmount = writeOffTotalAmount;
	}
	
}
