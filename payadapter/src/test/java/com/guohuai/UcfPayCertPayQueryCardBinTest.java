package com.guohuai;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UcfPayCertPayQueryCardBinTest {

    @Autowired
    ApplicationEventPublisher publisher;

    @Test
    public void test() {
        TradeEvent event = new TradeEvent();
        event.setChannel("16");
        event.setTradeType("queryCardBin");
        event.setCardNo("6228480402564890018");

        publisher.publishEvent(event);
        System.out.println("查询结果：" + event.toString());

        assertEquals(Constant.SUCCESS, event.getReturnCode());
    }

}
