package common.settlement.sdk;

import com.guohuai.settlement.api.SettlementSdk;

public class SettlementSdkUtil {

	public static String host7 = "http://139.224.233.7";
	public static String host215 = "http://118.190.115.215";
	public static String host159 = "http://demo.guohuaitech.com:58888";
	public static String host = "http://127.0.0.1:8883";
	public static String host84 = "http://114.215.133.84";
	public static SettlementSdk getSettlementSdk() {
		return new SettlementSdk(host);
	}
}
