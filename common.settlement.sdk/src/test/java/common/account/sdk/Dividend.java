package common.account.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class Dividend {

	public static void main(String[] args) {
		//会员账户交易派息
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
		
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("UID2017021200000001");
		req.setUserType("T1");
		req.setOrderType("03");
		req.setRelationProductNo("0001");
		req.setProductType("01");
		req.setBalance(new BigDecimal(1));
		req.setRemark("派息");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab78");
		AccountTransResponse resp=sdk.trade(req);
	
		log.info(JSONObject.toJSONString(resp));
	}

}