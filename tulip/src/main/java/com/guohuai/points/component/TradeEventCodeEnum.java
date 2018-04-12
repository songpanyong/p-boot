package com.guohuai.points.component;

public enum TradeEventCodeEnum {
	
	TRADE_2001("2001","定单来源不能为空"),
	TRADE_2002("2002","定单类别不能为空"),
	TRADE_2003("2003","定单用户不能为空"),
	TRADE_2004("2004","订单号重复"),
	TRADE_2005("2005","定单类别不支持"),
	TRADE_2006("2006","用户积分账户积分余额为0"),
	TRADE_2007("2007","用户积分账户积分余额不足"),
	TRADE_2008("2008","积分过期时间不能为空"),
	TRADE_2009("2009","撤单原订单号不能为空"),
	TRADE_2010("2010","撤单原订单号不存在"),
	TRADE_2011("2011","订单状态非成功，无需撤单"),
	TRADE_2012("2012","非消费订单不允许撤单"),
	TRADE_2013("2013","订单类型不存在"),
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
