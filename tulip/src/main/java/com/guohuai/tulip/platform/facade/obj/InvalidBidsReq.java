package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;

public class InvalidBidsReq implements Serializable{

	private static final long serialVersionUID = -3314389203001069090L;
	/** 产品ID */
	private String productId;

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
	
	
}
