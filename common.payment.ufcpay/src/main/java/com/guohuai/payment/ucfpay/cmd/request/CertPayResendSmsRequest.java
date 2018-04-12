package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

@Data
public class CertPayResendSmsRequest {
    /**
     * 商户请求编号
     */
    private String merchantNo;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 方法版本
     */
    private String methodVer = "2.0";
}
