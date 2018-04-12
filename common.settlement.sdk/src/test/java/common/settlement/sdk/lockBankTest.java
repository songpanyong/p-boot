package common.settlement.sdk;

import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.response.ElementValidaResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class lockBankTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		SettlementSdk sdk=new SettlementSdk("http://192.168.100.57");
		ElementValidationRequest orderRequest=new ElementValidationRequest();
		orderRequest.setUserOid("UID2017011400000001");
		orderRequest.setCardNo("6217000010069428764");
		orderRequest.setCertificateNo("22240119910504183x");
		orderRequest.setRealName("江健民");
		orderRequest.setPhone("15604330965");
		ElementValidaResponse orderResponse= sdk.elementValid(orderRequest);

		log.info("ElementValidationRequest:{}",orderResponse);
		log.info("ErrorMessage:{}",orderResponse.getErrorMessage());
//		String a="222A";
//		System.out.println(a.toLowerCase());
//		System.out.println(a.toUpperCase());
	}

}
