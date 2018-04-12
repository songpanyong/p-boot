package com.guohuai.payment.ucfpay;

import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest.WithdrawRequestBuilder;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
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
public class WithdrawTest {
    @Autowired
    UcfpayService ucfpayService;

    @Test
    public void testWithdraw() {
        //测试白名单:
        String merchantNo = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
//		String merchantNo = "SEL2016120500000001";
        String amount = "1";
        String transCur = "156";
        String userType = "1";
        String accountType = "1";
        String accountNo = "6222021001115704287";
        String accountName = "王泽武";
        String bankNo = "CCB";
        String mobileNo = "18100000000";
        String branchProvince = "";
        String branchCity = "";
        String branchName = "";
        String issuer = "";
        String memo = "";

        WithdrawRequestBuilder request = WithdrawRequest.builder().merchantNo(merchantNo).amount(amount)
                .transCur(transCur).userType(userType).accountType(accountType).accountNo(accountNo).accountName(accountName)
                .mobileNo(mobileNo).bankNo(bankNo).issuer(issuer).branchProvince(branchProvince).branchCity(branchCity).branchName(branchName).
                        memo(memo);
        WithdrawRequest req = request.build();
        System.out.println("Test参数：" + req);
        WithdrawResp res = this.ucfpayService.withdrawing(req);
        System.out.println("Test获取返回：" + res);
        assertEquals("00000", res.getResCode());
    }

}
