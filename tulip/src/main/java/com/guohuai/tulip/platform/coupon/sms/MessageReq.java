package com.guohuai.tulip.platform.coupon.sms;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class MessageReq implements Serializable {
	
	//fixed:固定红包,random:随机红包;coupon.代金券;rateCoupon,加息券;tasteCoupon.体验金;\r\n cashCoupon:提现券;pointsCoupon:积分券;
	
	/** 固定红包  */
	public static final String COUPONTYPE_FIXED="fixed";
	/** 随机红包  */
	public static final String COUPONTYPE_RANDOM="random";
	/** 代金券  */
	public static final String COUPONTYPE_COUPON="coupon";
	/** 加息券  */
	public static final String COUPONTYPE_RATECOUPON="rateCoupon";
	/** 体验金  */
	public static final String COUPONTYPE_TASTECOUPON="tasteCoupon";
	/** 提现券  */
	public static final String COUPONTYPE_CASHCOUPON="cashCoupon";
	/** 积分券  */
	public static final String COUPONTYPE_POINTSCOUPON="pointsCoupon";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8266684041765169572L;
	
	private String userId;
	
	private String userPhone;
	
	private String eventType;
	
	//卡券Id
	private String couponOid;
	
	/** 卡券金额 */
	private BigDecimal couponAmount;
	
	//卡券类型- 还需要区分是随机红包还是固定红包;coupon.优惠券;3.折扣券;tasteCoupon.体验金;rateCoupon,加息券
	private String couponType;
	
	//卡券名称
	private String name;
	
}
