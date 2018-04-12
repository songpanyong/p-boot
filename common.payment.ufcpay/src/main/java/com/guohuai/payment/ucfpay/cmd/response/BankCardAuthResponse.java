package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

@Data
public class BankCardAuthResponse {
    /**
     * 错误代码
     */
    private String resCode;
    /**
     * 错误内容
     */
    private String resMessage;
    /**
     * 商户请求编号
     */
    private String merchantNo;
    /**
     * 保留域
     */
    private String memo;
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 交易订单号
     */
    private String tradeNo;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 交易完成时间
     */
    private String tradeTime;
}
