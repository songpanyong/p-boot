package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyCouponReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1383098228230769340L;
	/** 用户ID */
	private String userId;
	/** 卡券Id */
	private String couponId;
	/** 卡券状态 */
	private String status;
	/** 卡券类型 */
	private String type;
	/** 产品Id */
	private String productId;
	/** 投资金额 */
	private BigDecimal investmentAmount;
	/** 页数 */
	private int page;
	/** 页大小 */
	private int rows;
	/** 产品标签 */
	private List<String> labelCodes=new ArrayList<String>();
	/**
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 investmentAmount
	 */
	public BigDecimal getInvestmentAmount() {
		return investmentAmount;
	}

	/**
	 * 设置 investmentAmount
	 */
	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	/**
	 * 获取 page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置 page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 获取 rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * 设置 rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 设置 labelCodes
	 */
	public List<String> getLabelCodes() {
		return labelCodes;
	}

	/**
	 * 获取 labelCodes 
	 */
	public void setLabelCodes(List<String> labelCodes) {
		this.labelCodes = labelCodes;
	}

	/**
	 * 获取 productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * 设置 productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * 获取 userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置 userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 couponId
	 */
	public String getCouponId() {
		return couponId;
	}

	/**
	 * 设置 couponId
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
}
