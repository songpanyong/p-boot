package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayBindBankCardRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayBindBankCardResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayBindBankCardTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {
        CertPayBindBankCardRequest req = new CertPayBindBankCardRequest();
        req.setOutOrderId(UUID.randomUUID().toString().substring(0, 30));
        req.setUserId("500666");
        req.setMobileNo("15213649877");
        req.setRealName("潘兴武");
        req.setCardNo("42062119900109121X");
        req.setBankCardNo("6226200103146602");
        req.setNoticeUrl("http://10.20.70.117:8053/certPayDemo/CertPayNotifySevlet");
        req.setMerchantNo(UUID.randomUUID().toString());

//        System.out.println("Test参数：" + req);
        CertPayBindBankCardResponse res = this.ucfCertPayService.certPayBindBankCard(req);
//        System.out.println("Test获取返回：" + res);
        Assert.assertEquals("00", res.getStatus());
    }

}
