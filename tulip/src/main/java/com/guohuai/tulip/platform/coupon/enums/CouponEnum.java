package com.guohuai.tulip.platform.coupon.enums;

public enum CouponEnum {

	coupon_type_redPackets("redPackets", "红包"),
	coupon_type_coupon("coupon", "代金券"),
	coupon_type_rateCoupon("rateCoupon", "加息券"),
	coupon_type_tasteCoupon("tasteCoupon", "体验金");
	
	private String typeCode;
	private String typeName;

	private CouponEnum(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public static String getTypeNameByTypeCode(String typeCode) {
		for (CouponEnum coupon : CouponEnum.values()) {
			if(coupon.getTypeCode().equals(typeCode)){
				return coupon.getTypeName();
			}
		}
		
		return "";
	}
	
}
