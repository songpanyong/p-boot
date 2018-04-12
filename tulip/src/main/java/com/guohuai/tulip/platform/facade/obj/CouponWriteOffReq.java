package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponWriteOffReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7709912256389880694L;
	
	/** 卡券Id */
	private String couponOid;
	
	/** 核销金额 */
	private BigDecimal writeOffAmount;
	
	/** 核销时间 */
	private Timestamp settlementTime;

	public String getCouponOid() {
		return couponOid;
	}

	public void setCouponOid(String couponOid) {
		this.couponOid = couponOid;
	}

	public BigDecimal getWriteOffAmount() {
		return writeOffAmount;
	}

	public void setWriteOffAmount(BigDecimal writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}

	public Timestamp getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Timestamp settlementTime) {
		this.settlementTime = settlementTime;
	}
	
}
