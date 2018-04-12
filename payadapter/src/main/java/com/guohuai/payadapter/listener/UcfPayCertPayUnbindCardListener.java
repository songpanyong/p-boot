package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayUnbindCardRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayUnbindCardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 先锋认证支付-解绑银行卡
 */
@Slf4j
@Component
public class UcfPayCertPayUnbindCardListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    @EventListener(condition = "#event.channel == '16' && #event.tradeType == 'unbindCard'")
    public void unbindCardEvent(AuthenticationEvent event) {
        log.info("先锋认证支付-解绑银行卡 {}", JSONObject.toJSON(event));
        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("解绑成功");
            return;
        }
        CertPayUnbindCardRequest req = new CertPayUnbindCardRequest();
        req.setBankCardNo(event.getCardNo());
        req.setUserId(event.getUserOid());
        req.setMerchantNo(UUID.randomUUID().toString().substring(0, 30));

        try {
            CertPayUnbindCardResponse resp = ucfCertPayService.certPayUnbindCard(req);
            if (!StringUtil.isEmpty(resp.getStatus()) && "00".equals(resp.getStatus())) {
                event.setReturnCode(Constant.SUCCESS);
                event.setErrorDesc("解绑成功");
                log.info("先锋认证支付-解绑银行卡 解绑成功");
            } else {
                event.setReturnCode(Constant.FAIL);
                if (!StringUtil.isEmpty(resp.getResCode())) {
                    event.setErrorDesc(resp.getResMessage());
                } else {
                    event.setErrorDesc(resp.getRespMsg());
                }
                log.info("先锋认证支付-解绑银行卡 解绑失败: {}", event.getErrorDesc());
            }
        } catch (Exception e) {
            log.error("先锋认证支付-解绑银行卡 解绑异常:exception:{}", e);
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("解绑异常");
        }
    }
}
