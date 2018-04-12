package com.guohuai.account.api;

import com.guohuai.account.api.request.*;
import com.guohuai.account.api.response.*;
import com.guohuai.settlement.api.request.InteractiveRequest;
import com.guohuai.settlement.api.request.OrderAccountRequest;
import com.guohuai.settlement.api.response.OrderAccountResponse;

import feign.Headers;
import feign.RequestLine;

import java.util.List;

public interface AccountApi {

	/**
	 * 创建用户
	* @Title: addNewUser
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreateUserResponse
	* @throws
	 */
	@RequestLine("POST /account/user/add")
	@Headers("Content-Type: application/json")
	public CreateUserResponse addNewUser(CreateUserRequest req);
	
	/**
	 * 设置密码
	* @Title: setPassword
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreatePasswordResponse
	* @throws
	 */
	@RequestLine("POST /account/user/setpassword")
	@Headers("Content-Type: application/json")
	public CreatePasswordResponse setPassword(CreatePasswordRequest req);
	
	/**
	 * 修改密码
	* @Title: modifyPassword
	* @Description: 
	* @param @param req
	* @param @return 
	* @return ModifyPasswordResponse
	* @throws
	 */
	@RequestLine("POST /account/user/modifypassword")
	@Headers("Content-Type: application/json")
	public ModifyPasswordResponse modifyPassword(ModifyPasswordRequest req);
	
	/**
	 * 校验密码
	* @Title: validatePassword
	* @Description: 
	* @param @param req
	* @param @return 
	* @return ValidatePasswordResponse
	* @throws
	 */
	@RequestLine("POST /account/user/validatepassword")
	@Headers("Content-Type: application/json")
	public ValidatePasswordResponse validatePassword(ValidatePasswordRequest req);
	
	/**
	 * 账户查询
	* @Title: accountQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return AccountListResponse
	* @throws
	 */
	@RequestLine("POST /account/account/accountlist")
	@Headers("Content-Type: application/json")
	public AccountListResponse accountQueryList(AccountQueryRequest req); 
	
	/**
	 * 创建子账户
	* @Title: createAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreateAccountResponse
	* @throws
	 */
	@RequestLine("POST /account/account/add")
	@Headers("Content-Type: application/json")
	public CreateAccountResponse createAccount(CreateAccountRequest req);
	

	/**
	 * 绑卡查询
	* @Title: cardQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CardListResponse
	* @throws
	 */
	@RequestLine("POST /account/card/cardlist")
	@Headers("Content-Type: application/json")
	public CardListResponse cardQueryList(CardQueryRequest req);
	
	/**
	 * 会员账户交易
	* @Title: addAccountTrans
	* @Description: 
	* @param @param req
	* @param @return 
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/trade")
	@Headers("Content-Type: application/json")
	public AccountTransResponse addAccountTrans(AccountTransRequest req);
	

	/**
	 ** 发行人放款，收款
	* @Title: publisherTrans
	* @param @param req
	* @param @return
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/publishertrans")
	@Headers("Content-Type: application/json")
	public AccountTransResponse publisherTrans(AccountTransRequest req);

	/**
	 * 平台、投资人转账
	* @Title: transferAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return TransferAccountResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/transferaccount")
	@Headers("Content-Type: application/json")
	public TransferAccountResponse transferAccount(TransferAccountRequest req);
	
	/**
	 * 平台、投资人入账
	* @Title: enterAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return EnterAccountResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/enteraccount")
	@Headers("Content-Type: application/json")
	public EnterAccountResponse enterAccount(EnterAccountRequest req);
	
	
	/**
	 * 修改批量发行人产品户方法
	* @Title: addPublishAccountTrans
	* @Description: TODO
	* @param @param reqList
	* @param @return 
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/tradepublish")
	@Headers("Content-Type: application/json")
	public AccountTransResponse addPublishAccountTrans(List<TransPublishRequest> reqList);
	
	/**
	 * 账户交易明细查询
	* @Title: tansDetailQuertList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return TransDetailListResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/detaillist")
	@Headers("Content-Type: application/json")
	public TransDetailListResponse tansDetailQuertList(TransDetailQueryRequest req);
	
	/**
	 * 初始化账户查询
	* @Title: initAccountQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return InitAccountListResponse
	* @throws
	 */
	@RequestLine("POST /account/account/initaccountlist")
	@Headers("Content-Type: application/json")
	public InitAccountListResponse initAccountQueryList(InitAccountQueryRequest req);
	
