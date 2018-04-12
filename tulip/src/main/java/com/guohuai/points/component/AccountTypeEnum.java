package com.guohuai.points.component;

/**
 * @ClassName: AccountEnum
 * @Description: 账户类别
 * @author CHENDONGHUI
 * @date 2017年3月20日  下午18:10:18
 *
 */
public enum AccountTypeEnum {
	
	ACCOUNT_TYPE01("01","积分基本户"),
	ACCOUNT_TYPE02("02","签到积分户"),
	ACCOUNT_TYPE03("03","卡券积分户"),
	ACCOUNT_TYPE04("04","充值积分户"),
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
	
	private AccountTypeEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	public static String getEnumName(final String value) {
		for (AccountTypeEnum accEnum : AccountTypeEnum.values()) {
			if (accEnum.getCode().equals(value)) {
				return accEnum.getName();
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
