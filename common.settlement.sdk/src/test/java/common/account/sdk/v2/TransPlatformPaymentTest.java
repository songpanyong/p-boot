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
 * 平台备付金转账
 * 出：平台备付金 -
 * 入：平台基本户
 * @author mr_gu
 *
 */
@Slf4j
public class TransPlatformPaymentTest {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
//		req.setUserOid("3c97543b24d945c29b97f72861e6fca5");
		req.setRemark("平台基本户转账");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setOrderType("transfer");
		req.setRequestNo(System.currentTimeMillis()+"");
		req.setOrigOrderNo("");
		req.setOutputAccountNo("ANPLATFORM00000012"); //出-指定的平台备付金账户
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(10));
		tradeEvent.setEventType(EventTypeEnum.TRANSFER_PLATFORM_PAYMENT.getCode());
		eventList.add(tradeEvent);
		req.setEventList(eventList);
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp = sdk.transferPlatformPayment(req);
		log.info(System.currentTimeMillis()+"");
		log.info("平台基本户转账返回：{}",JSONObject.toJSONString(resp));
	}

}
