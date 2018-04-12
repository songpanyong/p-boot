package com.guohuai.tulip.platform.coupon.coupon3;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.guohuai.tulip.platform.coupon.RulePropRep;
import com.guohuai.tulip.platform.coupon.couponRange.CouponRangeEntity;

import lombok.NoArgsConstructor;
/**
 * 加息券请求实体
 * @author suzhicheng
 *
 */
@NoArgsConstructor
public class RateCouponReq {
	private String oid;
	/** 加息券名称 */
	private String name;
	/** 体验金金额 */
	private BigDecimal couponAmount;
	/** 最高加息金额 */
	private BigDecimal maxRateAmount;
	/** 优惠天数 */
	private Integer validPeriod;
	/** 优惠天数类型：输入 input;与产品存续期同 same */
	private String validPeriodType;
	
	/** 发行数量 */
	@NotNull(message = "发行量不能为空")
	private Integer count;
	/** 金额类型 */
	private String amountType="fixed";
	/** 是否删除 */
	private String isdel="no";
	/** 失效时间 */
	private Integer disableDate=0;
	/** 失效类型 */
	private String disableType;
	/** 投资金额 */
	private BigDecimal investAmount=BigDecimal.ZERO;
	/** 投资金额 */
	private Integer investTime=0;
	/** 使用数量 */
	private Integer useCount=0;
	/** 权重 */
	private String weight;
	/** 创建时间 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	/** 更新时间 */
	private Timestamp updateTime = new Timestamp(System.currentTimeMillis());
	/** 创建用户 */
	private String createUser = "sys";
	/** 更新用户 */
	private String updateUser = "sys";
	/** 结算触发时间 */
	private String payflag="redeem";
	/** 是否可叠加使用 */
	private String overlap="no";
	/** 卡券类型 */
	private String type="rateCoupon";
	List<RulePropRep> propList = new ArrayList<RulePropRep>();
	
	List<CouponRangeEntity> couponRangeEntityList = new ArrayList<CouponRangeEntity>();

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

	public String getValidPeriodType() {
		return validPeriodType;
	}

	public void setValidPeriodType(String validPeriodType) {
		this.validPeriodType = validPeriodType;
	}
	
	
}
