package com.guohuai.rules.fact;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CouponInfo {

	/**
	 * 对象ID.
	 */
	String oid;
	
	/**
	 * 优惠券/抵扣券.
	 */
	String name;
	
	/**
	 * 有效时间.
	 */
	Date validFrom, validTo;
	
	/**
	 * 有效日.
	 */
	int days;
}
