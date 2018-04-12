package common.account.sdk;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.account.api.AccountSdk;
import com.guohuai.account.api.request.AccountTransRequest;
import com.guohuai.account.api.response.AccountTransResponse;
import com.guohuai.basic.common.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountContinuTest {
	
//	@Test
//	public void accountContinuedInvest(){
//		
//	}
	
	public static void main(String[] args) {
		AccountSdk sdk=new AccountSdk("http://127.0.0.1:8883");
		AccountTransRequest req = new AccountTransRequest();
		req.setUserOid("bedd429e1e94414d976699f074620458");
		req.setPublisherUserOid("3384UID2017061400000004");
		req.setUserType("T1");
		req.setOrderType("02"); //02赎回、01申购
		req.setProductType("01");
		req.setRelationProductNo("0001");
		req.setBalance(new BigDecimal(410));
		req.setFrozenBalance(new BigDecimal(400));
		req.setVoucher(new BigDecimal(6));
		req.setRemark("T1");
		String oldOrderNo = StringUtil.uuid();
		log.info("================活转定- 赎回 orderNo={}==============", oldOrderNo);
		req.setOrderNo(oldOrderNo);
		req.setSystemSource("mimosa");
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab00");
//		log.info(System.currentTimeMillis()+"---------------begin-----");
//		AccountTransResponse resp=sdk.redeem(req);
//		log.info(System.currentTimeMillis()+"---------------end-----");
		
		
		req.setRequestNo("9063fe65e0dd44b8939caa172f29ab99");
		req.setOldOrderNo("1b7f441c14684538a426684152291152");
		req.setBalance(new BigDecimal(400));
		req.setOrderType("01"); //02赎回、01申购
		log.info("================活转定- 申购 orderNo={}==============", oldOrderNo);
		log.info(System.currentTimeMillis()+"---------------begin-----");
		AccountTransResponse resp=sdk.purchase(req);
		log.info(System.currentTimeMillis()+"---------------end-----");
		
		
		log.info(JSONObject.toJSONString(resp));
	}
	
}
