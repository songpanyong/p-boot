package com.guohuai;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.AcceptOrderListener;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payadapter.listener.event.OrderEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcceptOrderListenerTests {

	@Autowired
	AcceptOrderListener acceptOrderListener;
	
	@Test
	public void contextLoads() {
		OrderEvent event = new OrderEvent();
		String amount = "0.01";
		
		/**
		 * 通用
		 */
		event.setOrderNo("SEL2016121200000004");//订单号
		event.setPayNo("29006010003920170110183416363568");
		event.setSourceType("mimosa");//定单来源
		event.setAmount(new BigDecimal(amount));//金额
		event.setCardNo("6215696000004499828");//
		event.setRealName("孙喜新");
		event.setTradeType("02"); //01收款 02付款
		event.setCustAccountId("420621199012133824");
		event.setBankName("中国银行");
		event.setMobile("17316381220");
		
        System.out.println("请求参数"+event);
        acceptOrderListener.acceptOrderEvent(event);
        System.out.println("返还参数"+event.getReturnCode()+event.getErrorDesc());
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}
	
	@Test
	public void testPayee(){
		OrderEvent event = new OrderEvent();
		double amount = 1.00;
		event.setUserOid("12345677");//userOid
		event.setOrderNo("SEL2016121200000004");//订单号
		event.setPayNo("29006010003920170110183416363566");
		event.setSourceType("mimosa");//定单来源
		event.setAmount(new BigDecimal(amount));//金额
		event.setCardNo("6217000330002122106");//
		event.setRealName("宋尚珍");
		event.setTradeType("01"); //01收款 02付款
//		event.setCustAccountId("420621199012133824");
		event.setBankName("中国银行");
//        System.out.println("发送验证码请求参数"+event);
//        acceptOrderListener.acceptOrderEvent(event);
//        System.out.println("发送验证码返还参数"+event.getReturnCode()+event.getErrorDesc());
		event.setMobile("17316381220");
		event.setVerifyCode("566178");
		event.setOrderNo(event.getOrderNo());
        System.out.println("交易订单请求参数"+event);
        acceptOrderListener.acceptOrderEvent(event);
        System.out.println("交易订单返还参数"+event.getReturnCode()+event.getErrorDesc());
	}

}
