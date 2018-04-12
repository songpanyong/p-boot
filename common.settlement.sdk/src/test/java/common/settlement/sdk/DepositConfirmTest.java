package common.settlement.sdk;

import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.DepositConfirmRequest;
import com.guohuai.settlement.api.response.DepositConfirmResponse;

/**
 * 单笔充值实时对账
 *
 */

public class DepositConfirmTest {

	public static void main(String[] args) {
		SettlementSdk sdk=new SettlementSdk("http://127.0.0.1:8883");
		DepositConfirmRequest req = new DepositConfirmRequest();
		req.setOrderNo("841012017070300000003");
		req.setTradeType("01");
		req.setUserOid("3384UID2017061400000008");
		DepositConfirmResponse response = sdk.depositConfirm(req);
		System.out.println(response.getReturnCode());
	}

}
