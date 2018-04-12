//package common.account.sdk;
//import com.alibaba.fastjson.JSONObject;
//import com.guohuai.account.api.AccountSdk;
//import com.guohuai.account.api.request.TiedCardRequest;
//import com.guohuai.account.api.response.TiedCardResponse;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//public class TiedCard {
////	
//	public static void main(String[] args) {
//		AccountSdk sdk=new AccountSdk("http://115.28.58.108");
////		AccountSdk sdk=new AccountSdk("http://139.129.97.154");
//		TiedCardRequest cardRequest=new TiedCardRequest();
//		cardRequest.setUserOid("UID2017011000000012");
//		cardRequest.setBankCard("6222222222");
//		cardRequest.setIdentityNo("1111222");
//		cardRequest.setPhone("1822");
//		cardRequest.setRealName("学");
////		TiedCardResponse resp=sdk.tiedCard(cardRequest);
//		TiedCardResponse resp=sdk.unLockCard(cardRequest);
//		log.info(JSONObject.toJSONString(resp));
//	}
//
//}
