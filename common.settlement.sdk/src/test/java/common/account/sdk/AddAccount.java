package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.CreateAccountRequest;
import com.guohuai.account.api.response.CreateAccountResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddAccount {

	public static void main(String[] args) {
//		AccountSdk sdk=new AccountSdk("http://172.17.2.124");
//		AccountSdk sdk=new AccountSdk("http://115.28.58.108");
//		AccountSdk sdk=new AccountSdk("http://139.224.210.164");
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		CreateAccountRequest oreq = new CreateAccountRequest();
		oreq.setUserOid("UID2017021200000002");
		oreq.setUserType("T2");
		oreq.setAccountType("02");
		oreq.setRelationProduct("1001");
		
		CreateAccountResponse orep  = sdk.createAccount(oreq);
		
		log.info(JSONObject.toJSONString(orep));


	}

}
