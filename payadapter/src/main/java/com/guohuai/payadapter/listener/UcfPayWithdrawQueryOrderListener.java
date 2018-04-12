package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.ReqWithdrawQueryRequest;
import com.guohuai.payment.ucfpay.cmd.ReqWithoidingQueryRequest;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 先锋单笔代付-查询订单
 */
@Slf4j
@Component
public class UcfPayWithdrawQueryOrderListener {

    @Autowired
    private UcfpayService ucfpayService;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    @EventListener(condition = "#event.channel == '18' && #event.tradeType == 'queryOrder'")
    public void payEvent(TradeRecordEvent event) {
        String trans_no = StringUtil.uuid();//商户流水号
        log.info("先锋单笔代付-查询订单  payEvent: {},查询流水号{}", JSONObject.toJSON(event), trans_no);

        ReqWithdrawQueryRequest req = ReqWithdrawQueryRequest.builder()
                .merchantNo(event.getOrderNo()).build();

        WithdrawResp resp = new WithdrawResp();
        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            event.setReturnCode(Constant.SUCCESS);
            event.setErrorDesc("交易成功");
            return;
        }
        try {
            resp = ucfpayService.withdrawingQuery(req);
        } catch (Exception e) {
            log.error("先锋单笔代付-查询订单 异常:exception:{}", e);
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc("系统异常");
        }
        log.info("接口返回参数：" + resp);

        final String status = resp.getStatus();
        if (!StringUtil.isEmpty(status)) {
            switch (status) {
                // 成功
                case "S":
                    event.setReturnCode(Constant.SUCCESS);
                    event.setStatus(CallBackEnum.SUCCESS.getCode());
                    event.setErrorDesc("支付成功");
                    break;
                // 失败
                case "F":
                    event.setReturnCode(Constant.FAIL);
                    event.setStatus(CallBackEnum.FAIL.getCode());
                    event.setErrorDesc("支付失败");
                    break;
                // 处理中
                default:
                    event.setReturnCode(Constant.INPROCESS);
                    event.setStatus(CallBackEnum.PROCESSING.getCode());
                    event.setErrorDesc("支付处理中");
                    break;
            }
        } else {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc(resp.getResMessage());
        }
        log.info("先锋单笔代付-查询订单 返回结果,returnCode:{},returnMsg:{}", event.getReturnCode(), event.getErrorDesc());
    }

}
