package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.response.CertPayQueryBankListResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertPayQueryBankListTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {

        CertPayQueryBankListResponse resp = ucfCertPayService.certPayQueryBankList();
        System.out.println(resp);
    }

}
