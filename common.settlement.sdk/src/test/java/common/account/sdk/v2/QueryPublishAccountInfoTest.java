package common.account.sdk.v2;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.PlatformAccountInfoRequest;
import com.guohuai.account.api.request.PublisherAccountInfoRequest;
import com.guohuai.account.api.response.PlatformAccountInfoResponse;
import com.guohuai.account.api.response.PublisherAccountInfoResponse;

@Slf4j
public class QueryPublishAccountInfoTest {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
//		AccountSdk sdk=new AccountSdk("http://192.168.223.21");
		
		PublisherAccountInfoRequest req = new PublisherAccountInfoRequest();
		req.setUserOid("1516951579037");
//		req.setProductAccountNo("1AN2018012600000004");
		long sendTime = System.currentTimeMillis();
		PublisherAccountInfoResponse resp = sdk.publisherAccountInfo(req);
		long endTime = System.currentTimeMillis();
		log.info("耗时:"+ (endTime-sendTime));
		log.info(JSONObject.toJSONString(resp));
	}

}
