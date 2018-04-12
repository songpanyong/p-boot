package common.account.sdk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.TransPublishRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class Publish {

	public static void main(String[] args) {
		//会员账户交易增加发行额
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8881");
		
		
		TransPublishRequest req = new TransPublishRequest();
		List<TransPublishRequest> reqList = new ArrayList<TransPublishRequest>();
		req.setOrderType("06");
		req.setRelationProductNo("0001");
		req.setBalance(new BigDecimal(100));
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab78");
		req.setAccountNo("AN2017021200000002");
		reqList.add(req);
		AccountTransResponse resp=sdk.tradepublish(reqList);
		log.info(JSONObject.toJSONString(resp));
	}

}
