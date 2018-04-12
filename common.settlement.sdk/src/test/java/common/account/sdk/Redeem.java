package common.account.sdk;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;

@Slf4j
public class Redeem {

	public static void main(String[] args) {
		//会员账户交易赎回
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		
		//赎回申请、补单、撤单公共参数
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("UID2017060600000007");
		req.setUserType("T1");
		req.setOrderType("02");
		req.setSystemSource("mimosa");
		req.setOrderNo("15ae9ce6c33145039363dffa2dbc7215");
//
//		
//		//赎回申请、补单
//		req.setRelationProductNo("0003");
//		req.setBalance(new BigDecimal(10));
//		req.setProductType("01");
//	    req.setRemark("赎回活期1001");
////		req.setRemark("补单");
////		req.setOrderNo(StringUtil.uuid());
//		req.setOrderCreatTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));//赎回需要传订单创建时间
//		req.setRequestNo("1563fe65e0dd44b8939caa172f29ab15");
//		AccountTransResponse resp=sdk.trade(req);
		
//		AccountTransResponse resp=sdk.redeemSupply(req);
		
		//赎回撤单
		req.setRemark("撤单");
		AccountTransResponse resp=sdk.redeemCancel(req);
		
		//批量赎回确认
//		List<CreateOrderRequest> reqOrderList = new ArrayList<CreateOrderRequest>();
//		CreateOrderRequest req1 = new CreateOrderRequest();
//		req1.setOrderNo("d4ae9ce6c33145039363dffa2dbc72d3");
//		req1.setUserOid("UID2017060700000002");
//		req1.setOrderType("02");
//		req1.setRemark("赎回确认");
//		reqOrderList.add(req1);
//		CreateOrderRequest req2 = new CreateOrderRequest();
//		req2.setOrderNo("15ae9ce6c33145039363dffa2dbc7215");
//		req2.setUserOid("UID2017060600000007");
//		req2.setOrderType("02");
//		req2.setRemark("赎回确认");
//		reqOrderList.add(req2);
//		AccountTransResponse resp=sdk.redeemConfirm(reqOrderList);
		
		log.info(JSONObject.toJSONString(resp));
	}

}
