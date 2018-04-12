package common.account.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.PurchaseTransCancelRequest;
import com.guohuai.account.api.response.PurchaseTransCancelResponse;
import com.guohuai.basic.common.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplyCancel {

	public static void main(String[] args) {
		//会员账户交易申购
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
//		AccountSdk sdk=new AccountSdk("http://139.224.210.164");
		PurchaseTransCancelRequest req = new PurchaseTransCancelRequest();
		req.setUserOid("UID2017060700000001");
		req.setPublisherUserOid("UID2017060700000002");
		req.setRemark("申购废单");
//		req.setOldOrderNo("abaabe99e1244115846e809e23015809");
		req.setOldOrderNo(StringUtil.uuid());
//		req.setSystemSource("mimosa");
//		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab78");
		log.info(System.currentTimeMillis()+"");
		PurchaseTransCancelResponse resp=sdk.tradeCancel(req);
		log.info(System.currentTimeMillis()+"");
		//{"balance":199.99,"orderNo":"122016120100000046","orderType":"01","relationProductNo":"6de6a9cffcb840498f4e0ba120f6e7bc","remark":"invest","requestNo":"b9eeb7762dd744dfb2f98919794bf0e9","systemSource":"mimosa","userOid":"UID2016120100000014","userType":"T1"}
		
		log.info(JSONObject.toJSONString(resp));
	}

}
