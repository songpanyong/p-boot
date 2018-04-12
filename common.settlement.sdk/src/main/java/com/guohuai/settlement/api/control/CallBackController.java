package com.guohuai.settlement.api.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementCallBackApi;
import com.guohuai.settlement.api.request.DepositBankOrderSyncReq;
import com.guohuai.settlement.api.request.InteractiveRequest;
import com.guohuai.settlement.api.request.WithdrawBankOrderSyncReq;
import com.guohuai.settlement.api.response.BaseResponse;
import com.guohuai.settlement.api.response.OrderResponse;
import com.guohuai.settlement.api.response.UserAccountInfoResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: CallBackController
 * @Description: 申购 赎回交易回调
 * @author xueyunlong
 * @date 2016年11月24日 下午6:31:28
 *
 */
@RestController
@RequestMapping(value = "/settlement/callback", method = RequestMethod.POST)
@Slf4j
public class CallBackController {
	@Autowired(required = false)
	SettlementCallBackApi settlementCallBackApi;

	@RequestMapping(value = "/trade", method = RequestMethod.POST, produces ="application/json")
	public boolean callback(@RequestBody OrderResponse resp) {
		log.info("回调信息：{}", JSONObject.toJSON(resp));
		boolean result = false;
		try {
			result = settlementCallBackApi.tradeCallback(resp);
		} catch (Exception e) {
			log.error("回调处理异常{}", e);
		}
		log.info("回调定单：{}，返回结果{}", resp.getOrderNo(), result);
		return result;
	}
	
	@RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST, produces ="application/json")
	public boolean changeStatus(@RequestBody InteractiveRequest req) {
		log.info("修改订单状态：{}", req);
		boolean result = false;
		try {
			result = settlementCallBackApi.changeOrderStatus(req);
		} catch (Exception e) {
			log.error("修改订单状态{}", e);
		}
		log.info("修改订单状态：{}，返回结果{}", req.getOrderNo(), result);
		return result;
	}
	
	@RequestMapping(value = "/getUserAccountInfo", method = RequestMethod.POST, produces ="application/json")
	public List<UserAccountInfoResponse> getUserAccountInfo(@RequestBody String [] memberId) {
		log.info("提现审核获取用户可用余额及用户创建时间");
		List<UserAccountInfoResponse> userAccountList = null;
		try {
			userAccountList= settlementCallBackApi.getUserAccountInfo(memberId);
		} catch (Exception e) {
			log.error("提现审核获取用户可用余额及用户创建时间{}", e);
		}
		return userAccountList;
	}
	
	@RequestMapping(value = "/notifyChangeAccountBalance", method = RequestMethod.POST, produces ="application/json")
	public boolean notifyChangeAccountBalance(@RequestBody InteractiveRequest req) {	
		log.info("通知业务系统同步账户余额：{}", req);
		boolean result = false;
		try {
			result = settlementCallBackApi.notifyChangeAccountBalance(req);
		} catch (Exception e) {
			log.error("通知业务系统同步账户余额异常{}", e);
		}
		log.info("通知业务系统同步用户账户余额：{}，返回结果{}", req.getUserOid(), result);
		return result;
	}
	
	@RequestMapping(value = "/depositbankorder", method = RequestMethod.POST, produces ="application/json")
	public BaseResponse depositBankOrder(@RequestBody DepositBankOrderSyncReq req) {	
		log.info("通知业务系统充值订单：{}", req);
		BaseResponse rep = new BaseResponse();
		
		try {
			rep = settlementCallBackApi.depositBankOrder(req);
		} catch (Exception e) {
			log.error("通知业务系统充值订单{}", e);
		}
		return rep;
	}
	
	
	@RequestMapping(value = "/withdrawbankorder", method = RequestMethod.POST, produces ="application/json")
	public BaseResponse withdrawBankOrder(@RequestBody WithdrawBankOrderSyncReq req) {	
		log.info("通知业务系统同步账户余额：{}", req);
		BaseResponse rep = new BaseResponse();
		
		try {
			rep = settlementCallBackApi.withdrawBankOrder(req);
		} catch (Exception e) {
			log.error("通知业务系统同步账户余额异常{}", e);
		}
		return rep;
	}
}
