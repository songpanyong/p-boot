package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CertPayRequest {
    /**
     * 金额
      */
    private String amount;
    /**
    *订单号						
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
    /**'
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 回调地址
     */
    private String noticeUrl;
    /**
     * 方法版本
     */
    private String methodVer ="2.0";
    /**
     * 关单时间
     */
    private String closeOrderTime;
    /**
     * 备注字段
     */
    private String memo;
    /**
     * 先锋会员ID
     */
    private String memberUserId;
    /**
     * 短信验证码
     */
    private String smsCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 支付流水
     */
    private String paymentId;
    /***
     * 交易流水
     */
    private String tradeNo;
    /**
     * 商户订单号
     */
    String merchantNo;

}
