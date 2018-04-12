package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

@Data
public class CertPayQueryCardBinResponse {
    /**
     * 错误代码
     */
    private String resCode;
    /**
     * 错误内容
     */
    private String resMessage;
    /**
     * /**
     * 卡类型
     */
    private String cardType;
    /**
     * 返回消息
     */
    private String respMsg;
    /**
     * 返回状态
     */
    private String status;
    /**
     * 银行编码
     */
    private String bankCode;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行卡号
     */
    private String bankCardNo;
}