	/**
	 * 用户查询
	* @Title: userQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return UserListResponse
	* @throws
	 */
	@RequestLine("POST /account/user/userlist")
	@Headers("Content-Type: application/json")
	public UserListResponse userQueryList(UserQueryRequest req);
	
	
	/**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/order/getAccountReconciliationData")
	@Headers("Content-Type: application/json")
	public List<OrderAccountResponse> getAccountReconciliationData(OrderAccountRequest req);
	
	/**
	 * 根据订单号获取订单信息
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/order/getAccountOrderByOrderCode")
	@Headers("Content-Type: application/json")
	public OrderQueryResponse getAccountOrderByOrderCode(OrderAccountRequest req);
	
	/**
	 * 根据用户id获取账户余额
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/getAccountBalanceByUserOid")
	@Headers("Content-Type: application/json")
	public AccountBalanceResponse getAccountBalanceByUserOid(InteractiveRequest req);

	/**
	 *根据用户id和账户类型获取发现行人账户余额
	 */
	@RequestLine("POST /account/account/getPublisherAccountBalanceByUserOid")
	@Headers("Content-Type: application/json")
	public PublisherAccountBalanceResponse getPublisherAccountBalanceByUserOid(PublisherAccountQueryRequest req);

	/**
	 * 赎回补单
	* @Title: addAccountTrans
	* @Description:
	* @param @param req
	* @param @return
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/supply")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemSupply(AccountTransRequest req);

	/**
	 * 赎回撤单
	* @Title: addAccountTrans
	* @Description:
	* @param @param req
	* @param @return
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/cancel")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemCancel(AccountTransRequest req);

	/**
	 * 赎回确认
	* @Title: addAccountTrans
	* @Description:
	* @param @param req
	* @param @return
	* @return AccountTransResponse
	* @throws
	 */
	@RequestLine("POST /account/trans/redeemConfirm")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemConfirm(List<CreateOrderRequest> req);

    /**
     * 申购废单
     */
    @RequestLine("POST /account/trans/transCancel")
   	@Headers("Content-Type: application/json")
   	PurchaseTransCancelResponse tradeCancel(PurchaseTransCancelRequest req);
    
    /**
     * 轧差结算
     * @param req
     * @return
     */
    @RequestLine("POST /account/trans/nettingSettlement")
	@Headers("Content-Type: application/json")
	public AccountSettlementResponse nettingSettlement(AccountSettlementRequest req);
    
    /**
     * 批量赎回
     * @param req
     * @return
     */
    @RequestLine("POST /account/trans/batchRedeem")
   	@Headers("Content-Type: application/json")
   	public AccountBatchRedeemResponse batchRedeem(AccountBatchRedeemRequest req);
    
    /**
     * 账户转账
     * @param req
     * @return
     */
    @RequestLine("POST /account/trans/transfer")
   	@Headers("Content-Type: application/json")
   	public AccountTransResponse transfer(AccountTransferRequest req);
    
    /**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/order/getAccountAlreadyReconciliationData")
	@Headers("Content-Type: application/json")
	public AccountReconciliationDataResponse getAccountAlreadyReconciliationData(OrderAccountRequest req);
	
	/**
	 * 修改密码
	* @Title: modifyPhone
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreateUserResponse
	* @throws
	 */
	@RequestLine("POST /account/user/modifyPhone")
	@Headers("Content-Type: application/json")
	public CreateUserResponse modifyPhone(CreateUserRequest req);

