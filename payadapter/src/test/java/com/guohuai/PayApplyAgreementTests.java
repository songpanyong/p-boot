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
public class PayApplyAgreementTests {

	@Autowired
	ApplicationEventPublisher publisher;
	
	@Test
	public void contextLoads() {
		
		PayAgreementEvent event = new PayAgreementEvent();
		event.setChannel("1");
		event.setTradeType("applyAgreement");
		
		//测试白名单: 
		//张三	6217000830000123038	15718122588		4455854123465455

		String merchantTreatyNo = "123456";//商户与用户协议号
		//String startDate = "20161117",endDate = "20171103"; //协议有效期, yyyyMMdd
		String holderName = "张三";//持卡人姓名
		//String bankType = "1051000";//银行类型 建行：1051000
		String bankCardNo = "6230580000089123769";//银行卡号
		String mobileNo = "15718122588";//预留手机号码
		String certificateNo = "4455854123465455";//证件号码
		//String paymentItem = "99920";//费项代码
		
		event.setMerchantTreatyNo(merchantTreatyNo);
		//event.setEffectiveDate(startDate);
		//event.setExpirationDate(endDate);
		event.setUserName(holderName);
		//event.setBankType(bankType);
		event.setCardNo(bankCardNo);
		event.setMobileNum(mobileNo);
		//event.setPaymentItem(paymentItem);
		event.setIdentityNo(certificateNo);
		
		publisher.publishEvent(event);
		System.out.println("订单号："+event.getOrderNo());
		System.out.println("协议号："+event.getTreatyId());
		System.out.println("银行卡号："+event.getCardNo());
		System.out.println("ReturnCode："+event.getReturnCode());
		System.out.println("错误描述："+event.getErrorDesc());
		
		assertEquals(Constant.SUCCESS, event.getReturnCode());
		
	}

}
