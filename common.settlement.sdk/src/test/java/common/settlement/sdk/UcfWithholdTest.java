package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UcfWithholdTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
//		SettlementSdk sdk=new SettlementSdk("http://114.215.133.84");

		OrderRequest orderRequest=new OrderRequest();
		orderRequest.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
		orderRequest.setAmount(new BigDecimal("0.1"));
		orderRequest.setRemark("后端测试支付");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setDescribe("测试先锋代扣");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setType("01");
		orderRequest.setUserType("T1");
		orderRequest.setOrderNo("84TEST"+System.currentTimeMillis());
		OrderResponse orderResponse= sdk.ucfWithhold(orderRequest);
		log.info("先锋代扣返回："+JSONObject.toJSON(orderResponse));

	}

}
