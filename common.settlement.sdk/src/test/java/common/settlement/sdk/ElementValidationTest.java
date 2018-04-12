package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.response.ElementValidaResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ElementValidationRequest
 * @Description: 四要素验证
 * @author xueyunlong
 * @date 2016年11月7日 下午6:26:39
 *
 */
@Slf4j
@Data
public class ElementValidationTest {
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 鉴权系统来源 例如：mimosa weiji
	 */
	private String systemSource;
	/**
	 * 请求流水号 表明一次请求，不允许重复 建议使用当前日期+交易唯一标示 例如 20161107183459000000001
	 */
	private String reuqestNo;
	/**
	 * 银行行别 如:
	 */
	private String bankCode;
	/**
	 * 客户姓名
	 */
	private String realName;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 证件号码
	 */
	private String certificateNo;
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 业务标签
	 */
	private String busiTag;

	public static void main(String[] args) {
//		SettlementSdk sdk = new SettlementSdk("http://115.28.58.108");
		SettlementSdk sdk = new SettlementSdk("http://127.0.0.1:8883");
//		SettlementSdk sdk = new SettlementSdk("http://114.215.133.84");
//		SettlementSdk sdk = new SettlementSdk("http://139.224.210.164");
//		SettlementSdk sdk=new SettlementSdk("http://115.28.58.108");
//		SettlementSdk sdk = new SettlementSdk("http://139.129.250.127");
		
		ElementValidationRequest elementValidationRequest = new ElementValidationRequest();
		elementValidationRequest.setSystemSource("后端测试");
		elementValidationRequest.setUserOid("827f8b0b24ee4e7d8dbf3780d778996d");
//		String idCardName = "王武";
		String idCardName="陈东辉";
//		String idCard = "420621199012133824";
		String idCard="411025199011035012";
//		String bankCardNum = "6222021001115704287";
		String bankCardNum="6212261001035206842";//
//		String mobile = "17316381220";
		String mobile="18639189002";
		
		
		//签约代扣协议时用
//		String idCardName = "张三";
//		String idCard = "4455854123465455";
//		String bankCardNum = "62284800512313133";
//		String mobile = "15718122588";
		
		elementValidationRequest.setRealName(idCardName);
		elementValidationRequest.setCardNo(bankCardNum);
		elementValidationRequest.setPhone(mobile);
		elementValidationRequest.setCertificateNo(idCard);
		elementValidationRequest.setCardOrderId("0001BA2017112100000001");
//		elementValidationRequest.setSmsCode("535469");
		ElementValidaResponse elementValidaResponse= sdk.bindApply(elementValidationRequest);
//		ElementValidaResponse elementValidaResponse= sdk.bindConfirm(elementValidationRequest);
//		ElementValidaResponse elementValidaResponse= sdk.unlock(elementValidationRequest);
		log.info("elementValidaResponse:{}",JSONObject.toJSON(elementValidaResponse));
	}

}
