package common.settlement.sdk;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.InteractiveRequest;
import com.guohuai.settlement.api.response.BankInfoResponse;

@Slf4j
public class BankQueryTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");
		InteractiveRequest interactiveRequest = new InteractiveRequest();
		interactiveRequest.setBankCardNo("6212261001035201111");
		BankInfoResponse bankInfoResponse = sdk.findBankInfoByCard(interactiveRequest);
		log.info("登记订单返回：{}",JSONObject.toJSONString(bankInfoResponse));

	}

}
