package common.account.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

@Slf4j
public class PublisherTrans {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//发行人放款
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("UID2017060600000006");
		req.setPublisherUserOid("UID2017060600000006");
		req.setUserType("T2");
		req.setOrderType("57");
		req.setProductType("");
		
		req.setBalance(new BigDecimal(100));
		req.setVoucher(BigDecimal.ZERO);
		req.setFee(BigDecimal.ZERO);
		req.setRemark("发行人放款");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo(StringUtil.uuid());
		req.setOrderDesc("orderdesc");
		
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp=sdk.publisherTrans(req);
		log.info(System.currentTimeMillis()+"");
		
		
		//发行人收款
		req.setUserOid("UID2017060600000006");
		req.setPublisherUserOid("UID2017060600000006");
		req.setUserType("T2");
		req.setOrderType("58");
		req.setProductType("");
		
		req.setBalance(new BigDecimal(100));
		req.setVoucher(BigDecimal.ZERO);
		req.setFee(BigDecimal.ZERO);
		req.setRemark("发行人收款");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo(StringUtil.uuid());
		req.setOrderDesc("orderdesc");
		
		log.info(System.currentTimeMillis()+"");
		AccountTransResponse resp2=sdk.publisherTrans(req);
		log.info(System.currentTimeMillis()+"");
		
		log.info(JSONObject.toJSONString(resp2));
	}

}