	/**
	 * 查询结算统计平台金额及三方商户余额
	 * @return 统计金额
	 */
	@RequestLine("POST /account/account/accountBalanceStatistical")
	@Headers("Content-Type: application/json")
	public AccountBalanceStatisticalResponse accountBalanceStatistical();

	/**
	 * 批量转账
	 * @param req 批量转账订单
	 * @return 批量转账收单状态
	 */
	@RequestLine("POST /account/trans/batchTransfer")
	@Headers("Content-Type: application/json")
	public AccountTransferResponse batchTransfer(AccountBatchTransferRequest req);

	/**
	 * 批量转账冻结
	 * @param req 冻结参数
	 * @return 冻结结果
	 */
	@RequestLine("POST /account/trans/batchTransferFrozen")
	@Headers("Content-Type: application/json")
	public AccountTransferResponse batchTransferFrozen(
			AccountBatchTransferFrozenRequest req);

	/**
	 * 单笔转账
	 * @param req 单笔转账参数
	 * @return 转账结果
	 */
	@RequestLine("POST /account/trans/singleTransfer")
	@Headers("Content-Type: application/json")
	public AccountTransferResponse singleTransfer(
			AccountSingleTransferRequest req);

	/**
	 * 解冻转账
	 * @param req 解冻转账参数
	 * @return 解冻结果
	 */
	@RequestLine("POST /account/trans/unFrozenTransfer")
	@Headers("Content-Type: application/json")
	public AccountTransferResponse unFrozenTransfer(
			AccountUnFrozenTransferRequest req);

	/**
	 * 活转定-赎回
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/trans/redeem")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeem(AccountTransRequest req);
	
	/**
	 * 活转定-续投（申购）
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/trans/purchase")
	@Headers("Content-Type: application/json")
	public AccountTransResponse purchase(AccountTransRequest req);
	
	/**
	 * 活转定-续投解冻
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/trans/continuUnFrozen")
	@Headers("Content-Type: application/json")
	public AccountTransResponse continuUnFrozen(AccountTransRequest req);
	
	/**
	 * 创建产品账户
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/add/product")
	@Headers("Content-Type: application/json")
	public CreateAccountResponse createProductAccount(CreateAccountRequest req);
	
	/**
	 * 查询产品户可用余额
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/queryProductAccountBalance")
	@Headers("Content-Type: application/json")
	public ProductAccountListResponse queryProductAccountBalance(ProductAccountRequest req);
	
	/**
	 * 产品户放款、收款
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/trans/productAccountTrans")
	@Headers("Content-Type: application/json")
	public AccountTransResponse productAccountTrans(AccountTransRequest req);

	/**
	 * 投资T+0
	 * @param accountTransferRequest
	 * @return
	 */
	@RequestLine("POST /account/transfer/investT0")
	@Headers("Content-Type: application/json")
	public AccountTransResponse investT0(AccountTransferRequest req);

	/**
	 * 投资T+1
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/investT1")
	@Headers("Content-Type: application/json")
	public AccountTransResponse investT1(AccountTransferRequest req);

	/**
	 * 赎回T+0
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/redeemT0")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemT0(AccountTransferRequest req);
	
	/**
	 * 赎回T+1
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/redeemT1")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemT1(AccountTransferRequest req);
	
	/**
	 * 轧差清算
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/netting")
	@Headers("Content-Type: application/json")
	public AccountTransResponse netting(AccountTransferRequest req);


	/**
	 * 使用红包
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/useRedPacket")
	@Headers("Content-Type: application/json")
	public AccountTransResponse useRedPacket(AccountTransferRequest req);
	
	/**
	 * 转换-实时兑付
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/redeemT0Change")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemT0Change(AccountTransferRequest req);
	
	/**
	 * 转换-实时投资
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/investT0Change")
	@Headers("Content-Type: application/json")
	public AccountTransResponse investT0Change(AccountTransferRequest req);
	
	/**
	 * 续投-实时兑付
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/redeemT0Continued")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemT0Continued(AccountTransferRequest req);
	
	/**
	 * 续投-非实时兑付
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/redeemT1Continued")
	@Headers("Content-Type: application/json")
	public AccountTransResponse redeemT1Continued(AccountTransferRequest req);
	
	/**
	 * 续投-实时投资
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/investT0Continued")
	@Headers("Content-Type: application/json")
	public AccountTransResponse investT0Continued(AccountTransferRequest req);
	
	/**
	 * 续投-非实时投资
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/investT1Continued")
	@Headers("Content-Type: application/json")
	public AccountTransResponse investT1Continued(AccountTransferRequest req);
	
	/**
	 * 续投-解冻
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/unfreezeContinued")
	@Headers("Content-Type: application/json")
	public AccountTransResponse unfreezeContinued(AccountTransferRequest req);

	/**
	 * 返佣
	 * @param accountTransferRequest
	 * @return
	 */
	@RequestLine("POST /account/transfer/rebate")
	@Headers("Content-Type: application/json")
	public AccountTransResponse rebate(AccountTransferRequest accountTransferRequest);
	
