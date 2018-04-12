package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class WriterOffOrderRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8936235689024768813L;
	/**
	 * 原始赎回单号
	 */
	private String originalRedeemOrderCode;
}
