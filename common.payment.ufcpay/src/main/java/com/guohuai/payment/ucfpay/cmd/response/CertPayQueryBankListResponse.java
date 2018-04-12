package com.guohuai.payment.ucfpay.cmd.response;

import lombok.Data;

import java.util.Map;

@Data
public class CertPayQueryBankListResponse {
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
     * 三方银行列表
     */
    private String bankList;

    private Map<String ,String > banks;

}
