package common.account.sdk.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.language.bm.Lang;

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
public class InvestT1Test {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setUserOid("efe7f83934184d2ea86543e08a0eea5d");
		req.setPublisherUserOid("1516951579037");
		req.setRemark("申购");
		req.setOrderType(OrderTypeEnum.INVEST_T1.getCode());
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo(System.currentTimeMillis()+"");
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(101));
		tradeEvent.setEventType(EventTypeEnum.INVEST_T1.getCode());
		eventList.add(tradeEvent);
		TradeEvent tradeEvent2 = new TradeEvent();
		tradeEvent2.setBalance(new BigDecimal(11));
		tradeEvent2.setEventType(EventTypeEnum.USE_VOUCHER_T1.getCode());
		eventList.add(tradeEvent2);
		req.setEventList(eventList);
		long sendTime = System.currentTimeMillis();
		AccountTransResponse resp = sdk.investT1(req);
		long endTime = System.currentTimeMillis();
		log.info("耗时:"+ (endTime-sendTime));
		log.info(JSONObject.toJSONString(resp));
	}
	
	//8135c7f2c98e4c4fa5a85a3817a25ca5
	//34c8a3311774499daf3767237a633eea
	//c99739c1072b419c8437ccec5d7299b4
	//3bd52d4dd2b2486f8f114e89401066d4

}
