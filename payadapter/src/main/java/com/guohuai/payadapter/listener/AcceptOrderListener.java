package com.guohuai.payadapter.listener;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.bankutil.BankUtilEntity;
import com.guohuai.payadapter.bankutil.BankUtilService;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.component.PayDesPlus;
import com.guohuai.payadapter.component.TradeEventCodeEnum;
import com.guohuai.payadapter.control.ChannelDao;
import com.guohuai.payadapter.control.PaymentAmountDao;
import com.guohuai.payadapter.listener.event.OrderEvent;
import com.guohuai.payadapter.listener.event.TradeEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: AcceptOrderListener
 * @Description: 接收定单
 * @author xueyunlong
 * @date 2016年11月9日 下午1:41:39
 *
 */
@Slf4j
@Component
public class AcceptOrderListener {

	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private PaymentAmountDao amountDao;
	@Autowired
	private BankUtilService bankUtilService;

	@EventListener
	public void acceptOrderEvent(OrderEvent event) {
		log.info("An event AcceptOrder: {}", JSONObject.toJSONString(event));
		if (StringUtil.isEmpty(event.getSourceType())) {
			event.setReturnCode(TradeEventCodeEnum.trade_1001.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}
		if (StringUtil.isEmpty(event.getTradeType())) {
			event.setReturnCode(TradeEventCodeEnum.trade_1002.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}
		if (StringUtil.isEmpty(event.getAmount().toString())) {
			event.setReturnCode(TradeEventCodeEnum.trade_1003.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}
		//获取银行代码
		BankUtilEntity bank = bankUtilService.getBankByCard(event.getCardNo());
		if(bank == null){
			event.setReturnCode(TradeEventCodeEnum.trade_1009.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}
		log.info("交易银行信息:"+bank);
		event.setBankName(bank.getBankName());
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setReturnCode(Constant.FAIL);
		// 查询支付渠道
		Object[] channels = getBestChannel(event, bank);
		if (null == channels || channels.length == 0) {
			event.setReturnCode(TradeEventCodeEnum.trade_1004.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}
		if("02".equals(event.getTradeType())){//付款
			// 取第一个匹配到的渠道
			for (Object channelObj : channels) {
				log.info("支付渠道：{}", JSONObject.toJSONString(channelObj));
				Object[] channel = (Object[]) channelObj;
				tradeEvent.setChannel(nullToStr(channel[0]));
				tradeEvent.setTradeType(nullToStr(channel[1]));
				tradeEvent.setPlatformAccount(nullToStr(channel[2]));// 平台账号
				tradeEvent.setTreatmentMethod(nullToStr(channel[3])); //支付方式
				tradeEvent.setMerchantId(nullToStr(channel[5])); //商户ID
				tradeEvent.setProductId(nullToStr(channel[6])); //产品ID
				tradeEvent.setCustBankNo(nullToStr(channel[9])); //银行代码
				tradeEvent.setLaunchplatform(event.getLaunchplatform());
				tradeEvent.setInAcctCityName(event.getInAcctCityName());
				tradeEvent.setInAcctProvinceCode(event.getInAcctProvinceCode());
				tradeEvent.setInAcctDept(event.getBranchBankName());
//				tradeEvent.setUserID(event.getUserOid());
				//金运通
				tradeEvent.setBankName(bank.getBankName());
				tradeEvent.setCardNo(event.getCardNo());
				tradeEvent.setRealName(event.getRealName());//姓名
				String	amount = String.valueOf(event.getAmount());//交易金额
				tradeEvent.setAmount(amount);
				break;
			}
		}else if("01".equals(event.getTradeType())){//收款
			//获取当日交易额度
//			BigDecimal daliyAmonut = amountDao.findDaliyAmonut(event.getUserOid());
			//20180102根据卡号判断当日交易额度
			String cardNo = PayDesPlus.encrypt(event.getCardNo());
			BigDecimal daliyAmonut = amountDao.findDaliyAmonutByCardNo(event.getUserOid(),cardNo);
			for (Object channelObj : channels) {
				log.info("支付渠道：{}", JSONObject.toJSONString(channelObj));
				Object[] channel = (Object[]) channelObj;
				String dailyLimitStr = nullToStr(channel[8]);//日限额
				BigDecimal dailyLimit = null;
				if(!StringUtil.isEmpty(dailyLimitStr)){
					dailyLimit = new BigDecimal(nullToStr(channel[8]));//日限额
				}
				String singleQuotaStr = nullToStr(channel[11]);//渠道银行单笔限额
				//判断是否超过单笔限额
				boolean outOfSingleQuota = false;
				if(singleQuotaStr != null){
					BigDecimal singleQuota = new BigDecimal(singleQuotaStr);
					if(singleQuota.compareTo(event.getAmount())==-1){//单笔限额<交易金额
						outOfSingleQuota = true;//超过单笔限额
					}
					log.info("判断充值是否超单笔限额，单笔交易金额：{}，单笔限额：{}，是否超限：{}", 
							event.getAmount(), singleQuota, outOfSingleQuota);
				}
				if(!outOfSingleQuota){
					//判断是否超额
					boolean outOfAmount = outOfAmount(event.getAmount(),daliyAmonut,dailyLimit);
					log.info("判断充值是否超单日累计限额，单笔交易金额：{}，当日累计交易金额：{}，单日限额：{}, 是否超限额：{}",
							event.getAmount(), daliyAmonut, dailyLimit, outOfAmount);
					if(!outOfAmount){
						tradeEvent.setChannel(nullToStr(channel[0]));
						tradeEvent.setTradeType(nullToStr(channel[1]));
						tradeEvent.setPlatformAccount(nullToStr(channel[2]));// 平台账号
						tradeEvent.setTreatmentMethod(nullToStr(channel[3])); //支付方式
						tradeEvent.setMerchantId(nullToStr(channel[5])); //商户ID
						tradeEvent.setProductId(nullToStr(channel[6])); //产品ID
						tradeEvent.setCustBankNo(nullToStr(channel[9])); //银行代码
						break;
					}else{
						tradeEvent.setEmergencyMark("outOfAmount");
					}
				}else {
					tradeEvent.setEmergencyMark("outOfSingleQuota");
				}
			}
		}
		if(tradeEvent.getChannel() == null&&"outOfSingleQuota".equals(tradeEvent.getEmergencyMark())){//超过单笔限额
			event.setReturnCode(TradeEventCodeEnum.trade_1010.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}else if(tradeEvent.getChannel() == null&&"outOfAmount".equals(tradeEvent.getEmergencyMark())){//超过日限额
			event.setReturnCode(TradeEventCodeEnum.trade_1008.getCode());
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}else if(tradeEvent.getChannel() == null){
			event.setReturnCode(TradeEventCodeEnum.trade_1004.getCode());//未匹配到支付渠道
			event.setErrorDesc(TradeEventCodeEnum.getEnumName(event.getReturnCode()));
			return;
		}

		tradeEvent.setCustID(event.getCustAccountId());// 身份证号
		tradeEvent.setRealName(event.getRealName());// 付款人姓名
		tradeEvent.setCardNo(event.getCardNo());// 付款人卡号-银行卡号-金运通
		if(StringUtil.isEmpty(tradeEvent.getCustBankNo())){
			tradeEvent.setCustBankNo(event.getBankTypeCode()); // 卡编号
		}
		tradeEvent.setPayNo(event.getPayNo());// 支付流水号-订单号-金运通
		tradeEvent.setOrderNo(event.getOrderNo());//订单号-金运通二次支付
		tradeEvent.setAmount(String.valueOf(event.getAmount()));//金额-金运通
		tradeEvent.setProtocolNo(event.getTreatyNo());// 代扣协议号
		//金运通参数
		tradeEvent.setUserOid(event.getUserOid());//客户号-金运通
		tradeEvent.setMobile(event.getMobile());//手机号-金运通二次支付
		tradeEvent.setVerifyCode(event.getVerifyCode());//验证码-金运通二次支付
		tradeEvent.setBindId(event.getBindId());//支付绑定白标识
		tradeEvent.setBusinessNo(event.getBusinessNo());//宝付业务流水号，用于支付推进
		tradeEvent.setOutPaymentId(event.getOutPaymentId());
		tradeEvent.setOutTradeNo(event.getOutTradeNo());
		tradeEvent.setBindId(event.getBindId());
		//先锋支付代付
		tradeEvent.setAccountType(event.getAccountType());
		
		//构建返回信息
		event.setMerchantId(tradeEvent.getMerchantId());
		event.setProductId(tradeEvent.getProtocolNo());
		event.setEmergencyMark(tradeEvent.getEmergencyMark());
		event.setPlatformName(tradeEvent.getPlatformName());
		event.setPlatformAccount(tradeEvent.getPlatformAccount());
		event.setPayAddress(tradeEvent.getPayAddress());
		event.setCrossFlag(tradeEvent.getUnionFlag());
		event.setDistanceMark(tradeEvent.getAddrFlag());
		event.setChannel(tradeEvent.getChannel());
		
		//当支付方式为手动，并且定单来源为用户发起的，不进行转账处理，走线下人功处理   定单来源为后台发起时，可以支付
		if(Constant.TreatmentMethod_Manual.equals(tradeEvent.getTreatmentMethod())){
			if(Constant.Launchplatform_front.equals(tradeEvent.getLaunchplatform())){
				event.setReturnCode(TradeEventCodeEnum.trade_1007.getCode());
				event.setErrorDesc(TradeEventCodeEnum.trade_1007.getName());
				return;
			}
		}
		
			//推送处理
		eventPublisher.publishEvent(tradeEvent);
		
		//返回信息
		event.setBusinessNo(tradeEvent.getBusinessNo());//宝付业务流水号，用于支付推进
		event.setHostFlowNo(tradeEvent.getHostFlowNo());
		event.setReturnCode(tradeEvent.getReturnCode());
		event.setErrorDesc(tradeEvent.getErrorDesc());
		event.setBindId(tradeEvent.getBindId());
		event.setOutPaymentId(tradeEvent.getOutPaymentId());
		event.setOutTradeNo(tradeEvent.getOutTradeNo());
		
		log.info("An event responseOrder: {}", JSONObject.toJSONString(event));
	}
	
	String nullToStr(Object str){
		if(null == str){
			return "";
		}
		return str.toString();
	}
	
	/**
	 * 获取最优渠道
	 */
	public Object[] getBestChannel(OrderEvent event, BankUtilEntity bank){
		Object[] channels = null;
		//获取满足条件的扣款渠道
		log.info("SourceType:"+event.getSourceType()+"&TradeType:"+event.getTradeType()+"&BankCode:"+bank.getBankCode());
		if("02".equals(event.getTradeType())){//提现
			channels = channelDao.queryChannel(event.getSourceType(), event.getTradeType(), bank.getBankCode(), event.getAmount());
		}else if("01".equals(event.getTradeType())){//充值
			channels = channelDao.queryBestChannel(event.getSourceType(), event.getTradeType(), bank.getBankCode());
		}
		return channels;
	}
	
	/**
	 * 获取银行编码支持的渠道
	 */
	public Object[] queryChannels(String standardCode){
			log.info("standardCode:{}",standardCode);
		return channelDao.queryChannels(standardCode);
	}
	
	
	
	/**
	 * 提供精确乘法运算的mul方法
	 * @param b1 被乘数
	 * @param b2 乘数
	 * @return 两个参数的积
	 */
	public BigDecimal mul(BigDecimal b1,BigDecimal b2){
		double d = b1.multiply(b2).doubleValue();
		BigDecimal b = new BigDecimal(Double.valueOf(d));
		return b;
	}
	
	/**
	 * 判断是否超日限额
	 * @param amount
	 * @param daliyAmonut
	 * @param dailyLimit
	 * @return
	 */
	public boolean outOfAmount(BigDecimal amount, BigDecimal daliyAmonut, BigDecimal dailyLimit){
		if(daliyAmonut != null&&dailyLimit != null){//交易额和日限额都不为空（交易额空表示未交易，日限额为空表示无上限）
//			daliyAmonut = daliyAmonut.add(amount);//当前交易额+申请交易额
			if(daliyAmonut.compareTo(dailyLimit)==1){//当前交易额+申请交易额>日限额
				return true;
			}
		}else if(dailyLimit != null){
			if(amount.compareTo(dailyLimit)==1){
				return true;
			}
		}
		return false;
	}
	
}
