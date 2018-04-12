package com.guohuai.tulip.schedule.notify;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.tulip.platform.facade.FacadeService;

@Service
public class AsynchEventManager {
	
	@Autowired
	private AsynchEventService asynchEventService;
	
	@Autowired
	private FacadeService facadeSerice;
	
	//注册事件
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onRegister(String biz, String asyncEventOid) {
		facadeSerice.onRegisterAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	
	/**
	 * 绑卡事件
	 *
	 * @param biz
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onBindingCard(String biz, String asyncEventOid) {
		facadeSerice.onBindingCardAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	
	//推荐人
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onFriend(String biz, String asyncEventOid) {
		facadeSerice.onFriendAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	
	//实名认证
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onSetRealName(String biz, String asyncEventOid) {
		facadeSerice.onSetRealNameAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}

	//投资
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onInvestment(String biz, String asyncEventOid) {
		facadeSerice.onInvestmentAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	
	//赎回
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onRedeem(String biz, String asyncEventOid) {
		facadeSerice.onRedeemAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//到期兑付
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onBearer(String biz, String asyncEventOid) {
		facadeSerice.onBearerAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//提现事件
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onCash(String biz, String asyncEventOid) {
		facadeSerice.onCashAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//退款
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onRefund(String biz, String asyncEventOid) {
		facadeSerice.onRefundAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//充值
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onRecharge(String biz, String asyncEventOid) {
		facadeSerice.onRechargeAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//签到
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onSign(String biz, String asyncEventOid) {
		facadeSerice.onSignAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	//流标
	@Transactional(value = TxType.REQUIRES_NEW)
	public void onInvalidBids(String biz, String asyncEventOid) {
		facadeSerice.onInvalidBidsAsynch(biz);
		asynchEventService.updateAsynchEvent(asyncEventOid);
	}
	
}
