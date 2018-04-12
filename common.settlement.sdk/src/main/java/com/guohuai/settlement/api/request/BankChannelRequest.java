package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 支付通道查询
 * @author zby
 *
 */
@Data
public class BankChannelRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8982981209867497237L;
	/**
	 * 来源类型
	 */
	private String sourceType;
}
