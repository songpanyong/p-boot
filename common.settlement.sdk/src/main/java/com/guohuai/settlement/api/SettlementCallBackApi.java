package com.guohuai.settlement.api;

import java.util.List;

import com.guohuai.settlement.api.request.DepositBankOrderSyncReq;
import com.guohuai.settlement.api.request.InteractiveRequest;
import com.guohuai.settlement.api.request.WithdrawBankOrderSyncReq;
import com.guohuai.settlement.api.response.BaseResponse;
import com.guohuai.settlement.api.response.OrderResponse;
import com.guohuai.settlement.api.response.UserAccountInfoResponse;

public interface SettlementCallBackApi {
	/**
	 * 交易回调
	 * 
	 * @param orderResponse
	 * @return
	 */
	public boolean tradeCallback(OrderResponse orderResponse);

	
	/**
	 * 修改订单状态
	 */
	public boolean changeOrderStatus(InteractiveRequest req);
	
	/**
	 * 提现审核获取用户可用余额及用户创建时间
	 * @param memberId
	 * @return
	 */
	public List<UserAccountInfoResponse> getUserAccountInfo(String [] memberId);
	
	/**
	 * 通知业务系统同步余额
	 * @param req
	 * @return
	 */
	public boolean notifyChangeAccountBalance(InteractiveRequest req);
	
	/**
	 * 充值登记
	 */
	public BaseResponse depositBankOrder(DepositBankOrderSyncReq req);
	
	/**
	 * 提现登记
	 */
	public BaseResponse withdrawBankOrder(WithdrawBankOrderSyncReq req);
	
}
