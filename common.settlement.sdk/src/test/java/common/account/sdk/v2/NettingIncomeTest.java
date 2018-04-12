package common.account.sdk.v2;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransferRequest;
import com.guohuai.account.api.request.entity.TradeEvent;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.account.component.util.EventTypeEnum;
import com.guohuai.basic.common.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/***
 * 轧差-入款
 * 出：发行人归集户
 * 入：发行人产品户
 * @author mr_gu
 *
 */
@Slf4j
public class NettingIncomeTest {

	public static void main(String[] args) {
		//轧差-入款
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo(System.currentTimeMillis()+"");
		req.setPublisherUserOid("1516951579037");
		req.setProductAccountNo("1AN2018012600000004");
		req.setOrderType("nettingIncome");
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		req.setRemark(EventTypeEnum.getEnumName(tradeEvent.getEventType()));
		tradeEvent.setBalance(new BigDecimal(1));
		tradeEvent.setEventType(EventTypeEnum.NETTING_DEPOSIT.getCode());
		eventList.add(tradeEvent);
		req.setEventList(eventList);
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp = sdk.nettingIncome(req);
		log.info(System.currentTimeMillis()+"");
		log.info("tradeEvent {}：{}",req.getRemark(),JSONObject.toJSONString(resp));
	}

}
