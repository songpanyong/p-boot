package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

@Data
public class CertPayResponse {
    /**
     * 错误代码
     */
    private String resCode;
    /**
     * 错误内容
     */
    private String resMessage;
    /**
     * 返回消息
     */
    private String respMsg;
    /**
     * 返回状态
     */
    private String status;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 交易流水号
     */
    private String tradeNo;
    /**
     * 支付流水号
     */
    private String paymentId;
    /**
     * 先锋会员ID
     */
    private String memberUserId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 订单号
     */
    private String outOrderId;
    /**
     * 支付渠道
     */
    private String payChannel;

    private String authStatus;
}
