package com.guohuai.payadapter.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.common.payment.jytpay.api.JytpayService;
import com.guohuai.common.payment.jytpay.cmd.CmdTC1002PayOne;
import com.guohuai.common.payment.jytpay.cmd.CmdTC1002PayOneResp;
import com.guohuai.payadapter.bankutil.StringUtil;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.component.TradeChannel;
import com.guohuai.payadapter.component.TradeType;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payadapter.redeem.CallBackDao;
import com.guohuai.payadapter.redeem.CallBackInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 金运通资金单笔代付-付款
 * 
 * @author hans
 *
 */
@Slf4j
@Component
@Transactional
public class JytPayListener {

	@Autowired
	private JytpayService jytpayService;

	@Autowired
	CallBackDao callbackDao;

	@Value("${jyt.bsnCode:09000}")
	String bsnCode;//代收业务代码
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;

	private final int minute = 1;// 回调间隔时间;
	private final int totalCount = 20;// 最大回调次数;

	@EventListener(condition = "#event.channel == '7' && #event.tradeType == '02'")
	@Transactional
	public void jytPayEvent(TradeEvent event) {

		log.info("An event occured: {}", event);
		String flowId = event.getPayNo(); // payNo交易流水号,orderNo交易订单号
		
		String bankName = event.getBankName();
		String accountNo = event.getCardNo();
		String accountName = event.getRealName();
		String accountType = event.getAccountType();
		accountType = "00";//银行账户类别00对私,01对公
		String currency = "CNY";//人民币
		String tranAmt = StringUtil.getMoneyStr(event.getAmount());
//		bsnCode = "00600";//业务代码

		CmdTC1002PayOne cmd = CmdTC1002PayOne.builder().bank_name(bankName).account_no(accountNo)
				.account_name(accountName).account_type(accountType).tran_amt(tranAmt).currency(currency)
				.bsn_code(bsnCode).build();
		CmdTC1002PayOneResp resp = new CmdTC1002PayOneResp();
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			event.setErrorDesc("交易成功");
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo()).tradeType(event.getTradeType())
					.payNo(event.getPayNo()).channelNo(event.getChannel()).type("bank").minute(minute)
					.totalCount(totalCount).totalMinCount(20).countMin(0).status(CallBackEnum.INIT.getCode()).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,包括上送的凭证号和返回的银行流水号
			return;
		}
		
		try{
			resp = this.jytpayService.payOne(cmd, flowId);
		}catch(Exception e){
			log.error("金运通资金代付,请求交易异常,{}", e);
			event.setReturnCode(Constant.INPROCESS); // 异常当交易超时处理
			event.setErrorDesc("交易异常");
			
			//交易流水号orderId,支付订单号payNo,代付中没有订单号
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(TradeType.payee.getValue()).payNo(event.getPayNo())
					.channelNo(TradeChannel.jinyuntongpay.getValue()).type("bank")
					.status(CallBackEnum.INIT.getCode()).minute(minute).totalCount(totalCount).totalMinCount(20)
					.countMin(0).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,上送的交易流水号和返回的流水号一致
			return;
		}
		log.info("金运通资金代付,返回信息{}", JSONObject.toJSONString(resp));
		if ("S0000000".equals(resp.getHead().getResp_code())) {
			if ("01".equals(resp.getBody().getTran_state())) {
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc("交易成功");
			} else if ("03".equals(resp.getBody().getTran_state())) {
				log.info("金运通代付款,交易失败,{}",resp.getBody().getRemark());
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc("交易失败");
			}else{
				log.info("金运通代付款,交易处理中");
				event.setReturnCode(Constant.INPROCESS);
				event.setErrorDesc("交易处理中");
				CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
						.tradeType(TradeType.payee.getValue()).payNo(event.getPayNo())
						.channelNo(TradeChannel.jinyuntongpay.getValue()).type("bank")
						.status(CallBackEnum.INIT.getCode()).minute(minute).totalCount(totalCount).totalMinCount(20)
						.countMin(0).createTime(new Date()).build();
				callbackDao.save(callBackInfo);// 保存交易信息,上送的交易流水号和返回的流水号一致
			} 
		}else if("E0000000".equals(resp.getHead().getResp_code())){
			log.info("金运通代付款,交易处理中");
			event.setReturnCode(Constant.INPROCESS);
			event.setErrorDesc("交易处理中");
			CallBackInfo callBackInfo = CallBackInfo.builder().orderNO(event.getOrderNo())
					.tradeType(TradeType.payee.getValue()).payNo(event.getPayNo())
					.channelNo(TradeChannel.jinyuntongpay.getValue()).type("bank")
					.status(CallBackEnum.INIT.getCode()).minute(minute).totalCount(totalCount).totalMinCount(20)
					.countMin(0).createTime(new Date()).build();
			callbackDao.save(callBackInfo);// 保存交易信息,上送的交易流水号和返回的流水号一致
		}  else {
			log.info("金运通代付款,交易失败");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("交易失败");
		}
	}

}
