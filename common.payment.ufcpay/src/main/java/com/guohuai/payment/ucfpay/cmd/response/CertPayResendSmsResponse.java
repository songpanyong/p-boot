package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

@Data
public class CertPayResendSmsResponse {
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
}
