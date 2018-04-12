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
public class TradeRecordTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		TradeRecordEvent event = new TradeRecordEvent();
		event.setChannel("lycheepay");
		event.setTradeType("tradeRecord");
		
		String orderNo = "56038ac49bc64fa8bc590986736b34a9";
		event.setOrderNo(orderNo);
		
		publisher.publishEvent(event);
		System.out.println("查询结果："+event.toString());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
