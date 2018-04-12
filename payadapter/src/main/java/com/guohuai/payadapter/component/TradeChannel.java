package com.guohuai.payadapter.component;

/**
 * @ClassName: TradeChannel
 * @Description: 交易渠道
 * @author xueyunlong
 * @date 2016年11月8日 上午11:35:27
 *
 */
public enum TradeChannel {
	    
    
	/**
	 * 快付通
	 */
	lycheepay("1"),
	/**
	 * 平安银行
	 */
	pinganbank("2"),
	/**
	 * 金运通代付
	 */
	jinyuntongpay("7"),
	/**
	 * 金运通支付
	 */
	jinyuntongpayee("8"),
	/**
	 * 宝付认证支付
	 */
	baofoopay("10"),
	/**
	 * 宝付代付
	 */
	baofoopayee("11"),
	/**
	 * 宝付代扣
	 */
	baofooDkWithoiding("12"),
	/**
	 * 宝付网关支付
	 */
	baofooGateway("14"),
	/**
	 * 先锋支付-认证支付
	 */
	ucfPayCertPay("16"),
	/**
	 * 先锋支付-代付
	 */
	ucfPayWithdraw("18"),
	;
    
    
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private TradeChannel(String value) {
		this.value = value;
	}

	
}
