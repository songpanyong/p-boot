package com.guohuai.payadapter.listener;

import java.util.Date;

import com.guohuai.payment.baofu.api.BaoFuService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.baofu.cmd.WithdrawReqDates;
import com.guohuai.payment.baofu.cmd.WithdrawReqDates.WithdrawReqDatesBuilder;
import com.guohuai.payment.baofu.cmd.WithdrawResp;

/**
 * @ClassName: BaofooPayeeListener
 * @Description: 宝付代付
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooPayeeListener {

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

	@EventListener(condition = "#event.channel == '11' && #event.tradeType == '02'")
	public void payeeEvent(TradeEvent event) {
		log.info("宝付代付 接收  payeeEvent: {}", JSONObject.toJSON(event));
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo()).tradeType(event.getTradeType())
					.payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank").minute(minute)
					.totalCount(totalCount).totalMinCount(20).countMin(0).status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}
		
		// trans_no|trans_money|to_acc_name|to_acc_no
		String bankname = "";
		try {// 根据卡号获取银行名称
			bankname = bankUtilService.getNameByCard(event.getCardNo());
		} catch (Exception e) {
			log.error( "获取渠道银行代码异常:exception:{}", e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("交易异常");
			return;
		}
		event.setAccountBankName(bankname);
		/** 单笔消费 **/
		WithdrawReqDates wrt = installParameter(event);
		WithdrawResp res = new WithdrawResp();
		try {
			res = baofuService.withdraw(wrt);
		} catch (Exception e) {
			// 处理异常当超时处理
			log.error( "交易异常:exception:{},", e);
			event.setReturnCode(Constant.INPROCESS); // 超时
			event.setErrorDesc("交易异常");
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}
		log.info("宝付代付返回  res: {}", JSONObject.toJSON(res));
		if (res == null) {
			log.info("交易提交超时");
			event.setReturnCode(Constant.INPROCESS); // 交易提交失败;
			event.setErrorDesc("交易处理中");
		} else {
			if ("0000".equals(res.getReturnCode()) || "200".equals(res.getReturnCode())
					|| "0300".equals(res.getReturnCode()) || "0999".equals(res.getReturnCode())) {
				event.setReturnCode(Constant.SUCCESS);
				event.setHostFlowNo(res.getTrans_orderid());
			} else {
				event.setReturnCode(Constant.SUCCESS);
			}
		}
		CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo()).tradeType(event.getTradeType())
				.payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank").minute(minute)
				.totalCount(totalCount).totalMinCount(20).countMin(0).status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
		callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
		log.info("宝付代付单笔支付 返回  payEvent: {}", JSONObject.toJSON(event));
	}

	/**
	 * 宝付代付参数组装
	 */
	public WithdrawReqDates installParameter(TradeEvent event) {
		log.info("event:+" + event);
		String trans_no = event.getPayNo();
		// DecimalFormat df = new DecimalFormat("0.00");
		// String trans_money = df.format(Double.valueOf(event.getAmount()));
		// 处理金额
		String trans_money = StringUtil.getMoneyStr(event.getAmount());
		String to_acc_name = event.getRealName();
		String to_acc_no = event.getCardNo();
		String to_bank_name = event.getAccountBankName();
		// 对私可不填写省、市、支行；对公必须填写
		String to_pro_name = getEmptyString(event.getInAcctProvinceCode());
		String to_city_name = getEmptyString(event.getInAcctCityName());
		String to_acc_dept = getEmptyString(event.getInAcctDept());
		String trans_card_id =  getEmptyString(event.getCustID());
		String trans_mobile = getEmptyString(event.getMobile());
		String trans_summary = getEmptyString(event.getOrderDesc());

		WithdrawReqDatesBuilder request = WithdrawReqDates.builder().trans_no(trans_no).trans_money(trans_money)
				.to_acc_name(to_acc_name).to_acc_no(to_acc_no).to_bank_name(to_bank_name).to_pro_name(to_pro_name)
				.to_city_name(to_city_name).to_acc_dept(to_acc_dept).trans_summary(trans_summary).trans_card_id(trans_card_id).trans_mobile(trans_mobile);
		WithdrawReqDates req = request.build();
		return req;

	}

	private String getEmptyString(String str) {
		if (StringUtil.isEmpty(str)) {
			str = "";
		}
		return str;
	}

}
