package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.BankChannelRequest;
import com.guohuai.settlement.api.response.BankChannelResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		BankChannelRequest bankChannelRequest = new BankChannelRequest();
		BankChannelResponse sBankChannelResponse = sdk.queryPaymentChannel(bankChannelRequest);
		log.info(JSONObject.toJSONString(sBankChannelResponse));
	}

}
