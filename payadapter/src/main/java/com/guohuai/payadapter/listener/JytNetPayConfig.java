package com.guohuai.payadapter.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;


@Configuration
@Data
public class JytNetPayConfig {

	@Value("${jyt.gateway:http://test1.jytpay.com:30080/JytNetpay/payment.do}")
	String gateway = "http://test1.jytpay.com:30080/JytNetpay/payment.do";
	
	@Value("${jyt.tranCode:TN1001}")
	String tranCode;
	
	@Value("${jyt.version:1.0.0}")
	String version;
	
	@Value("${jyt.charset:utf-8}")
	String charset;
	
	@Value("${jyt.uaType:00}")//客户端交易类型；00-PC；01-手机；目前只支持00
	String uaType;
	
	@Value("${jyt.merchantId:290060100013}")//商户号
	String merchantId;
	
	@Value("${jyt.notifyUrl:http://127.0.0.1:8088/netpayDemo/mer/payment-notify.do}")//通知地址
	String notifyUrl ;
	
	@Value("${jyt.backUrl:http://127.0.0.1:8088/netpayDemo/mer/payment-notify.do}")//跳转地址
	String backUrl;
	
	@Value("${jyt.signType:SHA256}")//签名算法
	String signType;
	
	@Value("${jyt.key:5ae8b323b36ebaa7bc416aeb1d9d31ba}")//网关密钥
	String key;
//	String key = "255ee226ddb2bdc94ef39c5e3e454606";
	
	@Value("${jyt.action:http://test1.jytpay.com:30080/JytNetpay/payment.do}")//网关地址
	String jytAction;
}
