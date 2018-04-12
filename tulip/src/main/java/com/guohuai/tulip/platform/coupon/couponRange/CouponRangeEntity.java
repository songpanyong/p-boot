package com.guohuai.tulip.platform.coupon.couponRange;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动-实体
 * 
 * @author tidecc
 */
@Entity
@Table(name = "T_TULIP_COUPON_RANGE")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CouponRangeEntity extends UUID implements Serializable {
	
	private static final long serialVersionUID = -7609419530969928274L;
	
	public static final String PRODUCT_TYPE_LABEL = "label"; //产品标签
	public static final String PRODUCT_TYPE_ID = "id"; //产品标签
	
	/**
	 * 卡券批次号
	 */
	String couponBatch;
	/**
	 * 产品编号
	 */
	String labelCode;
	/**
	 * 产品名称
	 */
	String labelName;
	
	/**
	 * 关联产品类型
	 */
	String type;
	/**
	 * 获取 couponBatch
	 */
	public String getCouponBatch() {
		return couponBatch;
	}
	/**
	 * 设置 couponBatch
	 */
	public void setCouponBatch(String couponBatch) {
		this.couponBatch = couponBatch;
	}
	/**
	 * 设置 labelCode
	 */
	public String getLabelCode() {
		return labelCode;
	}
	/**
	 * 获取 labelCode 
	 */
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	/**
	 * 设置 labelName
	 */
	public String getLabelName() {
		return labelName;
	}
	/**
	 * 获取 labelName 
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
