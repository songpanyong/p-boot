package common.settlement.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.OrderRequest;
import com.guohuai.settlement.api.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayBfTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.97.154");
//		SettlementSdk sdk=new SettlementSdk("http://192.168.100.57");
//		SettlementSdk sdk=new SettlementSdk("http://139.129.250.127");
		OrderRequest orderRequest=new OrderRequest();
//		orderRequest.setUserOid("wangsan");
//		orderRequest.setUserOid("UID2017011000000002");
		orderRequest.setUserOid("UID2017020600000105");
		
		
		orderRequest.setOrderNo(System.currentTimeMillis()+"aa");
		orderRequest.setAmount(new BigDecimal(1));
		orderRequest.setRemark("测试支付");
		orderRequest.setRequestNo(System.currentTimeMillis()+"");
		orderRequest.setSystemSource("mimosa");
		orderRequest.setType("01");
		orderRequest.setDescribe("定单描述");
		long time=System.currentTimeMillis();
		System.out.println(time);
		orderRequest.setOrderCreateTime(DateUtil.format(time, "yyyyMMdd HH:mm:ss"));
		OrderResponse orderResponse= sdk.pay(orderRequest);
		System.out.println(System.currentTimeMillis()-time);
		log.info("orderResponse:{}",JSONObject.toJSON(orderResponse));

	}

}
