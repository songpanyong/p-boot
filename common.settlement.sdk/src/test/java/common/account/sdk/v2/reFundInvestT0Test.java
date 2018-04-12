package common.account.sdk.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransferRequest;
import com.guohuai.account.api.request.entity.TradeEvent;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.account.component.util.EventTypeEnum;
import com.guohuai.account.component.util.OrderTypeEnum;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class reFundInvestT0Test {

	public static void main(String[] args) {
		//退款
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setUserOid("efe7f83934184d2ea86543e08a0eea5d");
		req.setRemark("退款");
		req.setOrderType(OrderTypeEnum.REFUND.getCode());
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo(System.currentTimeMillis()+"");
		req.setProductAccountNo("1AN2018012600000004");
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(10));
		tradeEvent.setEventType(EventTypeEnum.REFUND_INVEST_T0.getCode());
		eventList.add(tradeEvent);
		TradeEvent tradeEvent2 = new TradeEvent();
		tradeEvent2.setBalance(new BigDecimal(1));
		tradeEvent2.setEventType(EventTypeEnum.REFUND_USE_VOUCHER_T0.getCode());
		eventList.add(tradeEvent2);
		req.setEventList(eventList);
		req.setOrigOrderNo("11111111111111");
		long sendTime = System.currentTimeMillis();
		AccountTransResponse resp = sdk.reFundInvestT0(req);
		long endTime = System.currentTimeMillis();
		log.info("耗时:"+ (endTime-sendTime));
		log.info(JSONObject.toJSONString(resp));
	}

}
