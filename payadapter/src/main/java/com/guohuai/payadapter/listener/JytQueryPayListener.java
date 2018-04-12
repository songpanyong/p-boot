package com.guohuai.payadapter.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTC2002PayOneQuery;
import com.guohuai.common.payment.jytpay.cmd.CmdTC2002PayOneQueryResp;
import com.guohuai.common.payment.jytpay.utils.JytFlowIdUtils;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通单笔代付款交易查询
 * @author hans
 *
 */
@Slf4j
@Component
public class JytQueryPayListener {
	
	@Autowired
	JytpayService jytpayService;
	
	@Autowired
	JytFlowIdUtils flowUtils;
	
	@EventListener(condition = "#event.channel == '7'") // 金运通代收款支付查询
	public void payQueryEvent(TradeRecordEvent event) {
		log.info("金运通代收款支付查询  payQueryEvent: {}", JSONObject.toJSON(event));
		/**
		 * 代付款是查原流水号
		 */
		String orderId = event.getOrderNo();
		CmdTC2002PayOneQuery cmd = CmdTC2002PayOneQuery.builder().ori_tran_flowid(orderId).build();
		CmdTC2002PayOneQueryResp resp = new CmdTC2002PayOneQueryResp();
		try {
			resp = jytpayService.payOneQuery(cmd, flowUtils.getOneFlowId());//查询流水号
		} catch (Exception e) {
			log.error("金运通代收查询异常");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			e.printStackTrace();
		}
		if ("S0000000".equals(resp.getHead().getResp_code())) {
			if ("01".equals(resp.getBody().getTran_state())) {
				log.info("金运通代收款查询，订单交易成功");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
				event.setStatus(CallBackEnum.SUCCESS.getCode());
			} else if ("03".equals(resp.getBody().getTran_state())) {
				log.info("金运通代收款查询，订单交易失败,{}",resp.getBody().getRemark());
				event.setReturnCode(resp.getHead().getResp_code());
				event.setErrorDesc("交易失败");
				event.setStatus(CallBackEnum.FAIL.getCode());
			} else {
				log.info("金运通代收款查询，订单交易处理中");
				event.setReturnCode(Constant.INPROCESS);
				event.setErrorDesc("交易处理中");
				event.setStatus(CallBackEnum.PROCESSING.getCode());
			}
		} else {
			log.info("金运通代收款查询订单失败");
			event.setReturnCode(resp.getHead().getResp_code());
			event.setErrorDesc("系统异常");
			event.setStatus(CallBackEnum.FAIL.getCode());
		}
	}

}
