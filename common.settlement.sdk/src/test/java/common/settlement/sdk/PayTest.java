package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
		SettlementSdk sdk=new SettlementSdk("http://114.215.133.84");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
//		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");
//		SettlementSdk sdk = new SettlementSdk("http://192.168.100.57:8883");

		OrderRequest orderRequest=new OrderRequest();
		orderRequest.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
		orderRequest.setAmount(new BigDecimal("0.01"));
		orderRequest.setRemark("后端测试支付");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setDescribe("支付");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setProdInfo("测试产品003");
		OrderResponse orderResponse= sdk.validPay(orderRequest);
		log.info("预支付返回："+JSONObject.toJSON(orderResponse));
		orderRequest.setType("01");
		orderRequest.setUserType("T1");
		orderRequest.setOrderNo("84TEST"+System.currentTimeMillis());
		orderRequest.setSmsCode("530671");
		orderRequest.setPayNo("84012017102400000001");
		OrderResponse orderResponse2= sdk.pay(orderRequest);
		log.info("确认支付返回："+JSONObject.toJSON(orderResponse2));

	}

}
