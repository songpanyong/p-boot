package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Data;

@Data
public class CertPayQueryCardBinRequest {
    /**
     *银行卡号
     */
    private String bankCardNo;
    /**
     * 商户请求编号
     */
    private String merchantNo;
}
