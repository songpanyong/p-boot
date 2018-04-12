package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayConfirmTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {
        CertPayRequest req = new CertPayRequest();
        //  预支付修改项
        req.setOutOrderId("ae74a28f-d051-41ba-bb03-7e056b");
        req.setMemberUserId("10000099968");
        req.setSmsCode("888888");
        req.setBankName("民生银行");
        req.setPaymentId("01010101010101");
        req.setTradeNo("11010101010101");

        req.setAmount("120");
        req.setUserId("500666");
        req.setMobileNo("15213649877");
        req.setRealName("潘兴武");
        req.setCardNo("42062119900109121X");
        req.setBankCardNo("6226200103146602");
        req.setBankCode("CMBC");
        req.setMerchantNo(UUID.randomUUID().toString());


//        System.out.println("Test参数：" + req);
        CertPayResponse res = this.ucfCertPayService.certPayConfirm(req);
        System.out.println("Test获取返回：" + res);
        Assert.assertEquals("00", res.getStatus());
    }

}
