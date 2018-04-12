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

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemBalanceQueryTest {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		TradeEvent event = new TradeEvent();
		event.setTradeType("memBalanceQry");
		event.setAccountType("0");
		
		
		publisher.publishEvent(event);
		log.info("返回:"+event.getAccBalance());
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
