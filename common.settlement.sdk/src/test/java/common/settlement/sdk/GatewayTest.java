package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import antlr.ParserSharedInputState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GatewayTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
//		SettlementSdk sdk=new SettlementSdk("http://114.215.133.84");

		OrderRequest orderRequest=new OrderRequest();
		orderRequest.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
		orderRequest.setAmount(new BigDecimal("28")); //单位：分 ; *100;
		orderRequest.setRemark("后端测试支付");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setDescribe("测试先锋网关");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setType("01"); //充值-支付
		orderRequest.setUserType("T1");
		orderRequest.setChannel("19"); //19先锋网关支付
		orderRequest.setProdName("测试-产品hugo1号");
		orderRequest.setProdInfo("这是测试的产品，请留意");
		orderRequest.setCardType("1");
		orderRequest.setOrderNo("84TEST"+System.currentTimeMillis());
		OrderResponse orderResponse= sdk.gateway(orderRequest);
		log.info("先锋网关支付返回："+JSONObject.toJSON(orderResponse));

	}

}
