package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;
import com.guohuai.basic.component.ext.web.BaseResp;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponInterestRep extends BaseResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5358334354815689228L;
	/** 卡券金额 */
	BigDecimal couponAmount;

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
	
	
}
