package com.guohuai.payadapter.listener;

import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.DecryptEvent;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 先锋异步通知报文解密
 * 兼容3.0.0和4.0.0
 */
@Slf4j
@Component
public class UcfPayDecryptListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

    @Autowired
    UcfPayConfig ucfPayConfig;

    @EventListener(condition = "#event.tradeType == 'ucfPayDecrypt'")
    public void payEvent(DecryptEvent event) {

        log.info("先锋异步通知报文解密 接收....");

        org.json.JSONObject object = ucfCertPayService.AESCoderDecrypt(event.getData());
        if (null == object) {
            event.setReturnCode(Constant.VERIFY_FAIL);
            event.setErrorDesc("报文解密异常！");
            return;
        }
        log.info("先锋异步通知报文解密 内容：{}", object);

        if ("3".equals(event.getVersion())) {
            version_3_0_0(event, object);
        } else if ("4".equals(event.getVersion())) {
            version_4_0_0(event, object);
        } else {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("报文version不匹配！");
        }
        log.info("先锋异步通知报文解密 ReturnCode: {}，Status: {}，ErrorDesc: {}",
                event.getReturnCode(), event.getStatus(), event.getErrorDesc());
    }

    private void version_3_0_0(DecryptEvent event, JSONObject object) {

        if (!object.has("merchantNo") || !object.has("status")
                || !object.has("resMessage") || !object.has("tradeNo")) {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("订单必要参数不明确");
            return;
        }

        event.setPayNo(nullToStr(object.get("merchantNo")));
        event.setStatus(nullToStr(object.get("status")));
        event.setErrorDesc(nullToStr(object.get("resMessage")));
        event.setTradeNo(nullToStr(object.get("tradeNo")));
        event.setReturnCode(Constant.SUCCESS);
    }

    private void version_4_0_0(DecryptEvent event, JSONObject object) {

        if (!object.has("outOrderId") || !object.has("orderStatus")
                || !object.has("tradeNo")) {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("订单必要参数不明确");
            return;
        }

        event.setPayNo(nullToStr(object.get("outOrderId")));
        String orderStatus = nullToStr(object.get("orderStatus")).trim();
        event.setTradeNo(nullToStr(object.get("tradeNo")));

        if ("00".equals(orderStatus)) {
            event.setStatus("S");
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("支付成功！");
        } else {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("订单状态异常");
        }
    }

    private String nullToStr(Object str) {
        if (null == str) {
            return "";
        }
        return str.toString();
    }
}
