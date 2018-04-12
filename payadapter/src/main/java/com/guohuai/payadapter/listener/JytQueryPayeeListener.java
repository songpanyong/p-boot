package com.guohuai.payadapter.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTD2001PayeeQuery;
import com.guohuai.common.payment.jytpay.cmd.CmdTD2001PayeeQueryResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通单笔收款交易查询
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class JytQueryPayeeListener {

	@Autowired
	JytpayService jytpayService;
	
	@Autowired
	JytFlowIdUtils flowUtils;

	@EventListener(condition = "#event.channel == '8'") // 金运通实名支付查询
	public void payeeQueryEvent(TradeRecordEvent event) {
		log.info("金运通实名支付查询  payeeQueryEvent: {}", JSONObject.toJSON(event));
		/**
		 * 实名支付查的是订单号
		 */
		String orderId = event.getOrderNo();
		CmdTD2001PayeeQuery cmd = CmdTD2001PayeeQuery.builder().order_id(orderId).build();
		CmdTD2001PayeeQueryResp resp = new CmdTD2001PayeeQueryResp();
		try {
			resp = jytpayService.payeeQuery(cmd, flowUtils.getOneFlowId());//查询流水号
		} catch (Exception e) {
			log.error("金运通实名支付，查询订单异常");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			e.printStackTrace();
		}
		if ("S0000000".equals(resp.getHead().getResp_code())) {
			if ("00".equals(resp.getBody().getTran_state())) {
				log.info("金运通实名支付查询，交易成功");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
				event.setStatus(CallBackEnum.SUCCESS.getCode());
			} else if ("03".equals(resp.getBody().getTran_state())) {
				log.info("金运通实名支付查询，交易失败,{}",resp.getBody().getRemark());
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("交易失败");
				event.setStatus(CallBackEnum.FAIL.getCode());
			} else {
				log.info("金运通实名支付查询，交易处理中");
				event.setReturnCode(Constant.INPROCESS);
				event.setErrorDesc("交易处理中");
				event.setStatus(CallBackEnum.PROCESSING.getCode());
			}
		} else {
			log.info("金运通实名支付查询订单失败");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			event.setStatus(CallBackEnum.FAIL.getCode());
		}
	}
}
