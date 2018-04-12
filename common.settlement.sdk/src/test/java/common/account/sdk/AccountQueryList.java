package common.account.sdk;



import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.CreateUserRequest;
import com.guohuai.account.api.response.CreateUserResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountQueryList {

	public static void main(String[] args) {
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
		
		
//		AccountQueryRequest req=new AccountQueryRequest();
//		req.setUserOid("UID2016112200000007");
//		req.setUserType("T3");
//		AccountListResponse listResponse=sdk.accountQueryList(req);
//		log.info(JSONObject.toJSONString(listResponse));
		
		CreateUserRequest req = new CreateUserRequest();
		
		CreateUserResponse resp = new CreateUserResponse();
		req.setRemark("创建发行人");
		req.setSystemUid("8aad680858bd61bc0158bd77889d0040");
		req.setUserType("T2");
		req.setSystemSource("mimosa");
		resp = sdk.addUser(req);
		log.info(JSONObject.toJSONString(resp));
		
		//"remark":"创建发行人","systemSource":"mimosa","systemUid":"8aad680858bd61bc0158bd77889d0039","userType":"T2"}

	}

}
