package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferTest {

	public static void main(String[] args) {
		
//		AccountSdk sdk=new AccountSdk("http://localhost:8080");
		AccountSdk sdk = new AccountSdk("http://114.215.133.84");
		//转账
		AccountTransRequest req=new AccountTransRequest();
		req.setBalance(new BigDecimal(10));
		req.setUserOid("18221013685");
		req.setPublisherUserOid("3384UID2017061400000006");
		req.setUserType("T1");
		req.setOrderType("01");
		req.setBalance(new BigDecimal(100));
		req.setVoucher(new BigDecimal(10));
		req.setRemark("申购");
		req.setOrderNo(StringUtil.uuid());
		req.setSystemSource("mimosa");
		req.setRequestNo("1063fe65e0dd44b8939caa172f29ab78");
		log.info("tranferRequest:{}",JSONObject.toJSON(req));
//		AccountTransResponse res= sdk.transfer(req);
//		log.info("tranferResponse:{}",JSONObject.toJSON(res));
		
	}

}
