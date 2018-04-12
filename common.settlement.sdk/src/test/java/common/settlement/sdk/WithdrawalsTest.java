package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WithdrawalsTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.84.101");
//		SettlementSdk sdk = new SettlementSdk("http://106.15.90.69");
//		SettlementSdk sdk = new SettlementSdk("http://192.168.100.57:8883");
		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");

		orderRequest.setAmount(new BigDecimal("1"));
		orderRequest.setRemark("后端测试提现申请");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setDescribe("提现");
		orderRequest.setUserType("T1");
		orderRequest.setType("02");
		orderRequest.setRequestNo("TEST84201709140005");
//		orderRequest.setOrderNo(orderRequest.getRequestNo().substring(20)+5);
		orderRequest.setOrderNo(orderRequest.getRequestNo());
//		orderRequest.setBankCard("6227001215800040195");
		OrderResponse orderResponse = null;
//		orderResponse = sdk.applyWithdrawal(orderRequest);
		log.info("提现申请返回：{}",JSONObject.toJSONString(orderResponse));
		
//		orderRequest.setOrderNo(orderResponse.getOrderNo());
//		orderResponse= sdk.unforzenUserWithdrawal(orderRequest);
		log.info("提现解冻返回：{}",JSONObject.toJSONString(orderResponse));
		
		orderResponse= sdk.confirmWthdrawal(orderRequest);
		log.info("提现确认返回：{}",JSONObject.toJSONString(orderResponse));

//		
		log.info("orderResponse:{}",JSONObject.toJSON(orderResponse));

	}

}
