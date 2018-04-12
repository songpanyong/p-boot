package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayUnbindCardRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayUnbindCardResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayUnbindCardTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void testCertPayPrepare() {
        CertPayUnbindCardRequest req = new CertPayUnbindCardRequest();

        req.setUserId("500666");
        req.setBankCardNo("6226200103146602");
        req.setMerchantNo(UUID.randomUUID().toString());
        CertPayUnbindCardResponse res = this.ucfCertPayService.certPayUnbindCard(req);

        Assert.assertEquals("00", res.getStatus());
    }

}
