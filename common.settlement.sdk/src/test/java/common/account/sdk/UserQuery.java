package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.UserQueryRequest;
import com.guohuai.account.api.response.UserListResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserQuery {

	public static void main(String[] args) {
		AccountSdk sdk=new AccountSdk("http://115.28.58.108");
		UserQueryRequest req=new UserQueryRequest();
		req.setSystemSource("mimosa");
		req.setUserType("T3");
		UserListResponse listResponse=sdk.userQueryList(req);
		log.info(JSONObject.toJSONString(listResponse));

	}

}
