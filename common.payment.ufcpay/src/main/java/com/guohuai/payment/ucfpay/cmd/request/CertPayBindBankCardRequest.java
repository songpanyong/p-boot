package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

@Data
public class CertPayBindBankCardRequest {
    /**
     * 商户请求编号
     */
    private String merchantNo;
    /**
     * 订单号
     */
    private String outOrderId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 手机号
     */
    private String mobileNo;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 身份证
     */
    private String cardNo;
    /**
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 回调地址
     */
    private String noticeUrl;
}
