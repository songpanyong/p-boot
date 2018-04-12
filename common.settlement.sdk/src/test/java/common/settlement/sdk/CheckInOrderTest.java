package common.settlement.sdk;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.CheckInAccountOrderRequest;
import com.guohuai.settlement.api.response.BaseResponse;

@Slf4j
public class CheckInOrderTest {

	public static void main(String[] args) {
//		SettlementSdk sdk=new SettlementSdk("http://localhost:8883");
		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");//3384UID2017061400000004
//		SettlementSdk sdk = new SettlementSdk("https://n.kaisawealth.com");//046231faaebc4ec1bc6305280aa90c71
		CheckInAccountOrderRequest checkInAccountOrderRequest = new CheckInAccountOrderRequest();
		checkInAccountOrderRequest.setUserOid("3384UID2017061400000004");
		checkInAccountOrderRequest.setCardNo("6221789454645645646");
		checkInAccountOrderRequest.setChannelNo("10");
		checkInAccountOrderRequest.setFee(new BigDecimal(100));
		checkInAccountOrderRequest.setOrderCreateTime("2017-08-24 11:58:01");
		checkInAccountOrderRequest.setOutOrderTime("2017-08-25 11:58:01");//外部订单时间YYYY-MM-DD hh:mm:ss 
		checkInAccountOrderRequest.setPhone("");
		checkInAccountOrderRequest.setAmount(new BigDecimal(100));
		checkInAccountOrderRequest.setSystemSource("mimosa");
		checkInAccountOrderRequest.setDescribe("充值");
		checkInAccountOrderRequest.setType("01");
		checkInAccountOrderRequest.setPayNo("PAY"+System.currentTimeMillis());
		checkInAccountOrderRequest.setRequestNo(System.currentTimeMillis()+"");
		checkInAccountOrderRequest.setOrderNo("84"+System.currentTimeMillis());
		BaseResponse baseResponse = sdk.checkInAccountOrder(checkInAccountOrderRequest);
		log.info("登记订单返回：{}",JSONObject.toJSONString(baseResponse));

	}

}
