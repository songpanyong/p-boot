package common.account.sdk;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.response.AccountReconciliationDataResponse;
import com.guohuai.settlement.api.request.OrderAccountRequest;

@Slf4j
public class GetReconlictionDataTest {

	public static void main(String[] args) {
		AccountSdk sdk=new AccountSdk("http://localhost:8883");
//		AccountSdk sdk=new AccountSdk("http://114.215.133.84");
		OrderAccountRequest req = new OrderAccountRequest();
		req.setBeginTime("2017-09-01 00:00:00");
		req.setEndTime("2017-09-02 23:59:59");
		AccountReconciliationDataResponse resp = sdk.getAccountAlreadyReconciliationData(req);
		log.info("确认支付返回："+JSONObject.toJSON(resp));

	}

}
