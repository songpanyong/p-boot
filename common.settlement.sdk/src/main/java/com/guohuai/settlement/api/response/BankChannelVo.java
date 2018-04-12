package com.guohuai.settlement.api.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付通道查询Vo
 * @author zby
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BankChannelVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6405290875047923446L;

	/**
	 * 渠道号
	 */
	private String channelNo;
	
	/**
	 * 渠道名称
	 */
	private String channelName;
	
	/**
	 * 交易类型
	 */
	private String tradeType;
	
	/**
	 * 最小额度
	 */
	private BigDecimal minAmount;
	
	/**
	 * 最带额度
	 */
	private BigDecimal maxAmount;
	
	/**
	 * 费率
	 */
	private BigDecimal rate;
}
