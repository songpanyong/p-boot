package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest.ReqWithoidingQueryRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WithoidingQueryTest {

    @Autowired
    UcfpayService ucfpayService;

    @Test
    public void testWithoiding() {
        //测试白名单:
        String merchantNo = "SEL2016120500000001";
        ReqWithoidingQueryRequestBuilder request = ReqWithoidingQueryRequest.builder().merchantNo(merchantNo);
        ReqWithoidingQueryRequest req = request.build();
        System.out.println("Test参数：" + req);
        WithoidingResp res = this.ucfpayService.withoidingQuery(req);
        System.out.println("Test获取返回：" + res);
        assertEquals("00000", res.getResCode());
    }

}
