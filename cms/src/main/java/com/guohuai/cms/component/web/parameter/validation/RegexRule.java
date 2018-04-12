package com.guohuai.cms.component.web.parameter.validation;

/**
 * 正则表达式规则枚举
 * @author wangyan
 *
 */
public enum RegexRule {

	/** 
	 * email正则表达式规则
	 */ 
	EMAIL("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"), 

	/*
	 * 数字正则表达式规则
	 */
	NUMBER("^[0-9]*$"),
	
	/**
	 * 英文字母正则表达式规则
	 */
	ENGLISH("^[a-zA-Z]*$"),
	
	/**
	 * 中文正则表达式规则
	 */
	CHINESE("^[\u4e00-\u9fa5]*$"),
	
	/**
	 * 中文，英文字母和数字及_正则表达式规则
	 */
	CHINESE_ENGLISH_NUMBER_("^[\u4e00-\u9fa5_a-zA-Z0-9]+$"),
	
	/**
	 * 中文，英文字母和数字正则表达式规则
	 */
	CHINESE_ENGLISH_NUMBER("^[\u4e00-\u9fa5_a-zA-Z0-9]+$"),
	
	/**
	 * url链接地址
	 */
	HTTP_START("^http://.+$"),
	
	/**
	 * 只有时分秒的时间格式
	 */
	TIME("^((\\d)|([0-1]{1}\\d)|(2[0-3])):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}$");
	
	public String value;
	
	RegexRule(String value) {
		this.value = value;
	}
	 
}
