package com.guohuai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UcfTradeRecordTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		TradeRecordEvent event = new TradeRecordEvent();
		event.setChannel("lycheepay");
		event.setTradeType("ucfTradeRecord");
		
		String orderNo = "SEL2016120500000001";
		event.setOrderNo(orderNo);
		
		publisher.publishEvent(event);
		System.out.println("查询结果："+event.toString());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
