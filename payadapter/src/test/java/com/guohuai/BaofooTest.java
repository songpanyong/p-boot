package com.guohuai;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaofooTest {

	@Autowired
	ApplicationEventPublisher publisher;
	
//	@Autowired
//	StringUtil util;

	/**
	 * 代付
	 */
	@Test
	public void baofooPayee() {
		TradeEvent event = new TradeEvent();
		String trans_no = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		event.setTradeType("02");
		event.setChannel("11");

		event.setAmount("1");
		event.setRealName("江健民");
		event.setCardNo("6217000010069428764");
		event.setPayNo(trans_no);
		publisher.publishEvent(event);
		System.out.println(event);
	}

	/**
	 * 代付查询
	 */
	@Test
	public void baofooPayeeQuery() {
		TradeRecordEvent event = new TradeRecordEvent();
		event.setChannel("11");
//		String trans_no = "20170322113203510";
		String trans_no = "7SEL2017070600000002";
		event.setOrderNo(trans_no);
		publisher.publishEvent(event);
		System.out.println(event);
		System.out.println(event.getErrorDesc());
		System.out.println(event.getReturnCode());
	}

	/**
	 * 认证支付
	 */
	@Test
	public void baofooPay() {
		TradeEvent event = new TradeEvent();
		event.setTradeType("01");
		event.setChannel("10");

		// 预支付
		String trans_no = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());

		String bindId = "201604271949318660";// 201604271949318660  201604271946051306
		event.setBindId(bindId);
		
		String txn_amt = "1";
		event.setPayNo(trans_no);
		event.setOrderNo(trans_no);
		event.setAmount(txn_amt);

		// 确认支付
//		 event.setOrderNo(trans_no);
//		 event.setVerifyCode("123456");
//		 event.setBusinessNo("20170322032831031-20170322152837010431124625993986");

		publisher.publishEvent(event);
		System.out.println(event);
	}

	/**
	 * 直接绑卡
	 */
	@Test
	public void baofooBinding() {
		AuthenticationEvent event = new AuthenticationEvent();
		event.setTradeType("baofoobindCard");
		String orderId = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String idNo = "370101198210042885";
		String name = "宋尚珍";
		String accNo = "6217000330002122106";
		String mobile = "17316381220";
		
		event.setOrderId(orderId);
		event.setCardNo(accNo);
		event.setIdentityNo(idNo);
		event.setUserName(name);
		event.setMobileNum(mobile);
		
		publisher.publishEvent(event);
		System.out.println(event);
	}

	/**
	 * 绑定查询
	 */
	@Test
	public void baofooQueryBinding() {
		AuthenticationEvent event = new AuthenticationEvent();
		event.setTradeType("baofoobindquery");
		String orderId = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());

		String accNo = "6222802203222231298";
		event.setOrderId(orderId);
		event.setCardNo(accNo);
		publisher.publishEvent(event);
		System.out.println(event);
	}
	
	/**
	 * 绑定查询
	 */
	@Test
	public void test() {
		String txn_amt = "1";//认证支付金额单位:分
		txn_amt = StringUtil.getMoneyStr(txn_amt);
		txn_amt =String.valueOf(new BigDecimal(txn_amt).multiply(new BigDecimal("100")));//认证支付单位:分
		System.out.println(txn_amt);
	}
	
	/**
	 * 预绑卡
	 */
	@Test
	public void baofooBindCard() {
		AuthenticationEvent event = new AuthenticationEvent();
		event.setTradeType("baofooElement");
		String orderId = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String idNo = "370101198210042885";
		String name = "宋尚珍";
		String accNo = "6217000330002122106";
		String mobile = "17316381220";
		
		event.setOrderId(orderId);
		event.setCardNo(accNo);
		event.setIdentityNo(idNo);
		event.setUserName(name);
		event.setMobileNum(mobile);
		
		publisher.publishEvent(event);
		System.out.println(event);
	}
	
	/**
	 * 确认绑卡
	 */
	@Test
	public void baofooBindCardConfirm() {
		AuthenticationEvent event = new AuthenticationEvent();
		event.setTradeType("baofooElement");
		String transNo = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String orderId = "20170420045301829";
		String smsCode ="123456";
		event.setOrderId(orderId);
		event.setTransNo(transNo);
		event.setVerifyCode(smsCode);
		publisher.publishEvent(event);
		System.out.println(event);
	}
	
	/**
	 * 测试金额
	 */
	@Test
	public void TestUtil() {
		System.out.println(StringUtil.getMinMoneyStr("1","1000"));
	}
	
	/**
	 * 企业代扣
	 */
	@Test
	public void baofooPlatformPay() {
		TradeEvent event = new TradeEvent();
		String trans_no = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		event.setTradeType("01");
		event.setChannel("13");
		event.setAccountProvince("上海市");
		event.setAccountCity("上海市");
		event.setAccountDept("张江支行");
		event.setAmount("10000");
		event.setPlatformName("张宝");
		event.setCardNo("6222020111122220000");
		event.setPayNo(trans_no);
//		event.setCertificate_type("00");
//		event.setCertificate_no("2134567913104");
		publisher.publishEvent(event);
		System.out.println(event);
	}
	
}
