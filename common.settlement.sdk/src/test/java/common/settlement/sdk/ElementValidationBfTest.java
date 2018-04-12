package common.settlement.sdk;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

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
public class ElementValidationBfTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		SettlementSdk sdk = new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		for(int i=25000;i<30000;i++){
		ElementValidationRequest elementValidationRequest = new ElementValidationRequest();
		elementValidationRequest.setSystemSource("mimosa");
		BigDecimal userid=new BigDecimal("19000000000").add(new BigDecimal(i));
		elementValidationRequest.setUserOid(userid.toString()+"");
		String idCardName = "张三"+i;
		String idCard = "120101190"+i;
		BigDecimal bankCardNum=new BigDecimal("6227002118106218181").add(new BigDecimal(i));
		String mobile = userid+"";
		
		//签约代扣协议时用
//		String idCardName = "张三";
//		String idCard = "4455854123465455";
//		String bankCardNum = "62284800512313133";
//		String mobile = "15718122588";
		
		elementValidationRequest.setRealName(idCardName);
		elementValidationRequest.setCardNo(bankCardNum+"");
		elementValidationRequest.setPhone(mobile);
		elementValidationRequest.setCertificateNo(idCard);
		ElementValidaResponse elementValidaResponse= sdk.elementValid(elementValidationRequest);
//		log.info("elementValidaResponse:{}",JSONObject.toJSON(elementValidaResponse));
		System.out.println(i);
		System.out.println(userid);
		
		
//		System.out.println("http://127.0.0.1/settlement/test/pay?userId="+userid+"&type=01");
		}
	}

}
