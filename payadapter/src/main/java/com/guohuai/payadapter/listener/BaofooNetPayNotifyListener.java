package com.guohuai.payadapter.listener;

import com.guohuai.payment.baofu.api.BaoFuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payment.baofu.cmd.NetBankQueryResp;
import com.guohuai.payment.baofu.cmd.NetBankRequest;
import com.guohuai.payment.baofu.cmd.NetBankRequest.NetBankRequestBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 宝付网关支付通知
 * 
 * @author xyl
 *
 */
@Slf4j
@Component
public class BaofooNetPayNotifyListener {

	@Autowired
	private BaoFuService baofuService;
	@Autowired
	CallBackDao callbackDao;

	@Autowired
	BankUtilService bankUtilService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	
	@EventListener(condition = "#event.channel =='15' && #event.tradeType == '01'")
	public void netPay(TradeEvent event) {
		log.info("宝付网关支付接收 TradeEvent: {}", JSONObject.toJSON(event));

		// 不经过三方直接返回结果
		if ("yes".equals(withOutThirdParty)) {
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			return;
		}

		NetBankRequest netPayReq = new NetBankRequest();
		netPayReq = installParameter(event);
		NetBankQueryResp res = new NetBankQueryResp();
		try {
			log.info("网关支付请求报文：{}", JSONObject.toJSON(netPayReq));
			res = baofuService.netBankNotify(netPayReq);
		} catch (Exception e) {
			// 处理异常当超时处理
			log.error("定单号：{}交易异常:exception:{},",event.getOrderNo(), e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("交易异常");
			return;
		}
		if(Constant.SUCCESS.equals(res.getCheckResult())){
			event.setReturnCode(Constant.SUCCESS);
		}else{
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc(res.getCheckResult());
		}
		

	}

	/**
	 * 宝付代付参数组装
	 * 
	 * @param event
	 * @return
	 */
	public NetBankRequest installParameter(TradeEvent event) {
		log.info("event:+" + event);
		String trans_no = event.getPayNo();
		String trans_money = event.getAmount();
		String result = event.getReturnCode();
		String resultDesc = event.getErrorDesc();
		String additionalInfo = event.getEmergencyMark();
		String succTime = event.getSuccTime();
		NetBankRequestBuilder req = NetBankRequest.builder().TransID(trans_no).Result(result).ResultDesc(resultDesc)
				.AdditionalInfo(additionalInfo).Signature(event.getSignature()).succTime(succTime)
				.OrderMoney(trans_money);
		return req.build();

	}
}
