package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest.WithoidingRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WithoidingTest {

    @Autowired
    UcfpayService ucfpayService;

    @Test
    public void testWithoiding() {
        //测试白名单:
        String merchantNo = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
//		String merchantNo = "SEL2016120500000001";
        String amount = "100";
        String transCur = "156";
        String userType = "1";
        String accountType = "1";
        String accountNo = "6222021001115704287";
        String accountName = "王泽武";
        String bankId = "ICBC";
        String certificateType = "0";
        String certificateNo = "420621199012133824";
        String mobileNo = "18100000000";
        String branchProvince = "";
        String branchCity = "";
        String branchName = "";
        String productName = "测试产品";
        String productInfo = "";
        String expireTime = "";
        String memo = "";

        WithoidingRequestBuilder request = WithoidingRequest.builder().merchantNo(merchantNo).amount(amount)
                .transCur(transCur).userType(userType).accountType(accountType).accountNo(accountNo).accountName(accountName)
                .bankId(bankId).certificateType(certificateType).certificateNo(certificateNo).mobileNo(mobileNo)
                .branchProvince(branchProvince).branchCity(branchCity).branchName(branchName)
                .productInfo(productInfo).expireTime(expireTime).memo(memo)
                .productName(productName);
        WithoidingRequest req = request.build();
        System.out.println("Test参数：" + req);
        WithoidingResp res = this.ucfpayService.withoiding(req);
        System.out.println("Test获取返回：" + res);
        assertEquals("00000", res.getResCode());
    }

}
