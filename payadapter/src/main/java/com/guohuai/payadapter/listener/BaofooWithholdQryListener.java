package com.guohuai.payadapter.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.WithoidingQueryRequest;
import com.guohuai.payment.baofu.cmd.WithoidingQueryRequest.WithoidingQueryRequestBuilder;
import com.guohuai.payment.baofu.cmd.WithoidingQueryResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BaofooQryListener
 * @Description: 宝付代扣查询
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooWithholdQryListener {

	@Autowired
	private BaoFuService baofuService;
	@Autowired
	CallBackDao callbackDao;

	@Autowired
	BankUtilService bankUtilService;

	@Value("${payadapter_environment}")
	String environment;

	// private final int minute = 1;// 回调间隔时间;
	// private final int totalCount = 20;// 最大回调次数;
	
	@EventListener(condition = "#event.channel == '12'")
	public void payEvent(TradeRecordEvent event) {
		log.info("宝付代扣订单查询  payEvent: {}", JSONObject.toJSON(event));
		String trans_no = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());

		String orig_trans_id = event.getOrderNo();
		String trade_date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		WithoidingQueryRequestBuilder request = WithoidingQueryRequest.builder().trans_serial_no(trans_no)
				.orig_trans_id(orig_trans_id).orig_trade_date(trade_date);
		WithoidingQueryRequest req = request.build();
		WithoidingQueryResp res = new WithoidingQueryResp();
		try {
			res = baofuService.withoidingQuery(req);
			log.info("接口返回参数：" + res);
			event.setReturnCode(res.getResp_code());
			event.setErrorDesc(res.getResp_msg());
			if ("S".equals(res.getOrder_stat())) {
				event.setStatus(CallBackEnum.SUCCESS.getCode());
			}else if("I".equals(res.getOrder_stat())) {
				event.setStatus(CallBackEnum.PROCESSING.getCode());
				event.setReturnCode(Constant.INPROCESS);
			}else{
				event.setStatus(CallBackEnum.FAIL.getCode());
			}
		} catch (Exception e) {
			event.setErrorDesc("查询代扣定单异常");
			event.setStatus(CallBackEnum.PROCESSING.getCode());
			e.printStackTrace();
			event.setReturnCode(Constant.INPROCESS);
		}
	}

}
