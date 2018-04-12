package com.guohuai.settlement.api;
import com.guohuai.account.api.response.CardQuotaQueryResponse;
import com.guohuai.settlement.api.request.*;
import com.guohuai.settlement.api.response.*;
import feign.Headers;
import feign.Param.Expander;
import feign.RequestLine;

import java.lang.reflect.Array;
import java.util.List;

public interface SettlementApi {

	/**
	 * 四要素验证
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/valid")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse elementValid(ElementValidationRequest  elementValidationRequest);


	/**
	 * 四要素解绑
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/unlock")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse unlock(ElementValidationRequest  elementValidationRequest);
	/**
	 * 四要素查询
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/CheckBank")
	@Headers("Content-Type: application/json")
	public Boolean checkBank(ElementValidationRequest  elementValidationRequest);
	/**
	 * 绑卡申请
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/bindApply")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse bindApply(ElementValidationRequest  elementValidationRequest);
	
	/**
	 * 绑卡确认
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/bindConfrim")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse bindConfirm(ElementValidationRequest  elementValidationRequest);

	/**
	 * 绑卡确认（不发短信）
	 */
	@RequestLine("POST /settlement/element/bindConfirmWithoutSms")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse bindConfirmWithoutSms(ElementValidationRequest  elementValidationRequest);


	/**
	 * 宝付静默绑卡
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/bindBaofoo")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse bindBaofoo(ElementValidationRequest  elementValidationRequest);


	/**
	 * 企业绑卡
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/enterprise/bindcard")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse bindCard(ElementValidationRequest  elementValidationRequest);

	

	/**
	 * 申请签约代扣协议
	 * @param authenticationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/authentication/apply")
	@Headers("Content-Type: application/json")
	public AuthenticationResponse applyAgreement(AuthenticationRequest  authenticationRequest);
	
	/**
	 * 确认签约代扣协议
	 * @param authenticationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/authentication/confirm")
	@Headers("Content-Type: application/json")
	public AuthenticationResponse confirmAgreement(AuthenticationRequest  authenticationRequest);

	
	/**
	 * 支付
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/pay")
	@Headers("Content-Type: application/json")
	public OrderResponse pay(OrderRequest  orderRequest);
	
	/**
	 * 验证码（验证支付）
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/validPay")
	@Headers("Content-Type: application/json")
	public OrderResponse validPay(OrderRequest  orderRequest);
	
	/**
	 * 提现申请
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/applyWithdrawal")
	@Headers("Content-Type: application/json")
	public OrderResponse applyWithdrawal(OrderRequest  orderRequest);
	
	/**
	 * 提现确认
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/confirmWthdrawal")
	@Headers("Content-Type: application/json")
	public OrderResponse confirmWthdrawal(OrderRequest  orderRequest);
	
	/**
	 * 提现解冻
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/unforzenUserWithdrawal")
	@Headers("Content-Type: application/json")
	public OrderResponse unforzenUserWithdrawal(OrderRequest  orderRequest);
	
	/**
	 * 重新获取验证码（验证支付）
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/reValidPay")
	@Headers("Content-Type: application/json")
	public OrderResponse reValidPay(OrderRequest  orderRequest);
	
	/**
	 * 代扣
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/withholding")
	@Headers("Content-Type: application/json")
	public OrderResponse withholding(OrderRequest  orderRequest);
	
	/**
	 * 赎回
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/payee")
	@Headers("Content-Type: application/json")
	public OrderResponse payee(OrderRequest  orderRequest);


	/**
	 * 支付状态查询
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/queryPay")
	@Headers("Content-Type: application/json")
	public OrderResponse queryPay(OrderRequest  orderRequest);

	/**
	 * 交易订单查询
	 * @param reconciliationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/reconciliation/queryOrder")
	@Headers("Content-Type: application/json")
	public TransactionResponse queryOrder(TransactionRequest  reconciliationRequest);
	
	/**
	 * 回调定单
	 * @param response
	 * @return
	 */
	@RequestLine("POST /settlement/callback/trade")
	@Headers("Content-Type: application/json")
	public boolean callback(OrderResponse  response);

	/**
	 * 冲销单【撤单】
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/callback/reset")
	@Headers("Content-Type: application/json")
	public BaseResponse writerOffOrder(WriterOffOrderRequest req);
	/**
	 * 获取订单数
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/order/getCounNum")
	@Headers("Content-Type: application/json")
	public OrderAccountResponse getCounNum(OrderAccountRequest  req);
	
	
	/**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/order/getChackCompareData")
	@Headers("Content-Type: application/json")
	public List<OrderAccountResponse> getChackCompareData(OrderAccountRequest  req);
	final class ArrayExpander implements Expander {

		@Override
		public String expand(Object value) {
			if (null == value) {
				return "";
			}
			if (value.getClass().isArray()) {
				if (Array.getLength(value) > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < Array.getLength(value); i++) {
						sb.append(",").append(Array.get(value, i).toString());
					}
					return sb.substring(1);
				} else {
					return "";
				}
			}
			return value.toString();
		}

	}
	
	/**
	 * 修改订单状态
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/callback/changeOrderStatus")
	@Headers("Content-Type: application/json")
	public boolean changeOrderStatus(InteractiveRequest req);
	
	/**
	 * 提现审核获取用户可用余额及用户创建时间
	 * @param memberId
	 * @return
	 */
	@RequestLine("POST /settlement/callback/getUserAccountInfo")
	@Headers("Content-Type: application/json")
	public List<UserAccountInfoResponse> getUserAccountInfo(String [] memberId);
	
