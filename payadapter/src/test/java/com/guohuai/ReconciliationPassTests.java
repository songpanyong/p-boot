package com.guohuai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.ReconciliationPassEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReconciliationPassTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		ReconciliationPassEvent event = new ReconciliationPassEvent();
		event.setChannel("lycheepay");
		event.setTradeType("reconciliationPass");
		
		String checkDate = "20161110";
		event.setCheckDate(checkDate);
		
		publisher.publishEvent(event);
		System.out.println("查询结果："+event.toString());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
