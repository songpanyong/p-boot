package com.guohuai.payadapter.listener;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.ucfpay.UcfPayConfig;
import com.guohuai.payment.ucfpay.api.UcfpayService;
import com.guohuai.payment.ucfpay.cmd.request.WithdrawRequest;
import com.guohuai.payment.ucfpay.cmd.request.WithoidingRequest;
import com.guohuai.payment.ucfpay.cmd.response.WithdrawResp;
import com.guohuai.payment.ucfpay.cmd.response.WithoidingResp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 先锋单笔代付
 */
@Slf4j
@Component
public class UcfPayWithdrawListener {

    @Autowired
    private UcfpayService ucfService;

    @Autowired
    CallBackDao callbackDao;

    @Autowired
    UcfPayConfig ucfPayConfig;

    @Value("${withOutThirdParty:no}")
    private String withOutThirdParty;
    
    @Value("${payadapter_environment:online}")
    private String environment;

    private final int minute = 1;// 回调间隔时间;
    private final int totalCount = 20;// 最大回调次数;

    @EventListener(condition = "#event.channel == '18' && #event.tradeType == '02'")
    public void payEvent(TradeEvent event) {
        log.info("先锋单笔代付 接收 payEvent: {}", JSONObject.toJSON(event));
        // 金额单位:分，输入单位元乘以100
        String amount = StringUtil.getMinMoneyStr(event.getAmount(), "100");

        final String userType = nullToStr(event.getAccountType());
        final boolean isPublicAccount = userType.equals("2");
        final String accountType = isPublicAccount ? "4" : "1";
        final String issuer = isPublicAccount ? nullToStr(event.getIssuer()) : "";
        if("test".equals(environment)){
        	String accountNo = "6226200103146602";
        	String accountName = "潘兴武";
        	String bankId = "CMBC";
        	event.setCardNo(accountNo);
        	event.setRealName(accountName);
        	event.setCustBankNo(bankId);
        }
        WithdrawRequest req = WithdrawRequest.builder()
                .transCur("156").userType(userType)
                .accountType(accountType)
                .merchantNo(event.getPayNo())
                .amount(amount)
                .accountNo(event.getCardNo())
                .accountName(event.getRealName())
                .bankNo(event.getCustBankNo())
                .branchProvince("").branchCity("")
                .branchName("").mobileNo("")
                .issuer(issuer).memo("").build();

        WithdrawResp resp = new WithdrawResp();

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
            resp = ucfService.withdrawing(req);
        } catch (Exception e) {
            // 处理异常当超时处理
            log.error("先锋单笔代付 异常，", e);
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
                    event.setReturnCode(Constant.INPROCESS);
                    event.setErrorDesc("先锋支付交易处理中");
                    break;
                case "I":
                    event.setReturnCode(Constant.INPROCESS);
                    event.setErrorDesc("先锋支付交易处理中");
//                    CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
//                            .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
//                            .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
//                            .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
//                    callbackDao.save(callBackInfo);// 保存交易信息
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
        log.info("先锋单笔代付 返回payEvent: {}", JSONObject.toJSON(event));

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
