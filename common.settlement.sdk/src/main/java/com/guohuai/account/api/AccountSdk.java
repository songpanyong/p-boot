package com.guohuai.account.api;

import com.guohuai.account.api.request.*;
import com.guohuai.account.api.response.*;
import com.guohuai.settlement.api.request.InteractiveRequest;
import com.guohuai.settlement.api.request.OrderAccountRequest;
import com.guohuai.settlement.api.response.OrderAccountResponse;

import feign.Feign;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class AccountSdk {
	
	private AccountApi api;
	
	public AccountSdk(String apihost,Level level) {
		this.api = Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Slf4jLogger())
				.logLevel(level).target(AccountApi.class, apihost);
	}
	
	public AccountSdk(String apihost) {
		this.api = Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Slf4jLogger())
				.logLevel(Level.FULL).target(AccountApi.class, apihost);
	}

	/**
	 * 创建用户
	* @Title: addUser
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreateUserResponse
	* @throws
	 */
	public CreateUserResponse addUser(@RequestBody CreateUserRequest req) {
		return api.addNewUser(req);
	}
	
	/**
	 * 设置密码
	* @Title: setPassword
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreatePasswordResponse
	* @throws
	 */
	public CreatePasswordResponse setPassword(@RequestBody CreatePasswordRequest req){
		return api.setPassword(req);
	}
	
	/**
	 * 修改密码
	* @Title: modifyPassword
	* @Description: 
	* @param @param req
	* @param @return 
	* @return ModifyPasswordResponse
	* @throws
	 */
	public ModifyPasswordResponse modifyPassword(ModifyPasswordRequest req){
		return api.modifyPassword(req);
	}
	
	/**
	 * 校验密码
	* @Title: validatePassword
	* @Description: TODO
	* @param @param req
	* @param @return 
	* @return ValidatePasswordResponse
	* @throws
	 */
	public ValidatePasswordResponse validatePassword(ValidatePasswordRequest req){
		return api.validatePassword(req);
	}
	
	/**
	 * 账户查询
	* @Title: accountQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return AccountListResponse
	* @throws
	 */
	public AccountListResponse accountQueryList(AccountQueryRequest req){
		return api.accountQueryList(req);
	}
	
	/**
	 * 创建子账户
	* @Title: createAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CreateAccountResponse
	* @throws
	 */
	public CreateAccountResponse createAccount(CreateAccountRequest req){
		return api.createAccount(req);
	}
	
//	/**
//	 * 绑卡
//	* @Title: tiedCard
//	* @Description: 
//	* @param @param req
//	* @param @return 
//	* @return TiedCardResponse
//	* @throws
//	 */
//	public TiedCardResponse tiedCard(TiedCardRequest req){
//		return api.tiedCard(req);
//	}
	
