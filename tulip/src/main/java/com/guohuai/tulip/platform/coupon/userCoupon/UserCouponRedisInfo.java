package com.guohuai.tulip.platform.coupon.userCoupon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class UserCouponRedisInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//卡券Id
	private String couponOid;
	//用户id
	private String userId;
	
	/** 卡券金额 */
	private BigDecimal couponAmount;
	
	//卡券类型- 还需要区分是随机红包还是固定红包：fixed:固定红包,random:随机红包;coupon.优惠券;3.折扣券;tasteCoupon.体验金;rateCoupon,加息券
	private String couponType;
	
	//卡券名称
	private String name;
	
	private Timestamp finish;
	
}
