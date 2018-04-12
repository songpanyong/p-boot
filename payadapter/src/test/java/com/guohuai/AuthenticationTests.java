package com.guohuai;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;

import scala.annotation.meta.setter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	JytFlowIdUtils flowUtils;
	
	@Test
	public void contextLoads() {
		AuthenticationEvent event = new AuthenticationEvent();
		
		event.setTradeType("validationElement");
		
		String idCardName="董新洁";
        String idCard = "120101198809192058";
        String bankCardNum = "6227002168106218281";
        String mobile = "18610600800";
        
        event.setUserName(idCardName);
        event.setIdentityNo(idCard);
        event.setCardNo(bankCardNum);
        event.setMobileNum(mobile);
        
        System.out.println(event);
		publisher.publishEvent(event);
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
	}
	
	//金运通独立鉴权发送短信
	@Test
	public void jzAuthenFTest(){
		AuthenticationEvent event = new AuthenticationEvent();
		event.setTradeType("jytValidationElement");
		event.setCustId("888888");
		event.setCardNo("6217000330002122104");
		event.setUserName("han");
		event.setOrderId(flowUtils.getOrderId());
		event.setIdentityNo("370101198210042885");
		event.setMobileNum("17316381220");
		publisher.publishEvent(event);
	}

//	金运通独立鉴权确认短信
	@Test
	public void jzAuthenSTest(){
		AuthenticationEvent event = new AuthenticationEvent();
		event.setOrderId("29006010003909303030238407163");
		event.setTradeType("jytValidationElement");
		event.setMobileNum("17316381220");
		event.setVerifyCode("199850");
		event.setBindOrderId("D29006010003934555840781137450");
		publisher.publishEvent(event);
	}
}
