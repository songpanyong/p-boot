package com.guohuai.settlement.api.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付通道查询返回
 * @author zby
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BankChannelResponse extends BaseResponse{/**
	 * 
	 */
	private static final long serialVersionUID = -5344736015258073512L;
	
	List<BankChannel> bankChannes;
	
	@Data
	public static class BankChannel{
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
}

