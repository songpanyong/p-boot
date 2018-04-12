package common.account.sdk.v2;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.PlatformAccountInfoRequest;
import com.guohuai.account.api.response.PlatformAccountInfoResponse;

@Slf4j
public class QueryPlatformAccountInfoTest {

	public static void main(String[] args) {
		//投资
//		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		AccountSdk sdk=new AccountSdk("http://192.168.223.21");
		
		PlatformAccountInfoRequest req = new PlatformAccountInfoRequest();
		req.setUserOid("platform201800000001");
		long sendTime = System.currentTimeMillis();
		PlatformAccountInfoResponse resp = sdk.platformAccountInfo(req);
		long endTime = System.currentTimeMillis();
		log.info("耗时:"+ (endTime-sendTime));
		log.info(JSONObject.toJSONString(resp));
	}

}
