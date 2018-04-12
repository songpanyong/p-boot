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
public class CertPayPrepareTest {

    @Autowired
    UcfCertPayService ucfCertPayService;

    @Test
    public void test() {
        CertPayRequest req = new CertPayRequest();
        req.setAmount("120");
        req.setOutOrderId(UUID.randomUUID().toString().substring(0, 30));
        req.setUserId("500666");
        req.setMobileNo("15213649877");
        req.setRealName("潘兴武");
        req.setCardNo("42062119900109121X");
        req.setBankCardNo("6226200103146602");
        req.setBankCode("CMBC");
        req.setNoticeUrl("http://114.215.133.84:8883/settlement/noticeUrl/callback");
//        req.setCloseOrderTime("10");
        req.setMerchantNo(UUID.randomUUID().toString());
//        req.setMemo("备注");

//        System.out.println("Test参数：" + req);
        CertPayResponse res = this.ucfCertPayService.certPayPrepare(req);
//        System.out.println("Test获取返回：" + res);
        Assert.assertEquals("00", res.getStatus());
    }

    @Test
    public void Test() {

        String data="gG7tPTiqllEE68OcKP/cW9T63c9zVu8cwmeHBeBSUADh+shQxH5rijhd+HkxE65p8dONpREnSpqgUWSWKfHac4SsHZnG5oSLtZNCrr4sex8M7ZWc9/2gZ1AD73RWNy1HqYn1dTdVd5D30DCFHqDpA2oHcLpXJmx0KcCCW6SerMz2WEwrNKujFnRlCRbhaDTuDgE1viNG9ycOtg3BeDVHaPTxI48MQ2IYPz4I6DFY6AOBtrp9Aa3OAimQoNaf3f2ZsKemIy2rSOW5Y7jBFpFZxC0S3cy3rjw3PQXh07nfH1sstHwa20UEXslp4nkkGSt6l0qQ3C8IT0PjXt+ZFml20oQLaNZoUV0RUQZG3aABEoddSSMoTjyxKHTwqVWdOhMfLbIAyHVZgyCnZFkEwljYnqNbmBOpTsN2zTDFPuHXmeWZhXZ4OTg//nDWB6L5CLP7RF+tsM2ji6tDyo1pw7pz+1NInIAMeoTlIdo6eYgMaPIYCwFZuG1A9VEn2CLN8gtDRFUkhgSrWs81hk6JXxftsRTPUt9SMRbrS4xO+VYWfEBWP04w8xDv4KpWhnI+6JN6";

        ucfCertPayService.AESCoderDecrypt(data);
        data = "5k3fWE/7Ipw0p/pH+f2odEpP5cbGbYo6oyIZVA8ZDA8h7bbAbGbT3+ofyGED90K+8Ep+BQXl9dCjsQp94ydhtkgia95/G+kBn6TYptyWGyAOjLtIPMx6g9Sw6PpU9Y9xRH9PRSsS9cBOYjYGtvI3yPl/p6A8IbRmwPjdNW8q5ptz79yIJO5amPFUG0E5GJf8Ru+HOkSNLV/xPEpSZYfaZEvcwzDLLnsVMRu1DaeoFXbmNLWiC6PW71x22gwv5gqfYTGFToDTLnGHf2ooCdoUmhkj/4+wSoe2G+SRVqQ/cNb0FwL+own1YL5TlLYBwi+Ud65rZx6VTpRbXZT2fEYHXymlFizQ4vXjNkANE5QJN+IYA42z6avOloZhQVpXmDyEXsqjT+ahaAhAF+yfaspTXIP/wCC34PClEbFH4SD05vy1PjgyBsaasOa2JjDwLkYyn2Wj/E+mE4hyhdMfo/x4JPrtKPG7+k7PX4XR76hsWoA=";
        ucfCertPayService.AESCoderDecrypt(data);

    }
}
