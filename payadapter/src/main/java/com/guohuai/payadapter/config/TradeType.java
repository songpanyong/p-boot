package com.guohuai.payadapter.config;

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
     * 绑卡
     */
    bankCardAuth("bankCardAuth"),
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
    pay("pay"),
    /**
     * 打款
     */
    payee("payee"),
    /**
     * 交易结果
     */
    tradeRecord("tradeRecord"),
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
