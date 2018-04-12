package com.guohuai.payadapter.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTD1005IndeAuthMsgSend;
import com.guohuai.common.payment.jytpay.cmd.CmdTD1005IndeAuthMsgSendResp;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4006IndeAuthMsgConfirm;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4006IndeAuthMsgConfirmResp;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通--独立鉴权
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class JytAutheListener {

	@Autowired
	private JytpayService jytpayService;

	// @Value("${payadapter_environment}")
	// String environment;

	@EventListener(condition = "#event.tradeType == 'jytElement'")
	public void authenticationEvent(AuthenticationEvent event) {
		log.info("An event occured: {}", event);
		String flowId = event.getOrderId(); // 交易流水号
		String name = event.getUserName();
		String idCard = event.getIdentityNo();
		String bankCardNo = event.getCardNo();
		String mobile = event.getMobileNum();

		String custId = event.getCustId();
		String verifyCode = event.getVerifyCode();
		String orderId = event.getOrderId();
		String bindOrderId = event.getBindOrderId();

		// 如果没有验证码，独立鉴权短信发送
		if (StringUtils.isEmpty(verifyCode)) {
			CmdTD1005IndeAuthMsgSend cmdTD1005 = CmdTD1005IndeAuthMsgSend.builder().cust_no(custId).order_id(orderId)
					.bank_card_no(bankCardNo).id_card_no(idCard).mobile(mobile).name(name).build();
			CmdTD1005IndeAuthMsgSendResp respTD1005 = new CmdTD1005IndeAuthMsgSendResp();
			try {
				respTD1005 = this.jytpayService.indeAuthMsgSend(cmdTD1005, flowId);
			} catch (Exception e) {
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("金运通实名支付,独立鉴权异常");
				e.printStackTrace();
			}
			log.info("金运通实名支付--独立鉴权验证,{}", JSONObject.toJSONString(respTD1005));
			if ("S0000000".equals(respTD1005.getHead().getResp_code())) {
				if (!StringUtils.isEmpty(respTD1005.getBody().getBind_order_id())) {
					event.setBindOrderId(respTD1005.getBody().getBind_order_id());
					event.setReturnCode(Constant.SUCCESS);
				} else {
					log.info("金运通实名支付,返回绑卡编号为空");
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc("金运通实名支付,返回绑卡编号为空");
				}
			} else {
				log.info("金运通实名支付,独立鉴权发送短信失败");
				event.setErrorDesc(respTD1005.getHead().getResp_desc());
				event.setReturnCode(Constant.FAIL);
			}
		} else { // 如果有验证码，独立鉴权短信确认
			if (!StringUtils.isEmpty(bindOrderId)) {
				CmdTD4006IndeAuthMsgConfirm cmdTD4006 = CmdTD4006IndeAuthMsgConfirm.builder().mobile(mobile)
						.verify_code(verifyCode).bind_order_id(bindOrderId).build();
				CmdTD4006IndeAuthMsgConfirmResp respTD4006 = new CmdTD4006IndeAuthMsgConfirmResp();
				try {
					respTD4006 = this.jytpayService.indeAuthMsgConfirm(cmdTD4006, flowId);
				} catch (Exception e) {
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc("金运通实名支付,独立鉴权异常");
					e.printStackTrace();
				}
				log.info("金运通实名支付,确认绑卡返回信息{}", JSONObject.toJSONString(respTD4006));
				if ("S0000000".equals(respTD4006.getHead().getResp_code())) {
					log.info("金运通实名支付,绑卡成功");
					event.setReturnCode(Constant.SUCCESS);
				} else if ("EB000015".equals(respTD4006.getHead().getResp_code())) {//短信验证码超时
					event.setReturnCode(Constant.INPROCESS);
					if (!StringUtils.isEmpty(respTD4006.getHead().getResp_desc())) {
						event.setErrorDesc(respTD4006.getHead().getResp_desc());
					} else {
						event.setErrorDesc("输入验证码超时");
					}
				} else {
					log.info("金运通实名支付,绑卡失败{}", respTD4006.getHead().getResp_desc());
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc(respTD4006.getHead().getResp_desc());
				}
			} else {
				log.info("金运通实名支付,返回绑卡编号为空");
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("金运通实名支付,返回绑卡编号为空");
			}
		}
	}

}