	/**
	 * 平台基本户转账
	 * @param accountTransferRequest
	 * @return
	 */
	@RequestLine("POST /account/transfer/transferPlatformBasic")
	@Headers("Content-Type: application/json")
	public AccountTransResponse transferPlatformBasic(AccountTransferRequest accountTransferRequest);

	/**
	 * 平台备付金转账
	 * @param accountTransferRequest
	 * @return
	 */
	@RequestLine("POST /account/transfer/transferPlatformPayment")
	@Headers("Content-Type: application/json")
	public AccountTransResponse transferPlatformPayment(AccountTransferRequest accountTransferRequest);
    /**
     * 转账-发行人基本户转账
     */
    @RequestLine("POST /account/transfer/transferPublisherBasic")
	@Headers("Content-Type: application/json")
	public AccountTransResponse transferPublisherBasic(AccountTransferRequest accountTransferRequest);
    
    /**
     * 转账-发行人产品户转账
     */
    @RequestLine("POST /account/transfer/transferPublisherProduct")
	@Headers("Content-Type: application/json")
	public AccountTransResponse transferPublisherProduct(AccountTransferRequest accountTransferRequest);
    
    /**
	 * 轧差-入款
	 */
    @RequestLine("POST /account/transfer/nettingIncome")
	@Headers("Content-Type: application/json")
	public AccountTransResponse nettingIncome(AccountTransferRequest accountTransferRequest);

	/**
	 * 轧差-出款
	 */
    @RequestLine("POST /account/transfer/nettingOutcome")
	@Headers("Content-Type: application/json")
	public AccountTransResponse nettingOutcome(AccountTransferRequest accountTransferRequest);
    
    /**
	 * 投资T+0退款
	 * @param accountTransferRequest
	 * @return
	 */
	@RequestLine("POST /account/transfer/reFundInvestT0")
	@Headers("Content-Type: application/json")
	public AccountTransResponse reFundInvestT0(AccountTransferRequest req);

	/**
	 * 投资T+1退款
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/transfer/reFundInvestT1")
	@Headers("Content-Type: application/json")
	public AccountTransResponse reFundInvestT1(AccountTransferRequest req);

	/**
	 * 查询平台户基本信息
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/platformAccountInfo")
	@Headers("Content-Type: application/json")
	public PlatformAccountInfoResponse platformAccountInfo(PlatformAccountInfoRequest req);
	
	/**
	 * 平台备付金详情查询
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/platformReservedAccountDetail")
	@Headers("Content-Type: application/json")
	public PlatformReservedAccountDetailResponse platformReservedAccountDetail(PlatformReservedAccountDetailRequest req);

	/**
	 * 查询发行人账户信息
	 * @param req
	 * @return
	 */
	@RequestLine("POST /account/account/publisherAccountInfo")
	@Headers("Content-Type: application/json")
	public PublisherAccountInfoResponse publisherAccountInfo(PublisherAccountInfoRequest req);
	
}