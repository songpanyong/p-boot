package common.account.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class REGULARTOCURRENT {

	public static void main(String[] args) {
		//定转活
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
//		AccountSdk sdk=new AccountSdk("http://139.224.210.164");
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("UID2017021200000001");
		req.setUserType("T1");
		req.setOrderType("53");
		req.setProductType("06");
		req.setRelationProductNo("1002");
		req.setOutputRelationProductNo("1001");
		req.setBalance(new BigDecimal(100));
		req.setRemark("");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab78");
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp=sdk.trade(req);
		log.info(System.currentTimeMillis()+"");
		
		log.info(JSONObject.toJSONString(resp));
	}

}
