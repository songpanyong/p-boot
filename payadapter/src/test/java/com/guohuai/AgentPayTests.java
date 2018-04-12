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
public class AgentPayTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		TradeEvent event = new TradeEvent();
		
		//先锋支付
		event.setChannel("4");
		event.setTradeType("02");
		String merchantNo = "SEL2016121200000002";
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
