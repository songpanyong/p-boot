package com.guohuai.payadapter.listener;

import com.guohuai.payment.baofu.api.BaoFuService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.bankutil.BankUtilDao;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.baofu.cmd.BindCardRequest;
import com.guohuai.payment.baofu.cmd.BindCardResp;

/**
 * @Description: 宝付绑卡请求
 *
 */
@Slf4j
@Component
public class BaofooBindCardListener {

	@Autowired
	BankUtilDao bankUtilDao;
	
	@Autowired
	BankUtilService bankUtilService;

	@Autowired
	BaoFuService baofuService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	@EventListener(condition = "#event.tradeType == 'baofoobindCard'")
	public void authenticationEvent(AuthenticationEvent event) {
		log.info("An event occured …… 宝付直接绑卡交易: {}", JSONObject.toJSONString(event));
		String trans_serial_no = event.getOrderId();// 商户流水号
		String trans_id = event.getOrderId();// 商户订单号
		String acc_no = event.getCardNo();// 银行卡号
		String id_card = event.getIdentityNo();// 身份证ID
		String id_holder = event.getUserName();// 持卡人姓名
		String mobile = event.getMobileNum();// 手机号
		String pay_code = event.getBankCode();// 银行编号
		
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);

		BindCardResp resp = new BindCardResp();
		BindCardRequest req = BindCardRequest.builder().trans_serial_no(trans_serial_no).trans_id(trans_id)
				.acc_no(acc_no).id_card(id_card).id_holder(id_holder).mobile(mobile).pay_code(pay_code)
				.trade_date(trade_date).build();
		
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
			resp = baofuService.bindCard(req);
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
			event.setErrorDesc("绑卡异常");
			log.error("宝付直接绑卡异常:exception:{}", e);
		}
		log.info("AuthenticationEvent,{}", event.getErrorDesc());

	}
}