package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest.ReqWithdrawQueryRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WithdrawQueryTest {

    @Autowired
    UcfpayService ucfpayService;

    @Test
    public void testWithoiding() {
        //测试白名单:
        String merchantNo = "20170828074537978";
        ReqWithdrawQueryRequestBuilder request = ReqWithdrawQueryRequest.builder().merchantNo(merchantNo);
        ReqWithdrawQueryRequest req = request.build();
        System.out.println("Test参数：" + req);
        WithdrawResp res = this.ucfpayService.withdrawingQuery(req);
        System.out.println("Test获取返回：" + res);
        assertEquals("00000", res.getResCode());
    }

}
