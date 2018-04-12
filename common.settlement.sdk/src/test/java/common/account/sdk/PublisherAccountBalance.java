package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.PublisherAccountQueryRequest;
import com.guohuai.account.api.response.PublisherAccountBalanceResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherAccountBalance {

	public static void main(String[] args) {

		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		PublisherAccountQueryRequest oreq = new PublisherAccountQueryRequest();
		oreq.setUserOid("UID2017060900000001");//3384UID2017061400000006
//		oreq.setAccountType("12");

		PublisherAccountBalanceResponse orep = sdk.getPublisherAccountBalanceByUserOid(oreq);
		log.info(JSONObject.toJSONString(orep));


	}

}
