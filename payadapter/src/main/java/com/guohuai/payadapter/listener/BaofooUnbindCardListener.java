package com.guohuai.payadapter.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.DateTimeUtils;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.AuthenticationEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.UnbindCardRequest;
import com.guohuai.payment.baofu.cmd.UnbindCardResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 宝付解除绑定关系
 * 
 * @author hans
 *
 */
@Slf4j
@Component
public class BaofooUnbindCardListener {

	@Autowired
	BaoFuService baofuService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	@EventListener(condition = "#event.tradeType == 'baofooubindCard'")
	public void ubundCardEvent(AuthenticationEvent event) {
		log.info("宝付解绑: {}", JSONObject.toJSON(event));
		String trans_serial_no = event.getOrderId();
		String bind_id = event.getBindId();
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
		UnbindCardRequest request = UnbindCardRequest.builder().trans_serial_no(trans_serial_no).bind_id(bind_id)
				.trade_date(trade_date).build();
		UnbindCardResp resp = new UnbindCardResp();
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			event.setErrorDesc("解绑成功");
			return;
		}
		
		try {
			resp = baofuService.unbindCard(request);
			if ("0000".equals(resp.getResp_code()) || "BF00114".equals(resp.getResp_code())) {
				log.info("宝付解绑成功");
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("解绑成功");
			} else {
				log.info("宝付解绑失败: {}", resp.getResp_msg());
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("解绑失败");
			}
		} catch (Exception e) {
			log.error( "宝付解绑异常:exception:{}", e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("解绑异常");
			e.printStackTrace();
		}
	}

}
