package com.guohuai.rules.fact;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coupon {
	String type;
	Date validDate;
	
	CouponInfo info;
}
