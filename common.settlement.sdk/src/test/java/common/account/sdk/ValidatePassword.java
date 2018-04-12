package common.account.sdk;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.ValidatePasswordRequest;
import com.guohuai.account.api.response.ValidatePasswordResponse;

@Slf4j
public class ValidatePassword {

	public static void main(String[] args) {
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
		
		ValidatePasswordRequest req = new ValidatePasswordRequest();
		ValidatePasswordResponse resp = new ValidatePasswordResponse();
		req.setUserOid("UID2016112100000017");
		req.setPassword("123123");
		resp = sdk.validatePassword(req);
		log.info(JSONObject.toJSONString(resp));

	}

}
