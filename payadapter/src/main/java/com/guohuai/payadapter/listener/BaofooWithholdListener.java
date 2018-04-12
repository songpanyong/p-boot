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
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.WithoidingRequest;
import com.guohuai.payment.baofu.cmd.WithoidingRequest.WithoidingRequestBuilder;
import com.guohuai.payment.baofu.cmd.WithoidingResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BaofooPayListener
 * @Description: 宝付代扣
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooWithholdListener {

	@Autowired
	private BaoFuService baofuService;
	@Autowired
	CallBackDao callbackDao;

	@Autowired
	BankUtilService bankUtilService;

	@Value("${payadapter_environment}")
	String environment;
	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;

	@EventListener(condition = "#event.channel == '12' && #event.tradeType == '01'")
	public void payEvent(TradeEvent event) {
		if ("test".equals(environment)) {
			log.info("宝付代扣测试 接收  payeeEvent: {}", JSONObject.toJSON(event));
			event.setCardNo("6222020111122220000");
			event.setRealName("张宝");
			event.setPhone("15604330965");
			event.setCustID("320301198502169142");
			event.setCustBankNo("ICBC");
		} else {
			log.info("宝付代扣测试 接收  payeeEvent: {}", JSONObject.toJSON(event));
		}
		String channelBankNo = "";
		String bankname = "";
		try {// 根据卡号和渠道获取该银行卡号的渠道银行代码
			channelBankNo = bankUtilService.getBankNoByBin(event.getCardNo(), event.getChannel());
			bankname = bankUtilService.getNameByCard(event.getCardNo());
		} catch (Exception e) {
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("获取渠道银行代码异常");
			log.error(event.getErrorDesc() + ":exception:{},", e);
			return;
		}
		if (channelBankNo == null) {// 为获取到该银行卡号的渠道银行代码
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("不支持该银行卡");
			return;
		}
		event.setCustBankNo(channelBankNo);
		event.setAccountBankName(bankname);
		/** 单笔消费 **/
		WithoidingRequest wrt = installParameter(event);
		WithoidingResp res = new WithoidingResp();
		try {
			/** 单笔 **/
			res = baofuService.withoiding(wrt);
		} catch (Exception e) {
			// 处理异常当超时处理
			event.setReturnCode(Constant.INPROCESS); // 超时
			event.setErrorDesc("宝付代扣异常");
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(event.getTradeType()).payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank")
					.minute(minute).totalCount(totalCount).totalMinCount(20).countMin(0)
					.status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}
		String status = CallBackEnum.INIT.getCode();
		log.info("宝付代扣返回  res: {}", JSONObject.toJSON(res));
		if (res == null) {
			log.info("交易提交失败，以查询的结果为主");
			event.setReturnCode(Constant.SUCCESS);
			status = CallBackEnum.INIT.getCode();
		} else {
			if ("0000".equals(res.getResp_code()) || "BF00114".equals(res.getResp_code())
					|| "BF00100".equals(res.getResp_code()) || "BF00112".equals(res.getResp_code())
					|| "BF00113".equals(res.getResp_code()) || "BF00115".equals(res.getResp_code())
					|| "BF00144".equals(res.getResp_code()) || "BF00202".equals(res.getResp_code())) {
				event.setReturnCode(Constant.SUCCESS);
				event.setHostFlowNo(res.getTrans_no());
				status = CallBackEnum.INIT.getCode();
			} else {
				log.info("ErrorCode:" + res.getResp_code() + "FailureDetails:" + res.getResp_msg());
				event.setErrorDesc(res.getResp_msg());
				event.setReturnCode(res.getResp_code());
				status = CallBackEnum.INIT.getCode();
			}
		}
		CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo()).tradeType(event.getTradeType())
				.payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank").minute(minute)
				.totalCount(totalCount).totalMinCount(20).countMin(0).status(status).createTime(new Date()).build();
		log.info("保存回调信息 callBackInfo={}", JSONObject.toJSON(callBackInfo));
		callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
		log.info("宝付代扣单笔支付 返回  payEvent: {}", JSONObject.toJSON(event));
	}

	/**
	 * 宝付代扣参数组装
	 * 
	 * @param event
	 * @return
	 */
	public WithoidingRequest installParameter(TradeEvent event) {
		log.info("event:+" + event);
		String trans_id = event.getPayNo();
		String pay_code = event.getCustBankNo();
		String acc_no = event.getCardNo();
		String id = event.getCustID();
		String id_holder = event.getRealName();
		String mobile = event.getPhone();
		String txn_amt = changeY2F(event.getAmount());
		event.setTradeTime(new Date());
		String trade_date = new SimpleDateFormat("yyyyMMddhhmmss").format(event.getTradeTime());
		String trans_serial_no = trans_id;
		WithoidingRequestBuilder req = WithoidingRequest.builder();
		req.pay_code(pay_code).acc_no(acc_no).id_card(id).id_holder(id_holder).mobile(mobile).txn_amt(txn_amt)
				.trade_date(trade_date).trans_id(trans_id).trans_serial_no(trans_serial_no);
		return req.build();

	}

	// public static void main(String[] args) {
	// Timestamp time = new Timestamp(System.currentTimeMillis());
	// String tsStr = "";
	// DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	// try {
	// tsStr = sdf.format(time);
	// System.out.println(tsStr);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

}
