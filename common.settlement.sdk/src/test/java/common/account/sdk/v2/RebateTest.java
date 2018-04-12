package common.account.sdk.v2;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransferRequest;
import com.guohuai.account.api.request.entity.TradeEvent;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.account.component.util.EventTypeEnum;
import com.guohuai.basic.common.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RebateTest {

	public static void main(String[] args) {
		//返佣
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setUserOid("3c97543b24d945c29b97f72861e6fca5");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setOrderType(EventTypeEnum.REBATE.getCode());
		req.setRequestNo(System.currentTimeMillis()+"");
		req.setProductAccountNo("");
		req.setOrigOrderNo("");
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(10));
		tradeEvent.setEventType(EventTypeEnum.REBATE.getCode());
		req.setRemark(EventTypeEnum.getEnumName(tradeEvent.getEventType()));
		eventList.add(tradeEvent);
		req.setEventList(eventList);
		AccountTransResponse resp = sdk.rebate(req);
		log.info("tradeEvent {}：{}",req.getRemark(),JSONObject.toJSONString(resp));
	}

}
