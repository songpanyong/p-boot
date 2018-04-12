package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayQueryOrderRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayQueryOrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayQueryOrderTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {

        CertPayQueryOrderRequest req = new CertPayQueryOrderRequest();
        req.setMerchantNo(UUID.randomUUID().toString());
        req.setOrderId("3cd9a74d-444a-46a8-b14d-ba063f");
        final CertPayQueryOrderResponse resp = ucfCertPayService.certPayQueryOrder(req);
        System.out.println("Test获取返回：" + resp);
//        Assert.assertEquals("00", resp.getStatus());
    }

}
