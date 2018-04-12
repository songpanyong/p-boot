package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

@Data
public class BankCardAuthRequest {
    /**
     * 商户请求编号
     */
    private String merchantNo;
    /**
     * 账户号
     */
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 证件类型
     */
    private String certificateType = "0";
    /**
     * 证件号码
     */
    private String certificateNo;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 后台通知地址
     */
    private String noticeUrl;
    /**
     * 保留域
     */
    private String memo;
}
