package com.guohuai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.listener.event.TradeEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JytNetPayTest {
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void testPayNet(){
		TradeEvent event = new TradeEvent();
		event.setTradeType("jytNetPay");
		event.setOrderDesc("模拟接口支付");//订单描述
		event.setProdInfo("商品名称");//产品信息
	    event.setProdDetailUrl("http://item.jd.com/1217524.html");//产品路径
	    
		event.setUserOid("mertest0101");
		event.setAmount("0.20");
		event.setOrderNo("p-mer-20170117152234");
		publisher.publishEvent(event);
		System.out.println(event.getRespHTML());
	}

}
