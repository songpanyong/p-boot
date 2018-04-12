package com.guohuai.payadapter.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.BankUtilDao;
import com.guohuai.payadapter.bankutil.BankUtilEntity;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.BindConfirmCardRequest;
import com.guohuai.payment.baofu.cmd.BindConfirmCardResp;
import com.guohuai.payment.baofu.cmd.BindPreCardRequest;
import com.guohuai.payment.baofu.cmd.BindPreCardResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 宝付--独立鉴权
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class BaofooAutheListener {

	@Autowired
	BaoFuService baofuService;

	@Autowired
	BankUtilDao bankUtilDao;

	@Autowired
	BankUtilService bankUtilService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

//	@EventListener(condition = "#event.tradeType == 'baofooElement'")
	@EventListener(condition = "#event.channel == '10'  && #event.tradeType == 'bankCardAuth'")
	public void authenticationEvent(AuthenticationEvent event) {
		log.info("An event occured: {}", event);
		String trans_id = event.getOrderId();// 商户订单号
		String trans_serial_no = event.getTransNo();// 商户流水号
		String name = event.getUserName();
		String idCard = event.getIdentityNo();
		String bankCardNo = event.getCardNo();
		String mobile = event.getMobileNum();

		String verifyCode = event.getVerifyCode();
		String pay_code = "";// 银行编号
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
		if (!StringUtil.isEmpty(bankCardNo)) {
			try {
				BankUtilEntity bank = bankUtilService.getBankByCard(bankCardNo);
				pay_code = bankUtilDao.getChannelbankCodeByBin(bank.getBankCode());
			} catch (Exception e) {
				e.printStackTrace();
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("绑卡异常");
				log.error("宝付绑卡，获取银行编码异常");
				return;
			}
		}

		// 如果没有验证码，独立鉴权短信发送
		if (StringUtils.isEmpty(verifyCode)) {
			BindPreCardRequest reqPre = BindPreCardRequest.builder().trans_id(trans_id).trans_serial_no(trans_serial_no)
					.acc_no(bankCardNo).id_card(idCard).id_holder(name).mobile(mobile).pay_code(pay_code)
					.trade_date(trade_date).build();
			BindPreCardResp respPre = new BindPreCardResp();
			
			//不经过三方直接返回结果
			if("yes".equals(withOutThirdParty)){
				log.info("withOutThirdParty = yes");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
				return;
			}
			
			try {
				respPre = this.baofuService.preBindCard(reqPre);
				log.info("宝付预绑卡返回参数,{}", JSONObject.toJSONString(respPre));
				if ("0000".equals(respPre.getResp_code())) {
					event.setBindId(respPre.getBind_id());// 返回绑定标识号
					event.setReturnCode(Constant.SUCCESS);
					event.setErrorDesc(respPre.getResp_msg());
				} else {
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc(respPre.getResp_msg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("宝付预绑卡异常");
				log.error("宝付预绑卡异常:exception:{}", e);
			}
		} else { // 如果有验证码，独立鉴权短信确认
			trans_id=event.getBindOrderId();
			BindConfirmCardRequest reqCon = BindConfirmCardRequest.builder().trans_id(trans_id)
					.trans_serial_no(trans_serial_no).sms_code(verifyCode).trade_date(trade_date).build();
			BindConfirmCardResp respCon = new BindConfirmCardResp();
			
			//不经过三方直接返回结果
			if("yes".equals(withOutThirdParty)){
				log.info("withOutThirdParty = yes");
				String bind_id = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
				event.setBindId(bind_id);// 返回绑定标识号
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
				return;
			}
			
			try {
				respCon = this.baofuService.confirmBindCard(reqCon);
				log.info("宝付确认绑卡返回参数,{}", JSONObject.toJSONString(respCon));
				if ("0000".equals(respCon.getResp_code())) {
					event.setBindId(respCon.getBind_id());// 返回绑定标识号
					event.setReturnCode(Constant.SUCCESS);
					event.setErrorDesc(respCon.getResp_msg());
				} else {
					event.setReturnCode(Constant.FAIL);
					event.setErrorDesc(respCon.getResp_msg());
				}
			} catch (Exception e) {
				e.printStackTrace();
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("宝付确认绑卡异常");
				log.error("宝付确认绑卡异常:exception:{}", e);
			}
		}
	}

}
