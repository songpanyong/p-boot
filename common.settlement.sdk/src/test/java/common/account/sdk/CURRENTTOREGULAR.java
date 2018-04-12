package common.account.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class CURRENTTOREGULAR {

	public static void main(String[] args) {
		//活转定
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
//		AccountSdk sdk=new AccountSdk("http://139.224.210.164");
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("UID2017021200000001");
		req.setUserType("T1");
		req.setOrderType("52");
		req.setProductType("01");
		req.setRelationProductNo("1001");
		req.setOutputRelationProductNo("1002");
		req.setBalance(new BigDecimal(100));
		req.setRemark("");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab78");
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp=sdk.trade(req);
		log.info(System.currentTimeMillis()+"");
		//{"balance":199.99,"orderNo":"122016120100000046","orderType":"01","relationProductNo":"6de6a9cffcb840498f4e0ba120f6e7bc","remark":"invest","requestNo":"b9eeb7762dd744dfb2f98919794bf0e9","systemSource":"mimosa","userOid":"UID2016120100000014","userType":"T1"}
		
		log.info(JSONObject.toJSONString(resp));
	}

}
