package com.guohuai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTC1002PayOne;
import com.guohuai.common.payment.jytpay.cmd.CmdTC1002PayOneResp;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4003ReVerifiCode;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4003ReVerifiCodeResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class JytpayListenerTest {
	
	@Autowired
	JytpayService jytpayService;
	
	@Autowired
	JytFlowIdUtils utils;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void publishPayeeQueryTest(){
		TradeRecordEvent  event = new TradeRecordEvent();
		event.setOrderNo("D2900601000392831993723713160");//订单号
		event.setChannel("jytpayee");
		event.setTradeType("jytPayeeQuery");
		publisher.publishEvent(event);
		log.info("payeeQueryEvent:{},{}",event.getReturnCode(),event.getErrorDesc());
	}
	
	@Test
	public void publishPayQueryTest(){
		TradeRecordEvent  event = new TradeRecordEvent();
		event.setOrderNo("290060100039220729700230633079");//流水号
		event.setChannel("jytpay");
		event.setTradeType("jytPayQuery");
		publisher.publishEvent(event);
		log.info("payQueryEvent:{},{}",event.getReturnCode(),event.getErrorDesc());
	}
	
	/**
	 * 付款测试
	 */
	@Test
	public void publishPayTest(){
		TradeEvent  event = new TradeEvent();
		event.setBankName("中国银行");
		event.setCardNo("6215696000004499828");
		event.setRealName("孙喜新");
		event.setAccountType("01");
		event.setAmount("1.00");
		event.setBsnCode("00600");
		event.setPayNo(utils.getOneFlowId());
		event.setChannel("5");
		event.setTradeType("02");
		publisher.publishEvent(event);
		log.info("publishPayEvent:返回代码{},返回描述{}",event.getReturnCode(),event.getErrorDesc());
	}
	
	/**
	 * 收款测试
	 */
	@Test
	public void publishPayeeTest(){
		TradeEvent  event = new TradeEvent();
//		event.setOrderNo(utils.getOrderId());
//		event.setOrderNo("D29006010003972549814627869604");
//		event.setPayNo(utils.getOneFlowId());//29006010003920170104160216378944
		event.setPayNo("201701041602163793");//29006010003920170104160216378944，同业务流水号不能重复，不同业务校验
		event.setUserOid("12345679");//客户号
		event.setAmount("1.00");
		event.setCardNo("6217000330002122106");
		event.setVerifyCode("");
		event.setMobile("17316381220"); 
		
		event.setChannel("8");
		event.setTradeType("reVerifiCode");//reVerifiCode
//		event.setTradeType("01");
		publisher.publishEvent(event);
		log.info("商户订单号{}",event.getPayNo());//D29006010003972549814627869604
		log.info("publishPayEvent:返回代码{},返回描述{}",event.getReturnCode(),event.getErrorDesc());
	}
	
	@Test
	public void getVerfyCodeAgain(){
		CmdTD4003ReVerifiCode cmd= CmdTD4003ReVerifiCode.builder().mobile("17316381220").order_id("D29006010003972549814627869604").build();
		CmdTD4003ReVerifiCodeResp resp  = this.jytpayService.reVerifiCode(cmd, utils.getOneFlowId());
		
	}
	
	@Test
	public void xmlTest(){
		CmdTC1002PayOne cmd  =CmdTC1002PayOne.builder().bank_name("中国银行").account_no("6215696000004499828").account_name("孙喜新").
				account_type("00").tran_amt("1.00").currency("CNY").bsn_code("00600").build();
		CmdTC1002PayOneResp resp = jytpayService.payOne(cmd,"290060100039220729700230633072");
		System.out.println(resp.getHead().getResp_desc()+"\n"+resp.getBody().getTran_state());
	}
	
	

}
