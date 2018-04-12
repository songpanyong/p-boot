package common.account.sdk;

import com.guohuai.account.api.AccountSdk;

import common.settlement.sdk.SettlementSdkUtil;

public class AccountSdkUtil {
	
	String host7="http://139.224.233.7";
public static AccountSdk getAccountSdk(){
	return new AccountSdk(SettlementSdkUtil.host);
}
}
