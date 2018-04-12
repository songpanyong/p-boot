package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayResponse;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 先锋单笔代扣
 */
@Slf4j
@Component
public class UcfPayWithoidingListener {

    @Autowired
    private UcfpayService ucfService;

    @Autowired
    CallBackDao callbackDao;

    @Autowired
    UcfPayConfig ucfPayConfig;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;

    private final int minute = 1;// 回调间隔时间;
    private final int totalCount = 20;// 最大回调次数;

    @EventListener(condition = "#event.channel == '17' && #event.tradeType == '01'")
    public void payEvent(TradeEvent event) {
        log.info("先锋单笔代扣 接收 payEvent: {}", JSONObject.toJSON(event));
        // 金额单位:分，输入单位元乘以100
        String amount = StringUtil.getMinMoneyStr(event.getAmount(), "100");
        WithoidingRequest req = WithoidingRequest.builder()
                .transCur("156").userType("1")
                .accountType("1").certificateType("0")
                .merchantNo(event.getPayNo())
                .amount(amount)
                .accountNo(event.getCardNo())
                .accountName(event.getRealName())
                .bankId(event.getCustBankNo())
                .certificateNo(event.getCustID())
                .productName(event.getOrderDesc())
                .branchProvince("").branchCity("")
                .branchName("").productInfo("")
                .mobileNo("").expireTime("")
                .memo("").build();

        WithoidingResp resp = new WithoidingResp();

        // 不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            String returnCode = getResultByNo(event.getPayNo());
            event.setReturnCode(returnCode);
            if (returnCode.equals(Constant.INPROCESS)) {
                CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
                        .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
                        .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
                        .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
                callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
            }
            return;
        }

        try {
            resp = ucfService.withoiding(req);
        } catch (Exception e) {
            // 处理异常当超时处理
            log.error("先锋单笔代扣 异常，", e);
            event.setReturnCode(Constant.INPROCESS); // 超时
            event.setErrorDesc("支付超时");
            CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
                    .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
                    .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
                    .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
            callbackDao.save(callBackInfo);// 保存交易信息
            return;
        }
        event.setBankReturnSeriNo(nullToStr(resp.getTradeNo()));// 交易流水号
        if (!StringUtil.isEmpty(resp.getStatus())) {
            switch (resp.getStatus()) {
                case "S":
                    event.setReturnCode(Constant.SUCCESS);
                    event.setErrorDesc("支付成功");
                    break;
                case "I":
                    event.setReturnCode(Constant.INPROCESS);
                    event.setErrorDesc("支付超时");
                    CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
                            .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
                            .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
                            .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
                    callbackDao.save(callBackInfo);// 保存交易信息
                    break;
                default:
                    event.setReturnCode(Constant.FAIL);
                    event.setErrorDesc("支付失败");
                    break;
            }
        } else {
            event.setReturnCode(Constant.FAIL);
            event.setErrorDesc(resp.getResMessage());
        }
        log.info("先锋单笔代扣 返回payEvent: {}", JSONObject.toJSON(event));

    }

    /**
     * 配合挡板,根据订单号尾数判断订单成功失败
     */
    private String getResultByNo(String payNo) {
        log.info("挡板订单号:payNo{}", payNo);
        String isSuccess = Constant.SUCCESS;
        if (!StringUtil.isEmpty(payNo)) {
            int length = payNo.length();
            String lastStr = payNo.substring(length - 1, length);
            if ("1".equals(lastStr) || "3".equals(lastStr) || "5".equals(lastStr) || "7".equals(lastStr) || "9".equals(lastStr)) {
                isSuccess = Constant.SUCCESS;
            } else if ("2".equals(lastStr) || "4".equals(lastStr) || "6".equals(lastStr)) {
                isSuccess = Constant.FAIL;
            } else {
                isSuccess = Constant.INPROCESS;
            }
        }
        log.info("挡板返回:returnCode{}", isSuccess);
        return isSuccess;
    }

    private String nullToStr(Object str) {
        if (null == str) {
            return "";
        }
        return str.toString();
    }
}
