package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.CreateUserRequest;
import com.guohuai.account.api.response.CreateUserResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserPhone {

	public static void main(String[] args) {
//		AccountSdk sdk=new AccountSdk("http://172.17.2.124");
//		AccountSdk sdk=new AccountSdk("http://139.224.210.164");
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		CreateUserRequest oreq = new CreateUserRequest();
		oreq.setUserOid("UID2017060900000001");
		oreq.setPhone("13411111111");
		CreateUserResponse orep = sdk.modifyPhone(oreq);
		log.info(JSONObject.toJSONString(orep));


	}
}
