package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponWriteOffRep extends BaseResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3444462124187678382L;
	
	/** 卡券Id */
	private String couponOid;

	public String getCouponOid() {
		return couponOid;
	}

	public void setCouponOid(String couponOid) {
		this.couponOid = couponOid;
	}

}
