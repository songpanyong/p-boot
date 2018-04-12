package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.AuthenticationRequest;
import com.guohuai.settlement.api.response.AuthenticationResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationApp {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
		AuthenticationRequest authenticationRequest=new AuthenticationRequest();
		authenticationRequest.setUserOid("0004");
		authenticationRequest.setOrderNo(System.currentTimeMillis()+"");
		authenticationRequest.setCardNo("6227000320012225899");
		authenticationRequest.setCertificateNo("110102198505110855");
		authenticationRequest.setPhone("13521878700");
		authenticationRequest.setRealName("王大利");
		AuthenticationResponse authenticationResponse= sdk.applyAgreement(authenticationRequest);
		log.info("authenticationResponse:{}",JSONObject.toJSON(authenticationResponse));

	}

}
