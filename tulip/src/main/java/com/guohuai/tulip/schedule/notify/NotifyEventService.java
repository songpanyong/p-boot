package com.guohuai.tulip.schedule.notify;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.tulip.platform.eventAnno.EventConstants;
import com.guohuai.tulip.util.Collections3;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotifyEventService {
	
	@Autowired
	private AsynchEventService asynchEventService;
	@Autowired
	private AsynchEventManager asynchEventManager;
	
	@Autowired
	private AsynchEventDao asynchEventDao; 
	
	public void saveEvent(AsynchEventEntity entity){
		asynchEventDao.save(entity);
	}
	
	/**
	 * 注册
	 */
	public void sendRegisterEvent(){
		log.info("==============异步定时查询并发送注册事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_REGISTER);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送注册、绑卡事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onRegister(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送注册、绑卡事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * //推荐人
	 */
	public void sendFriendEvent(){
		log.info("==============异步定时查询并发送推荐人事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_FRIEND);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送推荐人事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onFriend(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送推荐人事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 实名认证
	 */
	public void sendSetRealNameEvent(){
		log.info("==============异步定时查询并发送实名认证事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_AUTHENTICATION);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送实名认证事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onSetRealName(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送实名认证事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	
	/**
	 * 投资
	 */
	public void sendInvestmentEvent(){
		log.info("==============异步定时查询并发送投资事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_INVESTMENT);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送投资事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onInvestment(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送投资事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 赎回
	 */
	public void sendRedeemEvent(){
		log.info("==============异步定时查询并发送赎回事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_REDEEM);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送赎回事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onRedeem(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送赎回事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 到期兑付
	 */
	public void sendBearerEvent(){
		log.info("==============异步定时查询并发送到期兑付事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_BEARER);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送到期兑付事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onBearer(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送到期兑付事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 提现事件
	 */
	public void sendCashEvent(){
		log.info("==============异步定时查询并发送提现事件事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_CASH);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送提现事件事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onCash(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送提现事件事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 退款
	 */
	public void sendRefundEvent(){
		log.info("==============异步定时查询并发送提现事件事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_REFUND);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送退款事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onRefund(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送退款事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 绑卡
	 */
	public void sendBindingCardEvent(){
		log.info("==============异步定时查询并发送绑卡事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_BINDINGCARD);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送绑卡事件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onBindingCard(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送绑卡事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 充值
	 */
	public void sendRechargeEvent(){
		log.info("==============异步定时查询并发送充值事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_RECHARGE);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送充值件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onRecharge(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送充值事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 签到
	 */
	public void sendSignEvent(){
		log.info("==============异步定时查询并发送签到事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_SIGN);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送签到件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onSign(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送签到事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}
	/**
	 * 流标
	 */
	public void sendInvalidBidsEvent(){
		log.info("==============异步定时查询并发送流标事件=================");
		List<AsynchEventEntity> notifys = asynchEventService.getEventList(EventConstants.EVENTTYPE_INVALIDBIDS);
		
		if (!Collections3.isEmpty(notifys)) {
			for (AsynchEventEntity notify : notifys) {
				try {
					log.info("====异步定时查询并发送流标件, 待发送的事件 {}", JSONObject.toJSONString(notify));
					asynchEventManager.onInvalidBids(notify.getEventStr(), notify.getOid());
					log.info("====异步定时查询并发送流标事件, 发送成功！ ");
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return;
	}

}
