package com.guohuai.tulip.platform.coupon.coupon5;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
/**
 * 保存提现券实体
 * @author suzhicheng
 *
 */
@NoArgsConstructor
public class CashCouponReq {
	private String oid;
	/** 提现券名称 */
	private String name;
	/** 提现券金额 */
	private BigDecimal couponAmount;
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
	/** 使用数量 */
	private Integer useCount=0;
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
	private String type="cashCoupon";
	
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
	
}
