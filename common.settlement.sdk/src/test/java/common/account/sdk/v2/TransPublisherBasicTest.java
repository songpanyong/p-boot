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

/**
 * 发行人基本户转账
 * 出：发行人基本户
 * 入：发行人产品户 - 发行人、入款账户
 * @author mr_gu
 *
 */
@Slf4j
public class TransPublisherBasicTest {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setRemark("发行人基本户转账");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setOrderType("transfer");
		req.setRequestNo(System.currentTimeMillis()+"");
		req.setOrigOrderNo("");
		req.setPublisherUserOid("7c74cab2d43042d8a0a17582ecb22f74");
		req.setInputAccountNo("1AN2018012200000001"); //入-指定发行人产品户
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(10));
		tradeEvent.setEventType(EventTypeEnum.TRANSFER_PUBLISHER_BASIC.getCode());
		eventList.add(tradeEvent);
		req.setEventList(eventList);
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp = sdk.transferPublisherBasic(req);
		log.info(System.currentTimeMillis()+"");
		log.info("发行人基本户转账返回：{}",JSONObject.toJSONString(resp));
	}

}
