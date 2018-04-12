package common.settlement.sdk;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.response.ElementValidaResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ElementValidationRequest
 * @Description: 四要素验证
 * @author xueyunlong
 * @date 2016年11月7日 下午6:26:39
 *
 */
@Slf4j
public class ElementValidationTest2 {
	public static void main(String[] args) throws FileNotFoundException {
//		FileOutputStream aa = new FileOutputStream("d:/test.txt");
//		  PrintStream bb = new PrintStream(aa);
//		  if (bb != null)
//		 System.setOut(bb);
//		SettlementSdk sdk = new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
//		SettlementSdk sdk=new SettlementSdk("http://192.168.100.57");
		ElementValidationRequest elementValidationRequest = new ElementValidationRequest();
		elementValidationRequest.setSystemSource("mimosa");
		int i=27;
		BigDecimal userid=new BigDecimal("29000000000").add(new BigDecimal(i));
		elementValidationRequest.setUserOid(userid.toString()+"");
		String idCardName = "张三"+i;
		String idCard = "120101190"+i;
		BigDecimal bankCardNum=new BigDecimal("6227002118106218183").add(new BigDecimal(i));
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
		log.info("elementValidaResponse:{}",JSONObject.toJSON(elementValidaResponse));
		System.out.println(i);
		System.out.println(userid);
		
		
		}

}
