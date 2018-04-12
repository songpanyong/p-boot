package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.request.FindBindRequest;
import com.guohuai.settlement.api.response.FindBindResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnterpriseWithdrawalsTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		ElementValidationRequest req = new ElementValidationRequest();
		FindBindRequest findBindRequest = new FindBindRequest();
		findBindRequest.setUserOid("3384UID2017061400000006");
		req.setCardNo("6228280038869327079");
		req.setUserOid("3384UID2017061400000006");
		/*req.setCertificateNo("42102119941211924X");
		req.setProvince("湖南");
		req.setCity("郴州");
		req.setBranch("贵阳支行");
		req.setCardType("企业");
		req.setBankName("农业银行");
		req.setRealName("开发");*/
		//企业绑卡
		//ElementValidaResponse orderResponse = sdk.bindCard(req);
		FindBindResponse orderResponse = sdk.findBindCard(findBindRequest);
//		log.info("企业绑卡返回：{}",JSONObject.toJSONString(orderResponse));
		//log.info("企业解绑返回：{}",JSONObject.toJSONString(orderResponse));
		log.info("企业查询银行卡返回：{}",JSONObject.toJSONString(orderResponse));
		
	}
}
