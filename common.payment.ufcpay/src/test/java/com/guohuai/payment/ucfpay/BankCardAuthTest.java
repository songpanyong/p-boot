package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.BankCardAuthRequest;
import com.guohuai.payment.ucfpay.cmd.response.BankCardAuthResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankCardAuthTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {

        BankCardAuthRequest req = new BankCardAuthRequest();
        req.setMerchantNo(UUID.randomUUID().toString().substring(0,30));
        req.setAccountName("潘兴武");
        req.setAccountNo("6226200103146602");
        req.setCertificateNo("42062119900109121X");
//        req.setCertificateType("0");
//        req.setMemo("xxx");
        req.setMobileNo("15213649877");
//        req.setNoticeUrl("http://10.20.70.117:8053/certPayDemo/CertPayNotifySevlet");

        final BankCardAuthResponse resp = ucfCertPayService.bankCardAuth(req);
        System.out.println("Test获取返回：" + resp);
//        Assert.assertEquals("00", resp.getStatus());
    }

}