//	/**
//	 * 解绑
//	 * @param req
//	 * @return
//	 */
//	public TiedCardResponse unLockCard(TiedCardRequest req){
//		return api.unLockCard(req);
//	}
	
	/**
	 * 绑卡查询
	* @Title: cardQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return CardListResponse
	* @throws
	 */
	public CardListResponse cardQueryList(CardQueryRequest req){
		return api.cardQueryList(req);
	}
	
	/**
	 * 会员账户交易
	* @Title: trade
	* @Description: 
	* @param @param req
	* @param @return 
	* @return AccountTransResponse
	* @throws
	 */
	public AccountTransResponse trade(AccountTransRequest req){
		return api.addAccountTrans(req);
	}


	/**
	*  发行人放款，收款
	* 收款,orderType传58
	* 放款,orderType传57
	* @Title: publisherTrans
	* @param @param req
	* @param @return
	* @return AccountTransResponse
	* @throws
	 */
	public AccountTransResponse publisherTrans(AccountTransRequest req){
		return api.publisherTrans(req);
	}

    public PurchaseTransCancelResponse tradeCancel(PurchaseTransCancelRequest req){
  		return api.tradeCancel(req);
  	}
	/**
	 * 平台、投资人转账
	* @Title: transferAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return TransferAccountResponse
	* @throws
	 */
	public TransferAccountResponse transferAccount(TransferAccountRequest req){
		return  api.transferAccount(req);
	}
	
	/**
	 * 平台、投资人入账
	* @Title: enterAccount
	* @Description: 
	* @param @param req
	* @param @return 
	* @return EnterAccountResponse
	* @throws
	 */
	public EnterAccountResponse enterAccount(EnterAccountRequest req){
		return  api.enterAccount(req);
	}
	
	
	/**
	 * 修改批量发行人产品户方法
	* @Title: tradepublish
	* @Description: TODO
	* @param @param reqList
	* @param @return 
	* @return AccountTransResponse
	* @throws
	 */
	public AccountTransResponse tradepublish(List<TransPublishRequest> reqList){
		return api.addPublishAccountTrans(reqList);
	}
	
	/**
	 * 账户交易明细查询
	* @Title: tansDetailQuertList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return TransDetailListResponse
	* @throws
	 */
	public TransDetailListResponse tansDetailQuertList(TransDetailQueryRequest req){
		return  api.tansDetailQuertList(req);
	}
	
	/**
	 * 初始化账户查询
	* @Title: initAccountQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return InitAccountListResponse
	* @throws
	 */
	public InitAccountListResponse initAccountQueryList(InitAccountQueryRequest req){
		return  api.initAccountQueryList(req);
	}
	
	/**
	 * 用户查询
	* @Title: userQueryList
	* @Description: 
	* @param @param req
	* @param @return 
	* @return UserListResponse
	* @throws
	 */
	public UserListResponse userQueryList(UserQueryRequest req){
		return api.userQueryList(req);
	}
	
	/**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	public List<OrderAccountResponse> getAccountReconciliationData(OrderAccountRequest req){
		return api.getAccountReconciliationData(req);
	}
	
	/**
	 * 根据订单号查询订单详细信息
	 * @param req
	 * @return
	 */
	public OrderQueryResponse getAccountOrderByOrderCode(OrderAccountRequest req){
		return api.getAccountOrderByOrderCode(req);
	}
	
	/**
	 * 根据用户id获取用户账户余额
	 * @param req
	 * @return
	 */
	public AccountBalanceResponse getAccountBalanceByUserOid(InteractiveRequest req){
		return api.getAccountBalanceByUserOid(req);

	}	
	public PublisherAccountBalanceResponse getPublisherAccountBalanceByUserOid(PublisherAccountQueryRequest req){
		return api.getPublisherAccountBalanceByUserOid(req);
	}
	/**
     * 赎回补单
     * @param req
     * @return
     */
    public AccountTransResponse redeemSupply(AccountTransRequest req){
        return api.redeemSupply(req);
    }

    /**
     * 赎回撤单
     * @param req
     * @return
     */
    public AccountTransResponse redeemCancel(AccountTransRequest req){
        return api.redeemCancel(req);
    }

    /**
     * 赎回确认
     * @param req
     * @return
     */
    public AccountTransResponse redeemConfirm(List<CreateOrderRequest> req){
        return api.redeemConfirm(req);
    }
    
    /**
     * 轧差结算
     * @param req
     * @return
     */
    public AccountSettlementResponse nettingSettlement(AccountSettlementRequest req){
    	return api.nettingSettlement(req);
    }
    
    /**
     * 批量赎回
     * @param req
     * @return
     */
    public AccountBatchRedeemResponse batchRedeem(AccountBatchRedeemRequest req){
    	return api.batchRedeem(req);
    }
    
    /**
     * 转帐
     * @param req
     * @return
     */
    public AccountTransResponse transfer(AccountTransferRequest req){
    	return api.transfer(req);
    }
    
    /**
	 * 获取订单对账数据
	 * @param req
	 * @return
	 */
	public AccountReconciliationDataResponse getAccountAlreadyReconciliationData(OrderAccountRequest req){
		return api.getAccountAlreadyReconciliationData(req);
	}
	
	/**
	 * 修改手机号
	 */
	public CreateUserResponse modifyPhone(CreateUserRequest req) {
		return api.modifyPhone(req);
	}
	
	/**
	 * 查询结算统计平台金额及三方商户余额
	 */
	public AccountBalanceStatisticalResponse accountBalanceStatistical(){
		return api.accountBalanceStatistical();
	}
	
    /**
     * 批量转账
     * @param req
     * @return
     */
    public AccountTransferResponse batchTransfer(AccountBatchTransferRequest req){
    	return api.batchTransfer(req);
    }
    
    /**
     * 批量转账冻结
     * @param req
     * @return
     */
    public AccountTransferResponse batchTransferFrozen(AccountBatchTransferFrozenRequest  req){
    	return api.batchTransferFrozen(req);
    }
    
    /**
     * 单笔转账
     * @param req
     * @return
     */
    public AccountTransferResponse singleTransfer(AccountSingleTransferRequest req){
    	return api.singleTransfer(req);
    }
    
    /**
     * 解冻转账
     * @param req
     * @return
     */
    public AccountTransferResponse unFrozenTransfer(AccountUnFrozenTransferRequest req){
    	return api.unFrozenTransfer(req);
    }
    
    /**
     * 活转定-赎回
     * @param req
     * @return
     */
    public AccountTransResponse redeem(AccountTransRequest req){
    	return api.redeem(req);
    }
    
    /**
     * 活转定-续投（申购）
     * @param req
     * @return
     */
    public AccountTransResponse purchase(AccountTransRequest req){
    	return api.purchase(req);
    }
    
    /**
     * 活转定-续投失败，解冻投资人续投金额
     * @param req
     * @return
     */
    public AccountTransResponse continuUnFrozen(AccountTransRequest req){
    	return api.continuUnFrozen(req);
    }
    
    /**
	 * 创建产品账户
	 * @param req
	 * @return
	 */
	public CreateAccountResponse createProductAccount(CreateAccountRequest req) {
		return api.createProductAccount(req);
	}
    
	/**
	 * 查询产品户可用余额
	 * @param req
	 * @return
	 */
	public ProductAccountListResponse queryProductAccountBalance(ProductAccountRequest req) {
		return api.queryProductAccountBalance(req);
	}
	
	/**
	 * 产品户放款、收款
	 * @param req
	 * @return
	 */
	public AccountTransResponse productAccountTrans(AccountTransRequest req) {
		return api.productAccountTrans(req);
	}
	
	/**
	 * 投资T+0
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse investT0(AccountTransferRequest accountTransferRequest){
		return api.investT0(accountTransferRequest);
	}
	
	/**
	 * 投资T+1
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse investT1(AccountTransferRequest accountTransferRequest){
		return api.investT1(accountTransferRequest);
	}
	
	/**
	 * 赎回T+0
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse redeemT0(AccountTransferRequest accountTransferRequest){
		return api.redeemT0(accountTransferRequest);
	}
	
	/**
	 * 赎回T+1
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse redeemT1(AccountTransferRequest accountTransferRequest){
		return api.redeemT1(accountTransferRequest);
	}

	/**
	 * 轧差
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse netting(AccountTransferRequest accountTransferRequest){
		return api.netting(accountTransferRequest);
	}

	/**
	 * 使用红包
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse useRedPacket(AccountTransferRequest accountTransferRequest){
		return api.useRedPacket(accountTransferRequest);
	}
	
	/**
	 * 转换-实时兑付
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse redeemT0Change(AccountTransferRequest accountTransferRequest){
		return api.redeemT0Change(accountTransferRequest);
	}
	
	/**
	 * 转换-实时投资
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse investT0Change(AccountTransferRequest accountTransferRequest){
		return api.investT0Change(accountTransferRequest);
	}
	
	/**
	 * 续投-实时兑付
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse redeemT0Continued(AccountTransferRequest accountTransferRequest){
		return api.redeemT0Continued(accountTransferRequest);
	}
	
	/**
	 * 续投-非实时兑付
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse redeemT1Continued(AccountTransferRequest accountTransferRequest){
		return api.redeemT1Continued(accountTransferRequest);
	}
	
	/**
	 * 续投-实时投资
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse investT0Continued(AccountTransferRequest accountTransferRequest){
		return api.investT0Continued(accountTransferRequest);
	}
	
	/**
	 * 续投-非实时投资
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse investT1Continued(AccountTransferRequest accountTransferRequest){
		return api.investT1Continued(accountTransferRequest);
	}
	
	/**
	 * 续投-解冻
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse unfreezeContinued(AccountTransferRequest accountTransferRequest){
		return api.unfreezeContinued(accountTransferRequest);
	}

	/**
	 * 返佣
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse rebate(AccountTransferRequest accountTransferRequest){
		return api.rebate(accountTransferRequest);
	}
	
	/**
	 * 平台基本户转账
	 * 出：平台基本户-查库
	 * 入：平台备付金-参数inputAccountNo
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse transferPlatformBasic(AccountTransferRequest accountTransferRequest){
		return api.transferPlatformBasic(accountTransferRequest);
	}
	
	/**
	 * 平台备付金转账
     * 出：平台备付金-参数outputAccountNo
	 * 入：平台基本户-查库
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse transferPlatformPayment(AccountTransferRequest accountTransferRequest){
		return api.transferPlatformPayment(accountTransferRequest);
	}
	
	/**
	 * 发行人基本户转账
	 * 出：发行人基本户-查库 
	 * 入：发行人产品户-参数inputAccountNo
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse transferPublisherBasic(AccountTransferRequest accountTransferRequest){
		return api.transferPublisherBasic(accountTransferRequest);
	}
	
	/**
	 * 发行人产品户转账
	 * 出：发行人产品户-参数outputAccountNo
	 * 入：发行人基本户-查库 
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse transferPublisherProduct(AccountTransferRequest accountTransferRequest){
		return api.transferPublisherProduct(accountTransferRequest);
	}
	
	/**
	 * 轧差-入款
	 * 出：发行人归集户-查库
	 * 入：发行人产品户-参数productAccountNo
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse nettingIncome(AccountTransferRequest accountTransferRequest){
		return api.nettingIncome(accountTransferRequest);
	}
	
	/**
	 * 轧差-出款
	 * 出：发行人产品户-参数productAccountNo
	 * 入：发行人归集户-查库
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse nettingOutcome(AccountTransferRequest accountTransferRequest){
		return api.nettingOutcome(accountTransferRequest);
	}
	
	/**
	 * 投资T+0退款
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse reFundInvestT0(AccountTransferRequest accountTransferRequest){
		return api.reFundInvestT0(accountTransferRequest);
	}
	
	/**
	 * 投资T+1退款
	 * @param accountTransferRequest
	 * @return
	 */
	public AccountTransResponse reFundInvestT1(AccountTransferRequest accountTransferRequest){
		return api.reFundInvestT1(accountTransferRequest);
	}
	
	/**
	 * 平台信息查询
	 * @param platformAccountInfoRequest
	 * @return
	 */
	public PlatformAccountInfoResponse platformAccountInfo(PlatformAccountInfoRequest platformAccountInfoRequest){
		return api.platformAccountInfo(platformAccountInfoRequest);
	}
	
	/**
	 * 平台备付金户详情查询
	 * @param platformReservedAccountDetailRequest
	 * @return
	 */
	public PlatformReservedAccountDetailResponse platformReservedAccountDetail(
			PlatformReservedAccountDetailRequest req){
		return api.platformReservedAccountDetail(req);
	}
	
	/**
	 * 查询发行人账户信息
	 * @param req
	 * @return
	 */
	public PublisherAccountInfoResponse publisherAccountInfo(PublisherAccountInfoRequest req) {
		return api.publisherAccountInfo(req);
	}
}