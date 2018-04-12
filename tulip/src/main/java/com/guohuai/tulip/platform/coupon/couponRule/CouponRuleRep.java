package com.guohuai.tulip.platform.coupon.couponRule;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class CouponRuleRep {

	private String oid, name, description, type, active, createUser, updateUser, payflag, overlap, status,amountType;
	private Timestamp createTime, updateTime;
	private BigDecimal upperAmount, lowerAmount;
	private Integer count;
	private Date start, finish;
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
	
	
}
