package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.AuthenticationRequest;
import com.guohuai.settlement.api.response.AuthenticationResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationConfrim {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8080");
		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
		AuthenticationRequest authenticationRequest=new AuthenticationRequest();
		authenticationRequest.setUserOid("0004");
		authenticationRequest.setOrderNo("SEL2016120500000065");
		AuthenticationResponse authenticationResponse= sdk.confirmAgreement(authenticationRequest);
		log.info("authenticationResponse:{}",JSONObject.toJSON(authenticationResponse));
	}

}
