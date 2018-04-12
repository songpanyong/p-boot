package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.response.AccountBalanceStatisticalResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountBalanceStatisticalTest {

	public static void main(String[] args) {

		AccountSdk sdk = new AccountSdk("http://127.0.0.1:8883");
		// AccountSdk sdk = new AccountSdk("http://114.215.133.84");
		AccountBalanceStatisticalResponse resp = sdk.accountBalanceStatistical();
		log.info(JSONObject.toJSONString(resp));
	}
}