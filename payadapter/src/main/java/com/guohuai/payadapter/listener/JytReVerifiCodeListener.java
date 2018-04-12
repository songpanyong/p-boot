package com.guohuai.payadapter.listener;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4003ReVerifiCode;
import com.guohuai.common.payment.jytpay.cmd.CmdTD4003ReVerifiCodeResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通实名支付-重新获取验证码
 * @author hans
 *
 */

@Slf4j
@Component
public class JytReVerifiCodeListener {
	
	@Autowired
	JytpayService jytpayService;

	@Autowired
	JytFlowIdUtils flowUtils;
	
	@EventListener(condition = "#event.channel == '8' && #event.tradeType == 'reVerifiCode'") // 金运通实名支付重新获取验证码
	public void jytReVerifiCodeEvent(TradeEvent event) {
		log.info("金运通实名支付重新获取验证码  jytReVerifiCodeEvent: {}", JSONObject.toJSON(event));
		/**
		 * 重新获取验证码需要参数手机号，待支付待支付订单号
		 */
		String mobile = event.getMobile();//手机号
		String order_id = event.getPayNo();//待支付订单号
		String flowId  = order_id;//交易流水号
		CmdTD4003ReVerifiCode cmd = CmdTD4003ReVerifiCode.builder().mobile(mobile).order_id(order_id).build();
		CmdTD4003ReVerifiCodeResp resp = new CmdTD4003ReVerifiCodeResp();
		try {
			resp = jytpayService.reVerifiCode(cmd, flowId);
		} catch (Exception e) {
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("金运通实名支付，重新获取验证码异常");
			e.printStackTrace();
		}
		if ("S0000000".equals(resp.getHead().getResp_code())) {
			if ("01".equals(resp.getBody().getTran_state())) {
				log.info("验证码重新发送成功");
				event.setReturnCode(Constant.SUCCESS);
				if (!StringUtils.isEmpty(resp.getBody().getRemark())) {
					event.setErrorDesc(resp.getBody().getRemark());
				} else {
					event.setErrorDesc("重新获取验证码,订单信息验证成功");
				}
			} else if("02".equals(resp.getBody().getTran_state())){
				log.info("订单不支持重新获取验证码");
				event.setReturnCode(Constant.FAIL);
				if (!StringUtils.isEmpty(resp.getBody().getRemark())) {
					event.setErrorDesc(resp.getBody().getRemark());
				} else {
					event.setErrorDesc("订单不支持重新获取验证码");
				}
			}else{
				log.info("重新获取验证码失败");
				event.setReturnCode(Constant.FAIL);
				if (!StringUtils.isEmpty(resp.getBody().getRemark())) {
					event.setErrorDesc(resp.getBody().getRemark());
				} else {
					event.setErrorDesc("重新获取验证码失败");
				}
			}
		} else {
			log.info("金运通实名支付重新获取验证码失败");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc(resp.getHead().getResp_desc());
		}
	}

		

}
