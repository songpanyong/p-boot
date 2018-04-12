package com.guohuai.payment.ucfpay.api;

import com.guohuai.payment.ucfpay.cmd.request.*;
import com.guohuai.payment.ucfpay.cmd.response.*;
import org.json.JSONObject;

public interface UcfCertPayService {
    /**
     * 认证支付（预支付）
     */
    CertPayResponse certPayPrepare(CertPayRequest request);

    /**
     * 认证支付（确认支付）
     */
    CertPayResponse certPayConfirm(CertPayRequest request);

    /**
     * 订单查询
     */
    CertPayQueryOrderResponse certPayQueryOrder(CertPayQueryOrderRequest request);

    /**
     * 解绑银行卡
     */
    CertPayUnbindCardResponse certPayUnbindCard(CertPayUnbindCardRequest request);

    /**
     * 查询卡bin
     */
    CertPayQueryCardBinResponse certPayQueryCardBin(CertPayQueryCardBinRequest request);

    /**
     * 查询支持银行卡列表
     */
    CertPayQueryBankListResponse certPayQueryBankList();

    /**
     * 重发短信验证码
     */
    CertPayResendSmsResponse certPayResendSms(CertPayResendSmsRequest request);

    /**
     * 银行卡鉴权
     */
    BankCardAuthResponse bankCardAuth(BankCardAuthRequest request);

    /**
     *单独绑卡
     */
    CertPayBindBankCardResponse certPayBindBankCard(CertPayBindBankCardRequest request);

    /**
     * 解密异步通知报文
     */
    JSONObject AESCoderDecrypt(String data);
}
