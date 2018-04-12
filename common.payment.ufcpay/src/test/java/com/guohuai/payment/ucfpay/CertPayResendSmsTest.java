package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayResendSmsRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayResendSmsResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayResendSmsTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {
        CertPayResendSmsRequest req = new CertPayResendSmsRequest();

//        req.setUserId("500666");
//        req.setMobileNo("15213649877");
        req.setMerchantNo(UUID.randomUUID().toString());
        CertPayResendSmsResponse res = this.ucfCertPayService.certPayResendSms(req);

        Assert.assertEquals("00", res.getStatus());
    }

}
