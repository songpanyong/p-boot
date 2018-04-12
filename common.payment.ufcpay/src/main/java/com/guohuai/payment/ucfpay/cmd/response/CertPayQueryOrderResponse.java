package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

@Data
public class CertPayQueryOrderResponse {
    /**
     * 错误代码
     */
    private String resCode;
    /**
     * 错误内容
     */
    private String resMessage;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 商户号
     */
    private String merchantId;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误描述
     */
    private String errorMessage;
}
