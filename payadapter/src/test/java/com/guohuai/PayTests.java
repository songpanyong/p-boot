package com.guohuai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		TradeEvent event = new TradeEvent();
		//快付通
//		event.setChannel("1");
//		event.setTradeType("01");
//		String trankNo = "12345678904"; //tradeName = "单笔收款";
//		String amount = "100";//交易金额支持整数和小数
//		String custBankNo = "1051000";//客户银行帐户银行别，测试环境只支持：中、农、建
//		String custBankAccountNo = "6217000830000123038";//本次交易中,从客户的哪张卡上扣钱
//		String custName = "张三";
//		String custID = "4455854123465455"; //持卡人证件类型，0，目前只支持身份证; 身份证号码;
//		String custProtocolNo = "1202125709755"; //用户协议号
//		
//		event.setPayNo(trankNo);
//		event.setAmount(amount);
//		event.setCustBankNo(custBankNo);
//		event.setCardNo(custBankAccountNo);
//		event.setRealName(custName);
//		event.setCustID(custID);
//		event.setProtocolNo(custProtocolNo);
		
		//先锋支付
		event.setChannel("3");
		event.setTradeType("01");
		String merchantNo = "SEL2016121200000001";
		String amount = "100";
		String accountNo = "6222021001115704287";
		String accountName = "王泽武";
		String bankId = "ICBC";
		String certificateNo = "420621199012133824";
		
		event.setPayNo(merchantNo);
		event.setAmount(amount);
		event.setCardNo(accountNo);
		event.setRealName(accountName);
		event.setCustBankNo(bankId);
		event.setCustID(certificateNo);
		
		publisher.publishEvent(event);
		System.out.println("返回订单号："+event.getHostFlowNo());
		System.out.println("错误："+event.getErrorDesc());
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
