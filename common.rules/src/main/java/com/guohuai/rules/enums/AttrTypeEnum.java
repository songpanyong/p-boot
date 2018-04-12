package com.guohuai.rules.enums;

public enum AttrTypeEnum {

	STR("str", "字符串"),
	DATE("date", "日期"),
	NUMBER("number", "数值");

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

	private AttrTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getEnumName(final String value) {
		for (AttrTypeEnum attrType : AttrTypeEnum.values()) {
			if (attrType.getCode().equals(value)) {
				return attrType.getName();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.code;
	}
}
