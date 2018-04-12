package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeeBfTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
		SettlementSdk sdk=new SettlementSdk("http://114.215.133.84");
		OrderRequest orderRequest=new OrderRequest();
		orderRequest.setUserOid("3384UID2017061400000004");
		orderRequest.setOrderNo(System.currentTimeMillis()+"88");
		orderRequest.setAmount(new BigDecimal(11));
		orderRequest.setRemark("测试赎回");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setType("02");
		orderRequest.setUserType("T2");
		orderRequest.setDescribe("定单描述赎回");
		OrderResponse orderResponse= sdk.payee(orderRequest);
		log.info("orderResponse:{}",JSONObject.toJSON(orderResponse));
		
		System.out.println();
	}

}
