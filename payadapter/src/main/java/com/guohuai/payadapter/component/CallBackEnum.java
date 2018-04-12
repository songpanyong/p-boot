package com.guohuai.payadapter.component;

public enum CallBackEnum {
	INIT("0","未处理"),
	SUCCESS("1","交易成功"),
	FAIL("2","交易失败"),
	PROCESSING("3","交易处理中"),
	OVERTIME("4","超时");//超时的放到处理中，等人功处理
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
	private CallBackEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.code;
	}

	/**
	 * 通过code取得类型
	 * 
	 * @param code
	 * @return
	 */
	public static String getName(String code) {
		for (CallBackEnum type : CallBackEnum.values()) {
			if (type.getCode().equals(code)) {
				return type.getName();
			}
		}
		return null;
	}
}
