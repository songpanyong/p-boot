package com.guohuai.tulip.platform.coupon;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 卡券实体
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_COUPON")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CouponEntity extends UUID {

	private static final long serialVersionUID = 3297827842886697187L;
	/**
	 * 卡券类型--红包
	 */
	public static final String COUPON_TYPE_redPackets = "redPackets";
	/**
	 * 卡券类型--代金券
	 */
	public static final String COUPON_TYPE_coupon = "coupon";
	/**
	 * 卡券类型--加息券
	 */
	public static final String COUPON_TYPE_rateCoupon = "rateCoupon";
	/**
	 * 卡券类型--体验金
	 */
	public static final String COUPON_TYPE_tasteCoupon = "tasteCoupon";
	/**
	 * 卡券类型--提现券
	 */
	public static final String COUPON_TYPE_cashCoupon = "cashCoupon";
	/**
	 * 卡券类型--积分券
	 */
	public static final String COUPON_TYPE_pointsCoupon = "pointsCoupon";
	
	/**
	 * 卡券类型--电影票
	 */
	public static final String COUPON_TYPE_moiveTicket = "movieTicket";

	/** 失效类型--天，23:59:59 */
	public static final String DISABLETYPE_DAY = "DAY";
	/** 失效类型--时 */
	public static final String DISABLETYPE_HOUR = "HOUR";

	/** 金额类型--比例 */
	public static final String AMOUNTTYPE_percentage = "scale";
	/** 金额类型--固定 */
	public static final String AMOUNTTYPE_fixed = "fixed";
	/** 金额类型--随机 */
	public static final String AMOUNTTYPE_random = "random";
	
	/** 结算标识-- 申购 */
	public static final String PAYFLAG_invest="invest";
	/** 结算标识-- 赎回 */
	public static final String PAYFLAG_redeem="redeem";
	/** 结算标识-- 提现 */
	public static final String PAYFLAG_cash="cash";

	/** 是否可叠加使用-- 是 */
	public static final String OVERLAP_yes="yes";
	/** 是否可叠加使用-- 否 */
	public static final String OVERLAP_no="no";
	
	/** 是否删除-- 是 */
	public static final String ISDEL_YES="yes";
	/** 是否删除-- 否 */
	public static final String ISDEL_NO="no";
	
	/** 卡券名称 */
	String name;
	
	/** 卡券描述 */
	String description;
	
	/** 卡券类型 */
	String type;
	
	/** 卡券金额 */
	BigDecimal couponAmount = BigDecimal.ZERO;
	
	/** 金额上限 */
	BigDecimal upperAmount = BigDecimal.ZERO;
	
	/** 金额下限 */
	BigDecimal lowerAmount = BigDecimal.ZERO;
	
	/** 比例 */
	BigDecimal scale=BigDecimal.ZERO;
	
	/** 创建时间 */
	Timestamp createTime;
	
	/** 更新时间 */
	Timestamp updateTime;
	
	/** 卡券发行量 */
	Integer count;
	
	/** 状态 */
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
	
	/** 多少天失效 */
	Integer disableDate = 0;
	
	/** 失效类型 DAY:HOUR */
	String disableType;
	/** 到期时间 */
	Date disableTime;
	
	/** 剩余红包数量 */
	Integer remainCount;
	
	/** 使用金额限制，无条件则为0 */
	BigDecimal investAmount = BigDecimal.ZERO;
	
	/** 投资时间限制，无条件则为0 */
	Integer investTime = 0;
	
	/** 最高加息金额 */
	BigDecimal maxRateAmount= BigDecimal.ZERO;
	
	/** 优惠天数 */
	Integer validPeriod;
	
	/** 优惠天数类型：输入 input;与产品存续期同 same */
	String validPeriodType;
	
	/** 对应产品 */
	String products;
	
	/** 使用规则描述 */
	String rules;
	
	/** 红包总金额 */
	BigDecimal totalAmount = BigDecimal.ZERO;
	
	/** 剩余红包总金额 */
	BigDecimal remainAmount = BigDecimal.ZERO;
	
	/** 使用数量 */
	Integer useCount = 0;
	/** 已结算总金额 */
	BigDecimal writeOffTotalAmount = BigDecimal.ZERO;

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

	public String getValidPeriodType() {
		return validPeriodType;
	}

	public void setValidPeriodType(String validPeriodType) {
		this.validPeriodType = validPeriodType;
	}

	public BigDecimal getWriteOffTotalAmount() {
		return writeOffTotalAmount;
	}

	public void setWriteOffTotalAmount(BigDecimal writeOffTotalAmount) {
		this.writeOffTotalAmount = writeOffTotalAmount;
	}

}
