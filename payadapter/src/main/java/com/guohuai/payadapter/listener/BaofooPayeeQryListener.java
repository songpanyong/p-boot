package com.guohuai.payadapter.listener;

import com.guohuai.payment.baofu.cmd.WithdrawQueryResp;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.WithdrawQueryDates;
import com.guohuai.payment.baofu.cmd.WithdrawQueryDates.WithdrawQueryDatesBuilder;

/**
 * @ClassName: BaofooAgentQryListener
 * @Description: 宝付代付查询
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooPayeeQryListener {

	@Autowired
	private BaoFuService baofuService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	@EventListener(condition = "#event.channel == '11'")
	public void payeeEvent(TradeRecordEvent event) {
		log.info("宝付代付查询订单  payeeEvent: {}", JSONObject.toJSON(event));
		String trans_no = event.getOrderNo();
		WithdrawQueryDatesBuilder request = WithdrawQueryDates.builder().trans_no(trans_no).trans_batchid("");
		WithdrawQueryDates req = request.build();
		WithdrawQueryResp res = new WithdrawQueryResp();
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			event.setErrorDesc("交易成功");
			return;
		}
		
		try {
			res = baofuService.withdrawQuery(req);
		} catch (Exception e) {
			log.error( "宝付代付查询订单异常:exception:{}", e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			e.printStackTrace();
		}
		log.info("接口返回参数：" + res);
		if(null !=res){
			event.setErrorDesc(res.getReturnMsg());
			if ("0000".equals(res.getReturnCode()) || "200".equals(res.getReturnCode())
					|| "0300".equals(res.getReturnCode())) {
				// 交易失败(-1)和交易成功(1)改变状态
				if ("1".equals(res.getState())) {
					event.setReturnCode(Constant.SUCCESS);
					event.setStatus(CallBackEnum.SUCCESS.getCode());
				} else if ("-1".equals(res.getState())) {
					event.setReturnCode(Constant.FAIL);
					event.setStatus(CallBackEnum.FAIL.getCode());
				} else {
					event.setReturnCode(Constant.INPROCESS);
					event.setStatus(CallBackEnum.PROCESSING.getCode());
				}
			}else if("0999".equals(res.getReturnCode())){
				event.setReturnCode(Constant.INPROCESS);
				event.setStatus(CallBackEnum.PROCESSING.getCode());
			} else {
				event.setReturnCode(res.getReturnCode());
				event.setErrorDesc(res.getReturnMsg());
			}
		}else{
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("代付交易返回参数为空");
			event.setStatus(CallBackEnum.FAIL.getCode());
		}
		log.info("宝付代付查询返回结果,returnCode:{},returnMsg:{}", event.getReturnCode(), event.getErrorDesc());
	}

}
