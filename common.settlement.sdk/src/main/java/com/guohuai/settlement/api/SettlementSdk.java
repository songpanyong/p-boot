package com.guohuai.settlement.api;

import com.guohuai.account.api.response.CardQuotaQueryResponse;
import com.guohuai.settlement.api.request.*;
import com.guohuai.settlement.api.response.*;

import feign.Feign;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import java.util.List;

public class SettlementSdk {
	private SettlementApi api;

	public SettlementSdk(String apihost, Level level) {
		this.api = Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Slf4jLogger())
				.logLevel(level).target(SettlementApi.class, apihost);
	}

	public SettlementSdk(String apihost) {
		this.api = Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Slf4jLogger())
				.logLevel(Level.BASIC).target(SettlementApi.class, apihost);
	}

	public ElementValidaResponse elementValid(ElementValidationRequest elementValidationRequest) {
		return api.elementValid(elementValidationRequest);

	}

	/**
	 * 绑卡申请
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse bindApply(ElementValidationRequest elementValidationRequest) {
		return api.bindApply(elementValidationRequest);
	}

	/**
	 * 绑卡确认
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse bindConfirm(ElementValidationRequest elementValidationRequest) {
		return api.bindConfirm(elementValidationRequest);
	}

	/**
	 * 绑卡确认（不发短信）
	 */
	public ElementValidaResponse bindConfirmWithoutSms(ElementValidationRequest elementValidationRequest) {
		return api.bindConfirmWithoutSms(elementValidationRequest);
	}

	/**
	 * 宝付静默绑卡
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse bindBaofoo(ElementValidationRequest elementValidationRequest) {
		return api.bindBaofoo(elementValidationRequest);
	}

	/**
	 * 企业绑卡
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse bindCard(ElementValidationRequest elementValidationRequest) {
		return api.bindCard(elementValidationRequest);
	}

	/**
	 * 提现申请
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse applyWithdrawal(OrderRequest orderRequest) {
		return api.applyWithdrawal(orderRequest);
	}

	/**
	 * 提现确认
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse confirmWthdrawal(OrderRequest orderRequest) {
		return api.confirmWthdrawal(orderRequest);
	}

	/**
	 * 提现解冻
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse unforzenUserWithdrawal(OrderRequest orderRequest) {
		return api.unforzenUserWithdrawal(orderRequest);
	}

	/**
	 * 验证码（验证支付）
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse validPay(OrderRequest orderRequest) {
		return api.validPay(orderRequest);
	}

	/**
	 * 重新获取验证码（验证支付）
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse reValidPay(OrderRequest orderRequest) {
		return api.reValidPay(orderRequest);
	}

	/**
	 * 代扣
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse withholding(OrderRequest orderRequest) {
		return api.withholding(orderRequest);
	}

	/**
	 * 申请签约代扣协议
	 * @param authenticationRequest
	 * @return
	 */
	public AuthenticationResponse applyAgreement(AuthenticationRequest authenticationRequest) {
		return api.applyAgreement(authenticationRequest);
	}

	/**
	 * 四要素解绑
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse unlock(ElementValidationRequest elementValidationRequest) {
		return api.unlock(elementValidationRequest);
	}

	/**
	 * 四要素解绑
	 * @param elementValidationRequest
	 * @return
	 */
	public Boolean checkBank(ElementValidationRequest elementValidationRequest) {
		return api.checkBank(elementValidationRequest);
	}

	/**
	 * 确认签约代扣协议
	 * @param authenticationRequest
	 * @return
	 */
	public AuthenticationResponse confirmAgreement(AuthenticationRequest authenticationRequest) {
		return api.confirmAgreement(authenticationRequest);
	}

	/**
	 * 支付
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse pay(OrderRequest orderRequest) {
		return api.pay(orderRequest);
	}

	/**
	 * 赎回
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse payee(OrderRequest orderRequest) {
		return api.payee(orderRequest);
	}

	/**
	 * 支付状态查询
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse queryPay(OrderRequest orderRequest) {
		return api.queryPay(orderRequest);
	}

	/**
	 * 交易订单查询
	 * @param reconciliationRequest
	 * @return
	 */
	public TransactionResponse queryOrder(TransactionRequest reconciliationRequest) {
		return api.queryOrder(reconciliationRequest);
	}

	/**
	 * 交易订单回调
	 * @param response
	 * @return
	 */
	public boolean callback(OrderResponse response) {
		return api.callback(response);
	}

	/**
	 * 冲销单 撤单
	 * @param req
	 * @return
	 */
	public BaseResponse writerOffOrder(WriterOffOrderRequest req) {
		return api.writerOffOrder(req);
	}

	/**
	 * 获取订单数
	 * @param req
	 * @return
	 */
	public OrderAccountResponse getCounNum(OrderAccountRequest req) {
		return api.getCounNum(req);
	}

	/**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	public List<OrderAccountResponse> getChackCompareData(OrderAccountRequest req) {
		return api.getChackCompareData(req);
	}

	/**
	 * 修改订单状态 场景:结算系统失败订单,银行成功,需修改业务系统订单状态
	 * @param req
	 * @return
	 */
	public boolean changeOrderStatus(InteractiveRequest req) {
		return api.changeOrderStatus(req);
	}

	/**
	 * 提现审核获取用户可用余额及用户创建时间
	 * @param memberId
	 * @return
	 */
	public List<UserAccountInfoResponse> getUserAccountInfo(String[] memberId) {
		return api.getUserAccountInfo(memberId);
	}

	/**
	 * 通知业务系统同步账户余额
	 * @param req
	 * @return
	 */
	public boolean notifyChangeAccountBalance(InteractiveRequest req) {
		return api.notifyChangeAccountBalance(req);
	}

	/**
	 * 单笔充值实时对账
	 * @param req
	 * @return
	 */
	public DepositConfirmResponse depositConfirm(DepositConfirmRequest req) {
		return api.depositConfirm(req);
	}

	/**
	 * 限额查询
	 * @param req
	 * @return
	 */
	public CardQuotaQueryResponse getSingleQuotaByCardNo(InteractiveRequest req) {
		return api.getSingleQuotaByCardNo(req);
	}

	/**
	 * 充值登记
	 * @param req
	 * @return
	 */
	public BaseResponse depositBankOrder(DepositBankOrderSyncReq req) {
		return api.depositBankOrder(req);
	}

	/**
	 * 提现登记
	 * @param req
	 * @return
	 */
	public BaseResponse withdrawBankOrder(WithdrawBankOrderSyncReq req) {
		return api.withdrawBankOrder(req);
	}

	/**
	 * 企业解绑
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse unbundling(ElementValidationRequest elementValidationRequest) {
		return api.unbundling(elementValidationRequest);
	}

	/**
	 * 企业代扣
	 * @param elementValidationRequest
	 * @return
	 */
	public OrderResponse baofuWithholding(OrderRequest elementValidationRequest) {
		return api.baofuWithholding(elementValidationRequest);
	}

	/**
	 * 查询绑定的银行卡
	 * @param elementValidationRequest
	 * @return
	 */
	public FindBindResponse findBindCard(FindBindRequest elementValidationRequest) {
		return api.findBindCard(elementValidationRequest);
	}

	/**
	 * 登记订单
	 * @param checkInAccountOrderRequest
	 * @return
	 */
	public BaseResponse checkInAccountOrder(CheckInAccountOrderRequest checkInAccountOrderRequest) {
		return api.checkInAccountOrder(checkInAccountOrderRequest);
	}

	/**
	 * 支付通道查询
	 * @param bankChannelRequest
	 * @return
	 */
	public BankChannelResponse queryPaymentChannel(BankChannelRequest bankChannelRequest) {
		return api.queryPaymentChannel(bankChannelRequest);
	}

	/**
	 * 根据银行卡号获取银行卡信息
	 * @param interactiveRequest
	 * @return
	 */
	public BankInfoResponse findBankInfoByCard(InteractiveRequest interactiveRequest) {
		return api.findBankInfoByCard(interactiveRequest);
	}

	/**
	 * 助贷
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse loanTrade(OrderRequest orderRequest) {
		return api.loanTrade(orderRequest);
	}

	/**
	 * 先锋代扣
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse ucfWithhold(OrderRequest orderRequest) {
		return api.ucfWithhold(orderRequest);
	}

	/**
	 * 易宝四要素解绑
	 * @param elementValidationRequest
	 * @return
	 */
	public ElementValidaResponse unlockYeePay(ElementValidationRequest elementValidationRequest) {
		return api.unlockYeePay(elementValidationRequest);
	}

	/**
	 * 绑卡配置规则
	 * @return ElementValidaRulesResponse
	 */
	public ElementValidaRulesResponse bindingRules() {
		return api.bindingRules();
	}
	
	/**
	 * 用户绑卡信息查询
	 * @param userProtocolRequest
	 * @return
	 */
	public UserProtocolResponse bindCardInfo(UserProtocolRequest userProtocolRequest){
		return api.bindCardInfo(userProtocolRequest);
	}
	
	/**
	 * 网关支付
	 * @param orderRequest
	 * @return
	 */
	public OrderResponse gateway(OrderRequest orderRequest){
		return api.gatewayPay(orderRequest);
	}
}