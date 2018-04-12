package com.guohuai.tulip.platform.coupon.coupon6;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.NoArgsConstructor;
@NoArgsConstructor
public class PointsCouponRep extends BaseResp{
	private String oid;
	/** 提现券名称 */
	private String name;
	/** 提现券金额 */
	private BigDecimal couponAmount;
	/** 发行数量 */
	@NotNull(message = "发行量不能为空")
	private Integer count;
	/** 金额类型 */
	private String amountType;
	/** 是否删除 */
	private String isdel;
	/** 到期时间 */
	private String disableTime;
	/** 使用数量 */
	private Integer useCount;
	/** 创建时间 */
	private Timestamp createTime;
	/** 更新时间 */
	private Timestamp updateTime;
	/** 创建用户 */
	private String createUser;
	/** 更新用户 */
	private String updateUser;
	/** 领取数量 */
	private Integer issueCount;
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
	 * 获取 disableTime
	 */
	public String getDisableTime() {
		return disableTime;
	}

	/**
	 * 设置 disableTime
	 */
	public void setDisableTime(String disableTime) {
		this.disableTime = disableTime;
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
	
	
}
