package com.guohuai.points.component;

/**
 * @ClassName: TradeType
 * @Description: 交易类别
 * @author xueyunlong
 * @date 2016年11月8日 上午11:28:18
 *
 */
public enum TradeType {
    
	/**
	 * 签到
	 */
	SIGNIN("01"),
	/**
	 * 积分券
	 */
	TICKET("02"),
    /**
     * 充值
     */
	RECHARGE("03"),
    /**
     * 消费
     */
	CONSUME("04"),
	/**
	 * 撤单
	 */
	KILLORDER("05"),
	/**
	 * 过期
	 */
	OVERDUE("06"),
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
