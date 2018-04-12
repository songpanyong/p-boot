package common.settlement.sdk;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.UserProtocolRequest;
import com.guohuai.settlement.api.response.UserProtocolResponse;

@Slf4j
public class QueryBindCardInfoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		SettlementSdk sdk=new SettlementSdk("http://192.168.223.21:8883");
		UserProtocolRequest userProtocolRequest = new UserProtocolRequest();
		userProtocolRequest.setUserOid("platform201800000001");//efe7f83934184d2ea86543e08a0eea5d
		UserProtocolResponse resp = sdk.bindCardInfo(userProtocolRequest);
		log.info(JSONObject.toJSONString(resp));
	}

}
