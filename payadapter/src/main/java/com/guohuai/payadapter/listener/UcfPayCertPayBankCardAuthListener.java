package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.BankCardAuthRequest;
import com.guohuai.payment.ucfpay.cmd.response.BankCardAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 先锋认证支付-银行卡鉴权
 */
@Slf4j
@Component
public class UcfPayCertPayBankCardAuthListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

    @Autowired
    UcfPayConfig ucfPayConfig;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    @EventListener(condition = "#event.channel == '16' && #event.tradeType == 'bankCardAuth'")
    public void payEvent(AuthenticationEvent event) {
        log.info("先锋认证支付-银行卡鉴权  payEvent: {}", JSONObject.toJSON(event));
        BankCardAuthRequest req = new BankCardAuthRequest();
        req.setMerchantNo(StringUtil.uuid());
        req.setAccountNo(event.getCardNo());
        req.setAccountName(event.getUserName());
        req.setMobileNo(event.getMobileNum());
        req.setCertificateNo(event.getIdentityNo());

        BankCardAuthResponse resp = new BankCardAuthResponse();
        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("交易成功");
            return;
        }
        try {
            resp = ucfCertPayService.bankCardAuth(req);
        } catch (Exception e) {
            log.error("先锋认证支付-银行卡鉴权 异常:exception:{}", e);
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("系统异常");
        }
        log.info("接口返回参数：" + resp);

        if (!StringUtil.isEmpty(resp.getStatus()) && "S".equals(resp.getStatus())) {
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("成功");
        } else {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc(resp.getResMessage());
        }

        log.info("先锋认证支付-银行卡鉴权 返回结果,returnCode:{},returnMsg:{}", event.getReturnCode(), event.getErrorDesc());
    }
}
