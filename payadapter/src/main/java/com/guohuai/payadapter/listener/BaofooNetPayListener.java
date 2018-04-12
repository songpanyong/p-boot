package com.guohuai.payadapter.listener;

import java.util.Date;

import com.guohuai.payment.baofu.api.BaoFuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.baofu.cmd.NetBankRequest;
import com.guohuai.payment.baofu.cmd.NetBankRequest.NetBankRequestBuilder;
import com.guohuai.payment.baofu.cmd.NetBankResp;

import lombok.extern.slf4j.Slf4j;

/**
 * 宝付网关支付
 * 
 * @author xyl
 *
 */
@Slf4j
@Component
public class BaofooNetPayListener {

	@Autowired
	private BaoFuService baofuService;
	@Autowired
	CallBackDao callbackDao;

	@Autowired
	BankUtilService bankUtilService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;
	
	@EventListener(condition = "#event.channel =='14' && #event.tradeType == '01'")
	public void netPay(TradeEvent event) {
		log.info("宝付网关支付接收 TradeEvent: {}", JSONObject.toJSON(event));

		// 不经过三方直接返回结果
		if ("yes".equals(withOutThirdParty)) {
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(totalCount).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}

		NetBankRequest netPayReq = new NetBankRequest();
		netPayReq = installParameter(event);
		NetBankResp res = new NetBankResp();
		try {
			log.info("网关支付请求报文：{}", JSONObject.toJSON(netPayReq));
			res = baofuService.netBank(netPayReq);
		} catch (Exception e) {
			// 处理异常当超时处理
			log.error("定单号：{}交易异常:exception:{},",event.getOrderNo(), e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("交易异常");
			return;
		}

		event.setRespHTML(res.getFrom());
		event.setReturnCode(Constant.SUCCESS);

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
		String trans_money = StringUtil.getMoneyStr(event.getAmount());
		String to_acc_name = event.getRealName();
		NetBankRequestBuilder req = NetBankRequest.builder().PayID("").TransID(trans_no)
				.TradeDate(DateUtil.format(event.getTradeTime(), "yyyyMMddHHmmss"))
				.ProductName(getEmptyString(event.getProdInfo())).OrderMoney(trans_money).Username(to_acc_name);
		return req.build();

	}

	private String getEmptyString(String str) {
		if (StringUtil.isEmpty(str)) {
			str = "";
		}
		return str;
	}


}
