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
public class UcfPayWithoidingTest {

    @Autowired
    ApplicationEventPublisher publisher;

 static    String merchantNo = UUID.randomUUID().toString().substring(0, 32);
 static    String amount = "1";
 static    String accountNo = "6226200103146602";
 static    String accountName = "潘兴武";
 static    String bankId = "CMBC";
 static    String certificateNo = "42062119900109121X";
 static    String bankName = "民生银行";

    @Test
    public void test() {

        TradeEvent event = new TradeEvent();
        event.setChannel("17");
        event.setTradeType("01");
        event.setAmount(amount);
        event.setPayNo(merchantNo);
        event.setRealName(accountName);
        event.setCustID(certificateNo);
        event.setCardNo(accountNo);
        event.setCustBankNo(bankId);
        event.setBankName(bankName);
        event.setOrderDesc("订单备注");

        publisher.publishEvent(event);
        assertEquals(Constant.SUCCESS, event.getReturnCode());
    }


}
