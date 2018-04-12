package com.guohuai.payadapter.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.payadapter.bankutil.BankUtilDao;
import com.guohuai.payadapter.bankutil.BankUtilEntity;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.CompanyWithoidingRequest;
import com.guohuai.payment.baofu.cmd.CompanyWithoidingRequest.CompanyWithoidingRequestBuilder;
import com.guohuai.payment.baofu.cmd.CompanyWithoidingResp;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BaofooPayListener
 * @Description: 宝付对公代扣查询
 *
 */
@Slf4j
@Component
public class BaofooPlatformWithholdListener {

	@Autowired
	private BaoFuService baofuService;
	@Autowired
	CallBackDao callbackDao;

	@Autowired
	BankUtilService bankUtilService;

	@Autowired
	BankUtilDao bankUtilDao;

	@Value("${payadapter_environment}")
	String environment;
	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;

	@EventListener(condition = "#event.channel == '13' && #event.tradeType == '01'")
	public void payEvent(TradeEvent event) {
		if ("test".equals(environment)) {
			log.info("宝付对公代扣测试 接收  payeeEvent: {}", JSONObject.toJSON(event));
			event.setCardNo("6222020111122220000");
			event.setPlatformName("张宝有限公司");// 户名
			event.setAccountProvince("上海市");// 省份
			event.setAccountCity("上海市");// 市
			event.setAccountDept("张江支行");// 支行名称
			event.setBankCode("ICBC");
		} else {
			log.info("宝付对公代扣 接收  payeeEvent: {}", JSONObject.toJSON(event));
		}
		String pay_code = "";
		try {
			BankUtilEntity bank = bankUtilService.getBankByCard(event.getCardNo());
			pay_code = bankUtilDao.getBankNoByBin(bank.getBankBin(), event.getChannel());
		} catch (Exception e) {
			e.printStackTrace();
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("宝付对公代扣,获取银行编码异常");
			log.error("宝付对公代扣,获取银行编码异常");
			return;
		}
		if (StringUtils.isEmpty(pay_code)) {// 未获取到该银行卡号的渠道银行代码
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("不支持该银行卡");
			return;
		}
		event.setBankCode(pay_code);
		/** 单笔消费 **/
		CompanyWithoidingRequest wrt = installParameter(event);
		CompanyWithoidingResp res = new CompanyWithoidingResp();
		try {
			/** 单笔 **/
			res = baofuService.companyWithoiding(wrt);
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
		log.info("宝付对公代扣返回  res: {}", JSONObject.toJSON(res));
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
		log.info("宝付对公代扣单笔支付 返回  payEvent: {}", JSONObject.toJSON(event));
	}

	/**
	 * 宝付代扣参数组装
	 * 
	 * @param event
	 * @return
	 */
	public CompanyWithoidingRequest installParameter(TradeEvent event) {
		log.info("event:+" + event);
		String trans_id = event.getPayNo();// 商户订单号
		String pay_code = event.getBankCode();// 银行编码
		String acc_name = event.getPlatformName();// 对公账户名称
		String acc_no = event.getCardNo();// 对公账户帐号
		String province = event.getAccountProvince();// 开户行所诉省份
		String city = event.getAccountCity();// 开户行所属城市
		String branch = event.getAccountDept();// 开户行所属支行
		String certificate_type = "00";// 企业证件类型;00-09
		String certificate_no = "2134567913104";// 证件号码
		String txn_amt = changeY2F(event.getAmount());// 交易金额
		event.setTradeTime(new Date());
		String trade_date = new SimpleDateFormat("yyyyMMddhhmmss").format(event.getTradeTime());// 订单日期
		String trans_serial_no = trans_id;
		CompanyWithoidingRequestBuilder req = CompanyWithoidingRequest.builder();
		req.pay_code(pay_code).acc_no(acc_no).certificate_type(certificate_type).certificate_no(certificate_no)
				.acc_name(acc_name).acc_no(acc_no).province(province).city(city).branch(branch).txn_amt(txn_amt)
				.trade_date(trade_date).trans_id(trans_id).trans_serial_no(trans_serial_no);
		return req.build();
	}

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
