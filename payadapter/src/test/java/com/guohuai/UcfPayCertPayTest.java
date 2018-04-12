package com.guohuai;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UcfPayCertPayTest {

    @Autowired
    ApplicationEventPublisher publisher;

 static    String userId = "500666";
 static    String merchantNo = UUID.randomUUID().toString().substring(0, 32);
 static    String amount = "1";
 static    String accountNo = "6226200103146602";
 static    String accountName = "潘兴武";
 static    String bankId = "CMBC";
 static    String certificateNo = "42062119900109121X";
 static    String phone = "15213649877";
 static    String bankName = "民生银行";

    @Test
    public void testPreparePay() {

        TradeEvent event = new TradeEvent();
        //先锋支付
        event.setChannel("16");
        event.setTradeType("01");
        event.setAmount(amount);
        event.setPayNo(merchantNo);
        event.setUserOid(userId);
        event.setMobile(phone);
        event.setRealName(accountName);
        event.setCustID(certificateNo);
        event.setCardNo(accountNo);
        event.setCustBankNo(bankId);
        event.setBankName(bankName);

        publisher.publishEvent(event);
        assertEquals(Constant.SUCCESS, event.getReturnCode());
    }

    @Test
    public void testConfirmPay() {
        TradeEvent event = new TradeEvent();
        String outOrderId1 = "75c9675f-0f2f-429f-ac7b-cb7ad87a";
        String protocolNo = "10000099968";
        String verifyCode = "888888";
        String outPaymentId = "01010101010101"; // 支付流水号  paymentId
        String outTradeNo = "11010101010101";// 交易流水号   tradeNo

        //先锋支付
        event.setPayNo(outOrderId1);
        event.setBindId(protocolNo);  // memberUserId
        event.setVerifyCode(verifyCode);
        event.setBankName(bankName);
        event.setOutPaymentId(outPaymentId);  //
        event.setOutTradeNo(outTradeNo);
        event.setChannel("16");
        event.setTradeType("01");
        event.setAmount(amount);
        event.setUserOid(userId);
        event.setMobile(phone);
        event.setRealName(accountName);
        event.setCustID(certificateNo);
        event.setCardNo(accountNo);
        event.setCustBankNo(bankId);

        publisher.publishEvent(event);
        assertEquals(Constant.SUCCESS, event.getReturnCode());
    }
}
