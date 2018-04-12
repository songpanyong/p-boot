package com.guohuai.tulip.platform.coupon.coupon1;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.NoArgsConstructor;
/**
 * 红包返回实体
 * @author suzhicheng
 *
 */
@NoArgsConstructor
public class RedPacketsRep extends BaseResp{
	
	
	String oid;
	/** 卡券名称 */
	String name;
	/** 类型 */
	String type="redPackets";
	/** 卡券金额 */
	BigDecimal couponAmount=BigDecimal.ZERO;
	/** 金额上限 */
	BigDecimal upperAmount=BigDecimal.ZERO;
	/** 奖励下限 */
	BigDecimal lowerAmount=BigDecimal.ZERO;
	/** 比例 */
	BigDecimal scale=BigDecimal.ZERO;
	/** 创建时间 */
	Timestamp createTime;
	/** 更新时间 */
	Timestamp updateTime;
	/** 卡券发行量 */
	Integer count;
	/** 使用数量 */
	Integer useCount;
	/** 是否删除 */
	String isdel;
	/** 创建用户 */
	String createUser;
	/** 更新用户 */
	String updateUser;
	/** 结算标识 */
	String payflag;
	/** 是否可叠加 */
	String overlap;
	/** 金额类型 */
	String amountType;
	/** 有效天数 */
	Integer disableDate;
	/** 有效天数类型 */
	String disableType;
	/** 红包总金额 */
	BigDecimal totalAmount;
	/** 红包剩余总金额 */
	BigDecimal remainAmount;
	/** 剩余张数 */
	Integer remainCount;

	/** 领取数量 */
	private Integer issueCount;
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
	 * 获取 scale
	 */
	public BigDecimal getScale() {
		return scale;
	}

	/**
	 * 设置 scale
	 */
	public void setScale(BigDecimal scale) {
		this.scale = scale;
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
	 * 获取 remainCount
	 */
	public Integer getRemainCount() {
		return remainCount;
	}

	/**
	 * 设置 remainCount
	 */
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
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
