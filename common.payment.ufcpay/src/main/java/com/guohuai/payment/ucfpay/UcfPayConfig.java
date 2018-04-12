package com.guohuai.payment.ucfpay;

import feign.Logger;
import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Logger.Level;

/**
 * @author CHENDONGHUI
 */
@Configuration
@Data
public class UcfPayConfig {

    @Value("${ucfpay.merId:M200000509}")
    String merId;

    @Value("${ucfpay.merRSAKey:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0nxlGowSQH64EjBczV4cvLre12j9Uy1M15QuJNkzSS0scTUi3QTUe29eJ/oCclEhXDeQRETb1CM1mlaq80aEWz21DwD9tO/8uwT6azDRtZ+M/8jPx+dZa8h8eNk3Ki97DJFXGCx9I4YsswdcoEE37c68OcRA0t7o2lgjpEq960QIDAQAB}")
    String merRSAKey;

    @Value("${ucfpay.secId:RSA}")
    String secId;

    @Value("${ucfpay.gateway:http://sandbox.firstpay.com/security/gateway.do}")
    String gateway;

    @Value("${ucfpay.returnUrl:http://1.2.7.1:8080/withoiding/ReceiveReturn}")
    String returnUrl;

    @Value("${ucfpay.noticeUrl:http://115.28.58.108/settlement/noticeUrl/callback}")
    String noticeUrl;

    @Value("${ucfpay.notice_4_0_0_Url:http://115.28.58.108/settlement/noticeUrl/callback}")
    String notice_4_0_0_Url;

    @Value("${ucfpay.withoiding.service:REQ_WITHOIDING}")
    String withoidingService;

    @Value("${ucfpay.withoidingQuery.service:REQ_WITHOIDING_QUERY}")
    String withoidingQueryService;

    @Value("${ucfpay.version:3.0.0}")
    String version;

    @Value("${ucfpay.version_4_0_0:4.0.0}")
    String version_4_0_0;

    @Value("${ucfpay.withdraw.service:REQ_WITHDRAW}")
    String withdrawService;

    @Value("${ucfpay.withdrawQuery.service:REQ_WITHDRAW_QUERY_BY_ID}")
    String withdrawQueryService;

    @Value("${ucfpay.certPayPrepareService.service:MOBILE_CERTPAY_API_PREPARE_PAY}")
    String certPayPrepareService;

    @Value("${ucfpay.certPayConfirm.service:MOBILE_CERTPAY_API_IMMEDIATE_PAY}")
    String certPayConfirmService;

    @Value("${ucfpay.certPayQueryOrderStatusService.service:MOBILE_CERTPAY_QUERYORDERSTATUS}")
    String certPayQueryOrderStatusService;

    @Value("${ucfpay.certPayUnbindCardService.service:MOBILE_CERTPAY_API_UNBIND_CARD}")
    String certPayUnbindCardService;

    @Value("${ucfpay.certPayQueryCardBinService.service:MOBILE_CERTPAY_OUT_QUERY_CARD_BIN}")
    String certPayQueryCardBinService;

    @Value("${ucfpay.certPayGetBankListService.service:MOBILE_CERTPAY_API_GET_BANKLIST}")
    String certPayGetBankListService;

    @Value("${ucfpay.certPayResendSmsService.service:MOBILE_CERTPAY_API_SEND_SMS}")
    String certPayResendSmsService;

    @Value("${ucfpay.bankCardAuthService.service:REQ_BANKCARD_AUTH}")
    String bankCardAuthService;

    @Value("${ucfpay.certPayBindBankCardService.service:MOBILE_CERTPAY_BINDBANKCARD}")
    String certPayBindBankCardService;

    @Value("${ucfpay.source:1}")
    String source;

    UcfPayClient ucfpayClient = null;

    public UcfPayClient createNowPayClient() {
        if (null == ucfpayClient) {
            ucfpayClient = Feign.builder().logLevel(Level.FULL).logger(new Logger.JavaLogger().appendToFile("http.log"))
                    .target(UcfPayClient.class, gateway);
        }
        return ucfpayClient;
    }
}
