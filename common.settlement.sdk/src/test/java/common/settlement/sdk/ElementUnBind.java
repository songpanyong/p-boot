package common.settlement.sdk;

import java.io.FileNotFoundException;

import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.response.ElementValidaResponse;

/**
 * @ClassName: ElementValidationRequest
 * @Description: 四要素验证
 * @author xueyunlong
 * @date 2016年11月7日 下午6:26:39
 *
 */
public class ElementUnBind {
	public static void main(String[] args) throws FileNotFoundException {
//		SettlementSdk sdk = new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
//		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");
		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
		ElementValidationRequest elementValidationRequest = new ElementValidationRequest();
		elementValidationRequest.setSystemSource("mimosa");
		elementValidationRequest.setUserOid("1006");
		elementValidationRequest.setCardNo("6228250038004724471");
//		ElementValidaResponse elementValidaResponse= sdk.unlock(elementValidationRequest);
		ElementValidaResponse elementValidaResponse= sdk.unlockYeePay(elementValidationRequest);
		System.out.println(elementValidaResponse);
	}

}
