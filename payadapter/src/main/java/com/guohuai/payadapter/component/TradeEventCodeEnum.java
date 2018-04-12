package com.guohuai.payadapter.component;

public enum TradeEventCodeEnum {
	
	trade_1001("1001","定单来源不能为空"),
	trade_1002("1002","定单类别不能为空"),
	trade_1003("1003","定单金额不能为空"),
	trade_1004("1004","未匹配到支付渠道"),
	trade_1005("1005","定单类别不支持"),
	trade_1006("1006","请绑定银行卡"),
//	trade_1007("1007","大额赎回"),
	trade_1007("1007","人工处理"),
	trade_1008("1008","绑定银行卡交易额度超过日限额"),
	trade_1009("1009","不支持该银行卡"),
	trade_1010("1010","绑定银行卡交易额度超过单笔限额"),
	trade_1011("1011","请使用手机验证码支付"),
	trade_1012("1012","账户可提现余额不足"),
	trade_1013("1013","记账失败"),
	trade_1014("1014","该订单已记过帐,不能重复记账"),
	trade_1015("1015","未匹配到绑卡渠道"),
	trade_1016("1016","银行卡号不能为空"),
	trade_1017("1017","姓名不能为空"),
	trade_1018("1018","证件号码不能为空"),
	trade_1019("1019","手机号码不能为空"),
	trade_1020("1020","交易类型不符合"),


	;

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
	
	private TradeEventCodeEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	public static String getEnumName(final String value) {
		for (TradeEventCodeEnum tradeEnum : TradeEventCodeEnum.values()) {
			if (tradeEnum.getCode().equals(value)) {
				return tradeEnum.getName();
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
