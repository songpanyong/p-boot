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
 * 转换- 实时投资
 * 出：投资人续投冻结户
 * 入：发行人产品户
 * @author mr_gu
 *
 */
@Slf4j
public class TransferInvestT0ChangeTest {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransferRequest req = new AccountTransferRequest();
		req.setUserOid("3c97543b24d945c29b97f72861e6fca5");
		req.setRemark("转换- 实时投资");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setOrderType("invest");
		req.setRequestNo(System.currentTimeMillis()+"");
//		req.setProductAccountNo("1AN2018012600000004");//发行人产品户号
		req.setOrigOrderNo("");
		List<TradeEvent> eventList = new ArrayList<TradeEvent>();
		TradeEvent tradeEvent = new TradeEvent();
		tradeEvent.setBalance(new BigDecimal(10));
		tradeEvent.setEventType(EventTypeEnum.INVEST_T0_CHANGE.getCode());
		eventList.add(tradeEvent);
		req.setEventList(eventList);
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp = sdk.investT0Change(req);
		log.info(System.currentTimeMillis()+"");
		log.info("转换- 实时投资返回：{}",JSONObject.toJSONString(resp));
	}

}
