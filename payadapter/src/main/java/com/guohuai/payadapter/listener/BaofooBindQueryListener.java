package com.guohuai.payadapter.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.BindQueryRequest;
import com.guohuai.payment.baofu.cmd.BindQueryResp;

/**
 * @Description: 宝付绑卡查询
 *
 */
@Slf4j
@Component
public class BaofooBindQueryListener {

	@Autowired
	BaoFuService baofuService;

	@Autowired
	BankUtilService bankUtilService;

	@EventListener(condition = "#event.tradeType == 'baofoobindquery'")
	public void authenticationEvent(AuthenticationEvent event) {
		log.info("An event occured …… 宝付绑卡查询: {}", JSONObject.toJSONString(event));
		String trans_serial_no = event.getOrderId();// 商户流水号
		String acc_no = event.getCardNo();// 银行卡号
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);

		BindQueryResp resp = new BindQueryResp();
		BindQueryRequest req = BindQueryRequest.builder().trans_serial_no(trans_serial_no).acc_no(acc_no).trade_date(trade_date).build();
		try {
			resp = baofuService.bindQuery(req);
			if ("0000".equals(resp.getResp_code())) {
				event.setBindId(resp.getBind_id());//返回绑定标识号
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc(resp.getResp_msg());
			} else {
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc(resp.getResp_msg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("宝付查询绑卡异常");
			log.error("宝付查询绑卡异常:"+event.getErrorDesc(), e);
		}
		log.info("AuthenticationEvent,{}", event.getErrorDesc());

	}
}