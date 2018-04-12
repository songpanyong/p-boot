package common.settlement.sdk;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountBatchRedeemRequest;
import com.guohuai.account.api.request.entity.AccountOrderDto;
import com.guohuai.account.api.response.AccountBalanceResponse;
import com.guohuai.account.api.response.AccountBatchRedeemResponse;
import com.guohuai.settlement.api.request.InteractiveRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedeemTest {

	public static void main(String[] args) {
		
		AccountSdk sdk=new AccountSdk("http://localhost:8883");
//		AccountSdk sdk = new AccountSdk("http://114.215.133.84");
		//测试轧差结算
//		AccountSettlementRequest req=new AccountSettlementRequest();
//		req.setNettingBalance(new BigDecimal(1000));
//		req.setRedeemBalance(new BigDecimal(150));
//		req.setPublisherUserOid("3384UID2017061400000006");
//		req.setOrderCreatTime("2017-06-21 10:48:00");
//		req.setOrderDesc("");
//		req.setOrderNo("84"+System.currentTimeMillis());
//		req.setOrderType("08");
//		req.setRemark("");
//		req.setRequestNo("84"+System.currentTimeMillis());
//		req.setSystemSource("mimosa");
//		req.setUserType("T2");
//		log.info("orderrequest:{}",JSONObject.toJSON(req));
//		AccountSettlementResponse res= sdk.nettingSettlement(req);
//		log.info("orderResponse:{}",JSONObject.toJSON(res));
		//测试批量赎回
		AccountBatchRedeemRequest req = new AccountBatchRedeemRequest();
		req.setPublisherUserOid("3384UID2017061400000004");
		req.setRequestNo("test000000000025");
		req.setSystemSource("mimosa");
		List<AccountOrderDto> orderList = new ArrayList<AccountOrderDto>();
		for (int i = 0; i < 5; i++) {
			AccountOrderDto dto = new AccountOrderDto();
			dto.setOrderNo(req.getRequestNo()+i);
			dto.setBalance(new BigDecimal(i));
//			dto.setFee(new BigDecimal(2));
			dto.setOrderDesc("");
			dto.setOrderType("02");
			dto.setRemark("");
			dto.setUserOid("UID2017060700000001");
//			dto.setUserOid("42a6708bc88e4eb3af62e8ad1d8e352e");
			dto.setVoucher(new BigDecimal(0));
			dto.setSubmitTime("2017-06-14 10:11:00");
			orderList.add(dto);
			req.setOrderList(orderList);
		}
		log.info("AccountBatchRedeemResponse:{}",JSONObject.toJSON(req));
		AccountBatchRedeemResponse res = sdk.batchRedeem(req);
		log.info("AccountBatchRedeemResponse:{}",JSONObject.toJSON(res));
		
		InteractiveRequest interactiveRequest = new InteractiveRequest();
		interactiveRequest.setUserOid("UID2017060700000001");
		AccountBalanceResponse accountBalanceResponse = sdk.getAccountBalanceByUserOid(interactiveRequest);
		log.info("accountBalanceResponse:{}",JSONObject.toJSON(accountBalanceResponse));
		
	}

}
