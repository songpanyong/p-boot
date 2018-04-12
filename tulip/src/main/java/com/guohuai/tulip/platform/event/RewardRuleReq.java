package com.guohuai.tulip.platform.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.guohuai.tulip.platform.coupon.CouponEntity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RewardRuleReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 916424373834882185L;
	String oid;
	/**
	 * and，逻辑与;or,逻辑或
	 */
	String weight;
	
	List<CouponEntity> couponOids = new ArrayList<CouponEntity>();
	
	List<PropRep> propList = new ArrayList<PropRep>();
	/** actionName */
	String actionName;
	/**
	 * 获取 oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置 oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 获取 weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * 设置 weight
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * 获取 couponOids
	 */
	public List<CouponEntity> getCouponOids() {
		return couponOids;
	}

	/**
	 * 设置 couponOids
	 */
	public void setCouponOids(List<CouponEntity> couponOids) {
		this.couponOids = couponOids;
	}

	/**
	 * 获取 propList
	 */
	public List<PropRep> getPropList() {
		return propList;
	}

	/**
	 * 设置 propList
	 */
	public void setPropList(List<PropRep> propList) {
		this.propList = propList;
	}

	/**
	 * 获取 actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * 设置 actionName
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
}
