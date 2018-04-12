package com.guohuai.tulip.platform.coupon.coupon2;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.tulip.platform.coupon.RulePropRep;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeEntity;

import lombok.NoArgsConstructor;
/**
 * 代金券返回实体
 * @author suzhicheng
 *
 */
@NoArgsConstructor
public class CouponRep extends BaseResp{
	
	private String oid;
	/** 代金券名称 */
	private String name;
	/** 描述 */
	private String description;
	/** 代金券价值 */
	private BigDecimal couponAmount;
	/** 卡券发行量 */
	private Integer count;
	/** 权重 */
	private String weight;
	/** 是否删除 */
	private String isdel;
	/** 多少天失效 */
	private Integer disableDate;
	/** 失效天数类型 */
	private String disableType;
	/** 投资金额 */
	private BigDecimal investAmount;
	/** 投资期限 */
	private Integer investTime;
	/** 创建用户 */
	private String createUser;
	/** 更新用户 */
	private String updateUser;
	/** 创建时间 */
	private Timestamp createTime ;
	/** 更新时间 */
	private Timestamp updateTime ;
	
	private List<RulePropRep> propList = new ArrayList<RulePropRep>();
	
	private List<CouponRangeEntity> couponRangeEntityList =new ArrayList<CouponRangeEntity>();
	
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
	 * 获取 propList
	 */
	public List<RulePropRep> getPropList() {
		return propList;
	}
	/**
	 * 设置 propList
	 */
	public void setPropList(List<RulePropRep> propList) {
		this.propList = propList;
	}
	/**
	 * 获取 couponRangeEntityList
	 */
	public List<CouponRangeEntity> getCouponRangeEntityList() {
		return couponRangeEntityList;
	}
	/**
	 * 设置 couponRangeEntityList
	 */
	public void setCouponRangeEntityList(List<CouponRangeEntity> couponRangeEntityList) {
		this.couponRangeEntityList = couponRangeEntityList;
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
