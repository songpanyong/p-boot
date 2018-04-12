package common.account.sdk.v2;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.PlatformAccountInfoRequest;
import com.guohuai.account.api.request.PlatformReservedAccountDetailRequest;
import com.guohuai.account.api.response.PlatformAccountInfoResponse;
import com.guohuai.account.api.response.PlatformReservedAccountDetailResponse;

@Slf4j
public class QueryPlatformReservedAccountDetailTest {

	public static void main(String[] args) {
		//投资
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
//		AccountSdk sdk=new AccountSdk("http://192.168.223.21");
		
		PlatformReservedAccountDetailRequest req = new PlatformReservedAccountDetailRequest();
		req.setAccountNo("ANPLATFORM00000012");
		long sendTime = System.currentTimeMillis();
		PlatformReservedAccountDetailResponse resp = sdk.platformReservedAccountDetail(req);
		long endTime = System.currentTimeMillis();
		log.info("耗时:"+ (endTime-sendTime));
		log.info(JSONObject.toJSONString(resp));
	}

}
