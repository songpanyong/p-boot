package common.account.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class Dividend2 {

	public static void main(String[] args) {
		//会员账户交易派息
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("117UID2017030200000015");
		req.setUserType("T1");
		req.setOrderType("03");
		req.setRelationProductNo("24b64052fe6948ddbf4f7da13d11ab8b");
		req.setProductType("06");
		req.setBalance(new BigDecimal(0.01));
		req.setRemark("interestTest");
		req.setOrderNo("fb68c3d081734f0cadae33472fbec251");
		req.setSystemSource("mimosa");
		req.setRequestNo(StringUtil.uuid());
		AccountTransResponse resp=sdk.trade(req);
	
		log.info(JSONObject.toJSONString(resp));
	}

}
