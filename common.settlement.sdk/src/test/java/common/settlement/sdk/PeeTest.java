package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeeTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
//		SettlementSdk sdk=new SettlementSdk("http://114.215.133.84");

		OrderRequest orderRequest=new OrderRequest();
		orderRequest.setUserOid("111111111111111");
		orderRequest.setAmount(new BigDecimal("5"));
		orderRequest.setRemark("后端测试放款还款");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setDescribe("放款");
		orderRequest.setOrderCreateTime("2017-09-21 15:34:01");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setType("02");
		orderRequest.setOrderNo("84TEST"+System.currentTimeMillis());
		
		orderRequest.setPhone("18639189001");
		orderRequest.setCertificateNo("411025199011054121");
		orderRequest.setBankCard("6204103232323322323");
		orderRequest.setRealName("陈东辉");
		OrderResponse orderResponse= sdk.loanTrade(orderRequest);
		log.info("支付返回："+JSONObject.toJSON(orderResponse));
	}
}
