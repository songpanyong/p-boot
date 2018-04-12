package com.guohuai.payadapter.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTD1002SecondAuthe;
import com.guohuai.common.payment.jytpay.cmd.CmdTD1002SecondAutheResp;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4004SecondPayee;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4004SecondPayeeResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.component.TradeChannel;
import com.guohuai.payadapter.component.TradeType;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通实名代收-收款 二次支付请求鉴权---二次支付交易
 * 
 * @author hans
 *
 */
@Slf4j
@Component
@Transactional
public class JytPayeeListener {

	@Autowired
	private JytpayService jytpayService;

	@Autowired
	CallBackDao callbackDao;
	
	@Autowired
	JytFlowIdUtils utils;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;

	@EventListener(condition = "#event.channel == '8' && #event.tradeType == '01'")//申购
	@Transactional
	public void jytPayeeEvent(TradeEvent event) {

		log.info("An event occured: {}", event);
		String orderId = event.getPayNo();// 订单号
		String flowId = orderId; // // payNo交易流水号,orderNo交易订单号
//		String flowId = utils.getOneFlowId(orderId); // // payNo交易流水号,orderNo交易订单号
		String custId = event.getUserOid();// 客户号
		String bankCardNo = event.getCardNo();// 银行卡号
		String tranAmt = StringUtil.getMoneyStr(event.getAmount());
		String verifyCode = event.getVerifyCode();// 二次鉴权验证码
		String mobile = event.getMobile();// 手机号

		// 如果没有验证码，发送短信
		if (StringUtil.isEmpty(verifyCode)) {
			CmdTD1002SecondAuthe cmdTD1002 = CmdTD1002SecondAuthe.builder().cust_no(custId).order_id(orderId)
					.bank_card_no(bankCardNo).tran_amount(tranAmt).build();
			CmdTD1002SecondAutheResp respTD1002 = new CmdTD1002SecondAutheResp();
			
			//不经过三方直接返回结果
			if("yes".equals(withOutThirdParty)){
				log.info("withOutThirdParty = yes");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
				CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo()).tradeType(event.getTradeType())
						.payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank").minute(minute)
						.totalCount(totalCount).totalMinCount(20).countMin(0).status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
				callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
				return;
			}
			
			try {
				respTD1002 = this.jytpayService.secondAuthe(cmdTD1002, flowId);
			} catch (Exception e) {
				log.error("金运通实名支付，交易发送短信异常，", e);
				event.setReturnCode(Constant.INPROCESS);
				event.setErrorDesc("金运通实名支付，交易发送短信异常");
			}
			log.info("实名支付鉴权验证,{}", JSONObject.toJSONString(respTD1002));
			if ("S0000000".equals(respTD1002.getHead().getResp_code())) {
				if ("01".equals(respTD1002.getBody().getTran_state())) {
					log.info("金运通实名支付,短信验证码发送成功");
					event.setReturnCode(Constant.SUCCESS);
					event.setErrorDesc("短信验证码发送成功");
				} else {
					log.info("平台验证失败,{}",respTD1002.getBody().getRemark());
					event.setReturnCode(Constant.FAIL);
					if (!StringUtil.isEmpty(respTD1002.getBody().getRemark())) {
						event.setErrorDesc(respTD1002.getBody().getRemark());//这里返回S0000000，head中的resp_desc为下单成功，所以只能取body中的remark
					} else {
						event.setErrorDesc("平台验证失败");
					}
				}
			} else {
				log.info("实名支付,{}",respTD1002.getHead().getResp_desc());
				event.setErrorDesc("短信验证码发送失败");
				event.setReturnCode(Constant.FAIL);
			}
		} else { // 如果有验证码，二次支付交易
			CmdTD4004SecondPayee cmdTD4006 = CmdTD4004SecondPayee.builder().mobile(mobile).verify_code(verifyCode)
					.order_id(orderId).build();
			CmdTD4004SecondPayeeResp respTD4006 = new CmdTD4004SecondPayeeResp();
			try {
				respTD4006 = this.jytpayService.secondPayee(cmdTD4006, flowId);
			} catch (Exception e) {
				log.error("金运通实名支付，请求交易异常，", e);
				event.setReturnCode(Constant.INPROCESS); // 异常当交易超时处理
				event.setErrorDesc("交易异常");
				CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
						.tradeType(TradeType.pay.getValue()).payNo(event.getPayNo())
						.channelNo(TradeChannel.jinyuntongpayee.getValue()).type("bank")
						.status(CallBackEnum.INIT.getCode()).minute(minute).totalCount(totalCount).totalMinCount(20)
						.countMin(0).createTime(new Date()).build();
				callbackDao.save(callBackInfo);// 保存交易信息,上送的交易流水号和返回的流水号一致
				return;
			}
			log.info("金运通实名支付,二次支付交易信息{}", JSONObject.toJSONString(respTD4006));
			if ("S0000000".equals(respTD4006.getHead().getResp_code()) || "E0000000".equals(respTD4006.getHead().getResp_code())) {
				if ("00".equals(respTD4006.getBody().getTran_state())) {
					Date date = null;
					try {
						date = new SimpleDateFormat("YYYYMMDDHHmmss").parse(respTD4006.getBody().getTran_date());
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
					event.setTradeTime(date);// 商户交易开始时间，格式为YYYYMMDDHHmmss
					event.setErrorDesc("交易成功");
					event.setReturnCode(Constant.SUCCESS);
				} else if ("03".equals(respTD4006.getBody().getTran_state())) {
					log.info("金运通实名支付,交易失败,{}",respTD4006.getHead().getResp_desc());
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc(respTD4006.getHead().getResp_desc());
				} else {
					log.info("金运通实名支付,交易处理中");
					event.setReturnCode(Constant.INPROCESS);
					event.setErrorDesc("交易处理中");
					CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
							.tradeType(TradeType.pay.getValue()).payNo(event.getPayNo())
							.channelNo(TradeChannel.jinyuntongpayee.getValue()).type("bank")
							.status(CallBackEnum.INIT.getCode()).minute(minute).totalCount(totalCount).totalMinCount(20)
							.countMin(0).createTime(new Date()).build();
					callbackDao.save(callBackInfo);// 保存交易信息,上送的交易流水号和返回的流水号一致
				}
			} else {
				log.info("金运通实名支付,二次支付交易失败{}", respTD4006.getHead().getResp_desc());
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc(respTD4006.getHead().getResp_desc());
			}
		}

	}

}
