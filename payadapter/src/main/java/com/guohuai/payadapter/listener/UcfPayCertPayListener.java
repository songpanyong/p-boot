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
import com.guohuai.payment.ucfpay.api.UcfCertPayService;
import com.guohuai.payment.ucfpay.cmd.request.CertPayRequest;
import com.guohuai.payment.ucfpay.cmd.response.CertPayResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 先锋认证支付
 */
@Slf4j
@Component
public class UcfPayCertPayListener {

    @Autowired
    private UcfCertPayService ucfCertPayService;

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

    @EventListener(condition = "#event.channel == '16' && #event.tradeType == '01'")
    public void payEvent(TradeEvent event) {
        log.info("先锋认证支付 接收 payEvent: {}", JSONObject.toJSON(event));
        if (StringUtil.isEmpty(event.getVerifyCode())) {
            preparePay(event);// 预支付
        } else {
            confirmPay(event);// 确认支付
        }
    }

    /**
     * 预支付请求
     */
    private void preparePay(TradeEvent event) {
        CertPayRequest req = installSendMsg(event);
        CertPayResponse resp;

        //不经过三方直接返回结果
        if ("yes".equals(withOutThirdParty)) {
            log.info("withOutThirdParty = yes");
            String businessNo = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
            event.setBusinessNo(businessNo);//业务流水号，用于支付推进
            event.setReturnCode(Constant.SUCCESS);
            return;
        }
        try {
            resp = ucfCertPayService.certPayPrepare(req);
        } catch (Exception e) {
            // 处理异常当超时处理
            log.error("先锋认证支付-预支付 发送短信异常，", e);
            event.setReturnCode(Constant.FAIL); // 超时
            event.setErrorDesc("短信发送异常");
            return;
        }
        event.setBindId(nullToStr(resp.getMemberUserId())); // 绑定协议号
        event.setOutPaymentId(nullToStr(resp.getPaymentId())); // 支付流水号
        event.setOutTradeNo(nullToStr(resp.getTradeNo()));// 交易流水号

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
        log.info("先锋认证支付-预支付 返回payEvent: {}", JSONObject.toJSON(event));
    }

    /**
     * 确认支付
     */
    private void confirmPay(TradeEvent event) {
        CertPayRequest req = installConfirm(event);
        CertPayResponse resp;

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
            resp = ucfCertPayService.certPayConfirm(req);
        } catch (Exception e) {
            // 处理异常当超时处理
            log.error("先锋认证支付-确认支付 异常,{}", e.getMessage());
            event.setReturnCode(Constant.INPROCESS); // 超时
            event.setErrorDesc("支付异常");
            CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
                    .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
                    .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
                    .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
            callbackDao.save(callBackInfo);// 保存交易信息
            return;
        }

        log.info("先锋认证支付-确认支付 返回resp: {}", JSONObject.toJSON(resp));

        if (!StringUtil.isEmpty(resp.getStatus()) && "00".equals(resp.getStatus())) {

            if (!StringUtil.isEmpty(resp.getOrderStatus()) && "S".equals(resp.getOrderStatus())) {
                event.setReturnCode(Constant.INPROCESS);
                event.setErrorDesc("先锋支付交易处理中");
            } else {
                // 超时
                event.setReturnCode(Constant.INPROCESS);
                event.setErrorDesc("支付状态未知");
                log.error("先锋认证支付-确认支付 交易结果暂未知,支付流水号：{}，status：{},orderStatus：{},respMsg：{}",
                        resp.getStatus(), event.getPayNo(), resp.getOrderStatus(), resp.getRespMsg());
                CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
                        .tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
                        .minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
                        .status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
                callbackDao.save(callBackInfo);
            }
        } else {
            event.setReturnCode(Constant.FAIL);
            if (!StringUtil.isEmpty(resp.getResCode())) {
                event.setErrorDesc(resp.getResMessage());
            } else {
                event.setErrorDesc(resp.getRespMsg());
            }
            log.error("先锋认证支付-确认支付 异常,status：{},resMessage：{},orderStatus：{},respMsg：{}",
                    resp.getStatus(), resp.getResMessage(), resp.getOrderStatus(), resp.getRespMsg());
        }
        log.info("先锋认证支付-确认支付 返回payEvent: {}", JSONObject.toJSON(event));
    }

    /**
     * 预支付参数组装
     */
    private CertPayRequest installSendMsg(TradeEvent event) {
        // 认证支付金额单位:分，输入单位元乘以100
        String amount = StringUtil.getMinMoneyStr(event.getAmount(), "100");
        CertPayRequest req = new CertPayRequest();
        req.setAmount(amount);
        req.setOutOrderId(event.getPayNo());
        req.setUserId(event.getUserOid());
        req.setMobileNo(event.getMobile());
        req.setRealName(event.getRealName());
        req.setCardNo(event.getCustID());
        req.setBankCardNo(event.getCardNo());
        req.setBankCode(event.getCustBankNo());
        req.setNoticeUrl(ucfPayConfig.getNotice_4_0_0_Url());
        req.setMerchantNo(UUID.randomUUID().toString());
//        req.setCloseOrderTime("");
//        req.setMemo("");
        if("test".equals(environment)){
        	String userId = "500666";
        	String accountNo = "6226200103146602";
            String accountName = "潘兴武";
            String bankId = "CMBC";
            String certificateNo = "42062119900109121X";
            String bankName = "民生银行";
            req.setRealName(accountName);
            req.setCardNo(certificateNo);
            req.setBankCardNo(accountNo);
            req.setBankCode(bankId);
            req.setBankName(bankName);
            req.setUserId(userId);
          
        }
        log.info("先锋认证支付-预支付 组装参数：{}", JSONObject.toJSON(req));
        return req;
    }

    /**
     * 确认支付参数组装
     */
    private CertPayRequest installConfirm(TradeEvent event) {

        // 认证支付金额单位:分，输入单位元乘以100
        String amount = StringUtil.getMinMoneyStr(event.getAmount(), "100");

        CertPayRequest req = new CertPayRequest();
        req.setMerchantNo(UUID.randomUUID().toString());

        req.setMemberUserId(event.getBindId());
        req.setSmsCode(event.getVerifyCode()); // 短信验证码
        req.setBankName(event.getBankName());
        req.setPaymentId(event.getOutPaymentId()); // 支付流水号
        req.setTradeNo(event.getOutTradeNo()); // 交易流水号
        req.setAmount(amount);
        req.setOutOrderId(event.getPayNo());
        req.setUserId(event.getUserOid());
        req.setMobileNo(event.getMobile());
        req.setRealName(event.getRealName());
        req.setCardNo(event.getCustID());
        req.setBankCardNo(event.getCardNo());
        req.setBankCode(event.getCustBankNo());
        if("test".equals(environment)){
        	String userId = "500666";
        	String accountNo = "6226200103146602";
            String accountName = "潘兴武";
            String bankId = "CMBC";
            String certificateNo = "42062119900109121X";
            String bankName = "民生银行";
            req.setRealName(accountName);
            req.setCardNo(certificateNo);
            req.setBankCardNo(accountNo);
            req.setBankCode(bankId);
            req.setBankName(bankName);
            req.setUserId(userId);
        }
        log.info("先锋认证支付-确认支付 组装参数:{}", JSONObject.toJSON(req));
        return req;
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
