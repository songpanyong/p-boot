package com.guohuai.payadapter.listener;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.MemberBalanceDetail;
import com.guohuai.payment.baofu.cmd.MemberBalanceRequest;
import com.guohuai.payment.baofu.cmd.MemberBalanceResp;

/**
 * 账户余额查询
 * @author hans
 *
 */
@Slf4j
@Component
public class BaofooMemBalanceQryListener {

	@Autowired
	private BaoFuService baofuService;
	
	@EventListener(condition = "#event.tradeType == 'memBalanceQry'")
	public void payEvent(TradeEvent event) {
		if(StringUtil.isEmpty(event.getAccountType())){
			log.info("账户类型为空");
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("商户余额查询账户类型参数为空");
			return;
		}
		String accountType = event.getAccountType();
		String accountName = "0".equals(accountType) ? "全部":"1".equals(accountType)?"基本户":"2".equals(accountType)?"未结算账户":"3".equals(accountType)?"冻结账户":"4".equals(accountType)?"保证金账户":"5".equals(accountType)?"资金托管账户":"未知账户类型";
		log.info("宝付商户余额查询 账户类型为:{}, {}", accountType,accountName);
		MemberBalanceRequest req = MemberBalanceRequest.builder().account_type(accountType).build();
		MemberBalanceResp res = null;
		try {
			res =  baofuService.memberBalanceQuery(req);
		} catch (Exception e) {
			log.error( "宝付商户余额查询异常:exception:{}", e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			e.printStackTrace();
		}
		log.info("接口返回参数："+res);
		if(null !=res){
			if("0000".equals(res.getReturn_code())){
				event.setReturnCode(Constant.SUCCESS);
				event.setErrorDesc(res.getReturn_msg());
				Map<String,String> balanceMap = new HashMap<String, String>();
				if(res.getTrans_reqData().size()>0){
					for(MemberBalanceDetail detail : res.getTrans_reqData()){
						String accType = detail.getAccount_type();
						String balance = detail.getBalance();
						balanceMap.put(accType, balance);
					}
					event.setAccBalance(balanceMap);
				}
			}else{
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc(res.getReturn_msg());
			}
		}
		log.info("宝付商户余额查询返回结果,returnCode:{},returnMsg:{}",event.getReturnCode(),event.getErrorDesc());
	}

}
