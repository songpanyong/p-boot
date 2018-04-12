package com.guohuai.tulip.platform.coupon;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 统计卡券兑付信息
 * @author mr_gu
 *
 */
@Data
public class StatisticsCouponDetailResp {
		
	/**
	 * 卡券类型
	 * 
	 */
	String couponType;
	
	/**
	 * 已创建总额
	 */
	BigDecimal allCreateAmount = BigDecimal.ZERO;
	
	/**
	 * 已发放总额
	 */
	BigDecimal	allGrantAmount = BigDecimal.ZERO;
	
	/**
	 * 已兑付总额
	 */
	BigDecimal	allCashAmount = BigDecimal.ZERO;
	
	/**
	 * 已过期总额
	 */
	BigDecimal	allDueAmount = BigDecimal.ZERO;
	
	/**
	 * 待兑付总额
	 */
	BigDecimal	unCashAmount = BigDecimal.ZERO;
}
