package com.guohuai.payadapter.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.CertPayConfirmRequest;
import com.guohuai.payment.baofu.cmd.CertPayConfirmRequest.CertPayConfirmRequestBuilder;
import com.guohuai.payment.baofu.cmd.CertPayConfirmResp;
import com.guohuai.payment.baofu.cmd.CertPayRequest;
import com.guohuai.payment.baofu.cmd.CertPayRequest.CertPayRequestBuilder;
import com.guohuai.payment.baofu.cmd.CertPayResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BaofooPayListener
 * @Description: 宝付认证支付(短信确认)
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooPayListener {

	@Autowired
	private BaoFuService baofuService;

	@Autowired
	CallBackDao callbackDao;

	@Value("${payadapter_environment}")
	String environment;
	
	@Value("${baofoo.bindId:201604271949318660}")
	String bindId;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;

	@EventListener(condition = "#event.channel == '10' && #event.tradeType == '01'")
	public void payEvent(TradeEvent event) {
		log.info("宝付认证支付 接收  payEvent: {}", JSONObject.toJSON(event));
		if ("test".equals(environment)) {
			event.setBindId(bindId);
		}
		if (!StringUtil.isEmpty(event.getVerifyCode()) && !StringUtil.isEmpty(event.getBusinessNo())) {
			certPayConfirm(event);
		} else {
			certPayPrepare(event);
		}
	}

	/**
	 * 预支付请求
	 */
	private void certPayPrepare(TradeEvent event) {
		CertPayRequest req = installSendMsg(event);
		CertPayResp res = new CertPayResp();
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			String businessNo = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
			event.setBusinessNo(businessNo);//宝付业务流水号，用于支付推进
			event.setReturnCode(Constant.SUCCESS);
			return;
		}
		
		try {
			res = baofuService.certPayPrepare(req);
		} catch (Exception e) {
			// 处理异常当超时处理
			log.error("宝付预支付，发送短信异常，", e);
			event.setReturnCode(Constant.INPROCESS); // 超时
			event.setErrorDesc("短信发送异常");
			return;
		}
		if ("0000".equals(res.getResp_code())) {
			log.info("宝付认证支付,{}",res.getResp_msg());
			event.setReturnCode(Constant.SUCCESS);
			event.setErrorDesc("短信发送成功");
			event.setBusinessNo(res.getBusiness_no());// 交易推进参数
		} else if ("BF00114".equals(res.getResp_code())) {
			log.info("宝付认证支付,{}",res.getResp_msg());
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("订单已支付成功，请勿重新支付");
		} else if("BF00100".equals(res.getResp_code()) || "BF00112".equals(res.getResp_code()) || "BF00113".equals(res.getResp_code()) || "BF00115".equals(res.getResp_code())
				|| "BF00144".equals(res.getResp_code()) || "BF00202".equals(res.getResp_code())){
			log.error("宝付预支付异常,{}", res.getResp_msg());
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
		}else {
			log.error("宝付预支付异常,{}", res.getResp_msg());
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc( res.getResp_msg());
		}
		log.info("宝付预支付返回  payEvent: {}", JSONObject.toJSON(event));
	}

	/**
	 * 确认支付
	 */
	private void certPayConfirm(TradeEvent event) {
		CertPayConfirmRequest req = installConfirm(event);
		CertPayConfirmResp res = new CertPayConfirmResp();

		// 不经过三方直接返回结果
		if ("yes".equals(withOutThirdParty)) {
			log.info("withOutThirdParty = yes");
			String returnCode=getResultByNo(event.getPayNo());
			event.setReturnCode(returnCode);
			if(returnCode.equals(Constant.INPROCESS)){
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			}
			return;
		}

		try {
			res = baofuService.certPayConfirm(req);
		} catch (Exception e) {
			// 处理异常当超时处理
			log.error("宝付确认支付异常,{}", res.getResp_msg());
			event.setReturnCode(Constant.INPROCESS); // 超时
			event.setErrorDesc("支付异常");
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}
		
		log.info("宝付确认支付返回  res: {}", JSONObject.toJSON(res));
		if ("0000".equals(res.getResp_code())) {
			event.setErrorDesc("支付成功");
			event.setReturnCode(Constant.SUCCESS);
			
		} else if ("BF00114".equals(res.getResp_code()) || "BF00100".equals(res.getResp_code())
				|| "BF00112".equals(res.getResp_code()) || "BF00113".equals(res.getResp_code())
				|| "BF00115".equals(res.getResp_code()) || "BF00144".equals(res.getResp_code())
				|| "BF00202".equals(res.getResp_code())) {
			event.setErrorDesc(res.getResp_msg());
			event.setErrorDesc("宝付交易结果暂未知，需查询 返回代码：" + res.getResp_code() + ",返回信息：" + res.getResp_msg());
			event.setReturnCode(Constant.INPROCESS);
			log.error("宝付确认支付，交易结果暂未知,支付流水号：{}，errorCode {},errorMsg{}", event.getPayNo(), res.getResp_code(),
					res.getResp_msg());
			
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			
		} else {
			log.error("宝付确认支付异常,errorCode {},errorMsg{}", res.getResp_code(), res.getResp_msg());
			event.setErrorDesc(res.getResp_msg());
			event.setReturnCode(Constant.FAIL);
		}
		
		log.info("宝付确认支付返回  payEvent: {}", JSONObject.toJSON(event));
	}

	/**
	 * 宝付预支付参数组装
	 * 
	 * @param event
	 * @return
	 */
	private CertPayRequest installSendMsg(TradeEvent event) {
		String trans_id = event.getPayNo();
		// String trans_serial_no = event.getOrderNo();
		String trans_serial_no = trans_id;
		String bindId = event.getBindId();// 宝付绑定标识号
		// String txn_amt = changeY2F(event.getAmount());
		// 处理金额
		String txn_amt = StringUtil.getMinMoneyStr(event.getAmount(),"100");// 认证支付金额单位:分，输入单位元乘以100
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);

		CertPayRequestBuilder request = CertPayRequest.builder().trans_id(trans_id).bind_id(bindId)
				.txn_amt(txn_amt).trade_date(trade_date).trans_serial_no(trans_serial_no);
		CertPayRequest req = request.build();
		log.info("宝付预支付组装参数:{}", JSONObject.toJSON(req));
		return req;
	}

	/**
	 * 宝付确认支付参数组装
	 */
	private CertPayConfirmRequest installConfirm(TradeEvent event) {
		String trans_serial_no = event.getOrderNo();// 交易流水号(8-20
													// 位字母和数字，每次请求都不可重复)
		String businessNo = event.getBusinessNo();// 支付推进参数
		String sms_code = event.getVerifyCode();// 短信验证码
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
		System.out.println("trade_date:" + trade_date);
		CertPayConfirmRequestBuilder request = CertPayConfirmRequest.builder().business_no(businessNo)
				.sms_code(sms_code).trade_date(trade_date).trans_serial_no(trans_serial_no);
		CertPayConfirmRequest req = request.build();
		log.info("宝付确认支付组装参数:{}", JSONObject.toJSON(req));
		return req;
	}

	// public static String changeY2F(String amount) {
	// String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
	// // 或者$的金额
	// int index = currency.indexOf(".");
	// int length = currency.length();
	// Long amLong = 0l;
	// if (index == -1) {
	// amLong = Long.valueOf(currency + "00");
	// } else if (length - index >= 3) {
	// amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".",
	// ""));
	// } else if (length - index == 2) {
	// amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "")
	// + 0);
	// } else {
	// amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "")
	// + "00");
	// }
	// return amLong.toString();
	// }
	
	/**
	 * 配合挡板,根据订单号尾数判断订单成功失败
	 */
	private String getResultByNo(String payNo){
		log.info("挡板订单号:payNo{}",payNo);
		String isSuccess = Constant.SUCCESS;
		if(!StringUtil.isEmpty(payNo)){
			int length = payNo.length();
			String lastStr = payNo.substring(length-1, length);
			if("1".equals(lastStr) || "3".equals(lastStr) || "5".equals(lastStr) || "7".equals(lastStr) || "9".equals(lastStr) ){
				isSuccess = Constant.SUCCESS;
			}else if("2".equals(lastStr) || "4".equals(lastStr)  || "6".equals(lastStr)){
				isSuccess = Constant.FAIL;
			}else{
				isSuccess = Constant.INPROCESS;
			}
		}
		log.info("挡板返回:returnCode{}",isSuccess);
		return isSuccess;
	}

}
