package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CertPayQueryOrderRequest {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 商户请求编号
     */
    private String merchantNo;

}
