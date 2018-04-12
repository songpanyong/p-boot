package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayResendSmsRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayResendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 先锋认证支付-重发短信
 */
@Slf4j
@Component
public class UcfPayCertPayResendSmsListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    @EventListener(condition = "#event.channel == '16' && #event.tradeType == 'reVerifiCode'")
    public void resendSms(TradeEvent event) {
        log.info("先锋认证支付-重发短信 接收 payEvent: {}", JSONObject.toJSON(event));

        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            String businessNo = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
            event.setBusinessNo(businessNo);//业务流水号，用于支付推进
            event.setReturnCode(Constant.SUCCESS);
            return;
        }

        CertPayResendSmsRequest req = new CertPayResendSmsRequest();
        req.setMerchantNo(UUID.randomUUID().toString().substring(0, 30));
        req.setMobileNo(event.getPhone());
        req.setUserId(event.getUserOid());

        CertPayResendSmsResponse resp;
        try {
            resp = ucfCertPayService.certPayResendSms(req);
        } catch (Exception e) {
            // 处理异常当超时处理
            log.error("先锋认证支付-重发短信 发送短信异常，", e);
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("短信发送异常");
            return;
        }

        if (!StringUtil.isEmpty(resp.getStatus()) && "00".equals(resp.getStatus())) {
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("成功");
        } else {
            event.setReturnCode(Constant.FAIL);
            if (!StringUtil.isEmpty(resp.getResCode())) {
                event.setErrorDesc(resp.getResMessage());
            } else {
                event.setErrorDesc(resp.getRespMsg());
            }
        }
        log.info("先锋认证支付-重发短信 返回payEvent: {}", JSONObject.toJSON(event));
    }
}
