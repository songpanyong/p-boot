package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayQueryCardBinRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayQueryCardBinResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 先锋认证支付-查询卡bin
 */
@Slf4j
@Component
public class UcfPayCertPayQueryCardBinListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    @EventListener(condition = "#event.channel == '16'  && #event.tradeType == 'queryCardBin'")
    public void payEvent(TradeEvent event) {
        log.info("先锋认证支付-查询卡bin  payEvent: {}", JSONObject.toJSON(event));
        CertPayQueryCardBinRequest req = new CertPayQueryCardBinRequest();
        req.setMerchantNo(StringUtil.uuid());
        req.setBankCardNo(event.getCardNo());

        CertPayQueryCardBinResponse resp = new CertPayQueryCardBinResponse();
        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("交易成功");
            return;
        }
        try {
            resp = ucfCertPayService.certPayQueryCardBin(req);
        } catch (Exception e) {
            log.error("先锋认证支付-查询卡bin 异常:exception:{}", e);
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("系统异常");
        }
        log.info("接口返回参数：" + resp);

        event.setBankCode(nullToStr(resp.getBankCode()));
        event.setBankName(nullToStr(resp.getBankName()));
        event.setCardType(nullToStr(resp.getCardType()));

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

        log.info("先锋认证支付-查询卡bin 返回结果,returnCode:{},returnMsg:{}", event.getReturnCode(), event.getErrorDesc());
    }

    private String nullToStr(Object str) {
        if (null == str) {
            return "";
        }
        return str.toString();
    }

}
