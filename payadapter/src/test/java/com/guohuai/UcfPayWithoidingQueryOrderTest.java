package com.guohuai;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UcfPayWithoidingQueryOrderTest {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void test() {
		
		TradeRecordEvent event = new TradeRecordEvent();
		event.setChannel("17");
		event.setTradeType("queryOrder");
		String orderNo = "3cd9a74d-444a-46a8-b14d-ba063f";
		event.setOrderNo(orderNo);
		
		publisher.publishEvent(event);
		System.out.println("查询结果："+event.toString());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
