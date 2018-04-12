package com.guohuai.account.component.util;

/**
 * @ClassName: OrderTypeEnum
 * @Description: 订单类型枚举
 * @author chendonghui
 * @date 2018年2月1日 下午3:52:43
 *
 */
public enum OrderTypeEnum {
	
	INVEST_T0("investT0","快申"),
	INVEST_T1("investT1","申购"),
	REDEEM_T0("redeemT0","快赎"),
	REDEEM_T1("redeemT1","赎回"),
	CONVERSION_REDEEM("conversionRedeem","转换-赎回"),
	CONVERSION_INVEST("conversionInvest","转换-申购"),
	REPAY_CAPITAL_WITH_INTEREST("repayCapitalWithInterest","还本付息"),
	WIND_UP_REDEEM("windUpRedeem","清盘赎回"),
	DIVIDEND("dividend","现金分红"),
	RECHARGE("recharge","充值"),
	WITHDRAWALS("withdraw","提现"),
	USE_RED_PACKET("useRedPacket","红包"),
	REBATE("rebate","返佣"),
	REFUND("reFund", "退款"),
	RAISE_FAILURE_REFUND("RaiseFailureReFund", "募集失败退款"),
	TRANSFER("transfer", "转账"),
	NETTING("netting", "轧差"),
    UNFREEZE("unfreeze","解冻");
	
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private OrderTypeEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	public static String getEnumName(final String value) {
		for (OrderTypeEnum accountTpyeEnum : OrderTypeEnum.values()) {
			if (accountTpyeEnum.getCode().equals(value)) {
				return accountTpyeEnum.getName();
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
