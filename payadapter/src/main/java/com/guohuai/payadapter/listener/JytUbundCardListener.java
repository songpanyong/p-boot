package com.guohuai.payadapter.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4002Ubunding;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4002UbundingResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 解除实名支付银行卡
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class JytUbundCardListener {

	@Autowired
	JytpayService jytpayService;

	@Autowired
	JytFlowIdUtils flowUtils;

	@EventListener(condition = "#event.tradeType == 'jytUnlockCard'") // 金运通实名支付解绑卡
	public void ubundCardEvent(AuthenticationEvent event) {
		log.info("金运通解除实名支付银行卡  ubundCardEvent: {}", JSONObject.toJSON(event));
		/**
		 * 实名支付查的是订单号
		 */
		String bankCardNo = event.getCardNo();
		String custId = event.getCustId();
		CmdTD4002Ubunding cmd = CmdTD4002Ubunding.builder().bank_card_no(bankCardNo).cust_no(custId).build();
		CmdTD4002UbundingResp resp = new CmdTD4002UbundingResp();
		try {
			resp = jytpayService.ubunding(cmd, flowUtils.getOneFlowId());
		} catch (Exception e) {
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("金运通实名支付，解除实名支付银行卡异常");
			e.printStackTrace();
		}
		if ("S0000000".equals(resp.getHead().getResp_code())) {
			if ("0".equals(resp.getBody().getTran_state())) {
				log.info("金运通解绑成功");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("解绑成功");
			} else {
				log.info("金运通解绑失败,{}",resp.getHead().getResp_desc());
				event.setReturnCode(Constant.FAIL);
					event.setErrorDesc("解绑失败");
			}
		} else {
			log.info("金运通解绑失败,{}",resp.getHead().getResp_desc());
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("解绑失败");
		}
	}

}
