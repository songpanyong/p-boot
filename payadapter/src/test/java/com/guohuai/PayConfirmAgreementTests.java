package com.guohuai;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.PayAgreementEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayConfirmAgreementTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		PayAgreementEvent event = new PayAgreementEvent();
		event.setChannel("lycheepay");
		event.setTradeType("confirmAgreement");
		String orderNo = "517e994f36674f1db8d014c6ba7c6500";//订单号
		String treatyNo = "1202125709764";//协议号
		
		event.setOrderNo(orderNo);
		event.setTreatyId(treatyNo);
		
		publisher.publishEvent(event);
		System.out.println(event.getMerchantTreatyNo());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}

}
