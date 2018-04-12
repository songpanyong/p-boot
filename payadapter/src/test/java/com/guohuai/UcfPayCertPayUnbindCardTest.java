package com.guohuai;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payadapter.listener.event.TradeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UcfPayCertPayUnbindCardTest {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void test() {

		AuthenticationEvent event = new AuthenticationEvent();
		event.setChannel("16");
		event.setTradeType("unbindCard");
		event.setUserOid("500666");
		event.setCardNo("6226200103146602");

		publisher.publishEvent(event);
		System.out.println("查询结果："+event.toString());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