	/**
	 * 通知业务系统同步账户余额
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/callback/notifyChangeAccountBalance")
	@Headers("Content-Type: application/json")
	public boolean notifyChangeAccountBalance(InteractiveRequest req);

	/**
	 * 单笔充值实时对账
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/order/depositConfirm")
	@Headers("Content-Type: application/json")
	public DepositConfirmResponse depositConfirm(DepositConfirmRequest req);


	/**
	 * 根据银行卡号查询单日限额
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/channelBank/getBanKQuotaByBankCard")
	@Headers("Content-Type: application/json")
	public CardQuotaQueryResponse getSingleQuotaByCardNo(InteractiveRequest req);

	/**
	 *
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/callback/depositbankorder")
	@Headers("Content-Type: application/json")
	public BaseResponse depositBankOrder(DepositBankOrderSyncReq req);

	/**
	 *
	 * @param req
	 * @return
	 */
	@RequestLine("POST /settlement/callback/withdrawbankorder")
	@Headers("Content-Type: application/json")
	public BaseResponse withdrawBankOrder(WithdrawBankOrderSyncReq req);

	/**
	 * 企业解绑
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/enterprise/unbundling")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse unbundling(ElementValidationRequest  elementValidationRequest);

	/**
	 * 企业代扣
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/enterprise/baofuWithholding")
	@Headers("Content-Type: application/json")
	public OrderResponse baofuWithholding(OrderRequest  elementValidationRequest);


	/**
	 * 查询绑定的银行卡
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/enterprise/findBindCard")
	@Headers("Content-Type: application/json")
	public FindBindResponse findBindCard(FindBindRequest  elementValidationRequest);

	/**
	 * 线上登记订单
	 * @param checkInAccountOrderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/checkInAccountOrder")
	@Headers("Content-Type: application/json")
	public BaseResponse checkInAccountOrder(CheckInAccountOrderRequest checkInAccountOrderRequest);

	/**
	 * 支付通道查询
	 * @param bankChannelRequest
	 * @return
	 */
	@RequestLine("POST /settlement/channel/queryPaymentChannel")
	@Headers("Content-Type: application/json")
	public BankChannelResponse queryPaymentChannel(BankChannelRequest bankChannelRequest);

	/**
	 * 根据银行卡号获取银行卡信息
	 * @param interactiveRequest
	 * @return
	 */
	@RequestLine("POST /settlement/channelBank/findBankInfoByCard")
	@Headers("Content-Type: application/json")
	public BankInfoResponse findBankInfoByCard(InteractiveRequest interactiveRequest);
	
	/**
	 * 助贷支付
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/payment")
	@Headers("Content-Type: application/json")
	public OrderResponse loanTrade(OrderRequest orderRequest);
	
	/**
	 * 先锋代扣
	 * @param orderRequest
	 * @return
	 */
	@RequestLine("POST /settlement/order/ucfWithhold")
	@Headers("Content-Type: application/json")
	public OrderResponse ucfWithhold(OrderRequest orderRequest);
	
	/**
	 * 易宝四要素解绑
	 * @param elementValidationRequest
	 * @return
	 */
	@RequestLine("POST /settlement/element/unlockYeePay")
	@Headers("Content-Type: application/json")
	public ElementValidaResponse unlockYeePay(ElementValidationRequest  elementValidationRequest);
	
	/**
	 * 绑卡配置规则
	 * @return ElementValidaRulesResponse
	 */
	@RequestLine("POST /settlement/element/bindingRules")
	@Headers("Content-Type: application/json")
	public ElementValidaRulesResponse bindingRules();
	
	/**
	 * 用户绑卡信息查询
	 * @return UserProtocolResponse
	 */
	@RequestLine("POST /settlement/element/bindCardInfo")
	@Headers("Content-Type: application/json")
	public UserProtocolResponse bindCardInfo(UserProtocolRequest userProtocolRequest);
	
	/**
	 * 网关支付
	 * @return
	 */
	@RequestLine("POST /settlement/order/gatewayPay")
	@Headers("Content-Type: application/json")
	public OrderResponse gatewayPay(OrderRequest orderRequest);
	
}

