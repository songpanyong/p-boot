package com.guohuai.payadapter.component;

/**
 * @ClassName: TradeType
 * @Description: 交易类别
 * @author xueyunlong
 * @date 2016年11月8日 上午11:28:18
 *
 */
public enum TradeType {
    
    /**
     * 验证四要素（鉴权）
     */
    validationElement("validationElement"),
    /**
     * 开通代扣协议申请
     */
    applyAgreement("applyAgreement"),
	
	/**
     * 开通代扣协议确认
     */
    confirmAgreement("confirmAgreement"),
    /**
     * 支付
     */
    pay("01"),
    /**
     * 打款
     */
    payee("02"),
	/**
	 * 金盈通 四要素验证
	 */
	jytTradeType("jytElement"),
	/**
	 * 宝付 四要素验证
	 */
    baofooTradeType("baofooElement"),
	/**
	 * 先锋支付 四要素验证
	 */
	ucfPayElement("bankCardAuth"),
	/**
	 * 先锋支付  解密异步通知报文
	 */
	ucfPayDecrypt("ucfPayDecrypt"), 
	/**
	 * 四要素短信验证通用标识
	 */
	bankCardAuth("bankCardAuth"),
	;
    
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private TradeType(String value) {
		this.value = value;
	}

}
