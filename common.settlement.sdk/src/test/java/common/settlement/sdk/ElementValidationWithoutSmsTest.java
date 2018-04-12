package common.settlement.sdk;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.settlement.api.SettlementSdk;
import com.guohuai.settlement.api.request.ElementValidationRequest;
import com.guohuai.settlement.api.response.ElementValidaResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *  四要素验证
 */
@Slf4j
@Data
public class ElementValidationWithoutSmsTest {
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
		SettlementSdk sdk = new SettlementSdk("http://127.0.0.1:8883");
		
		ElementValidationRequest elementValidationRequest = new ElementValidationRequest();
		elementValidationRequest.setSystemSource("后端测试");
		elementValidationRequest.setUserOid("UID2017020600000905");
		String idCardName="云龙";
		String idCard="342623198902173039";
		String bankCardNum = "6222021001115701287";
		String mobile="18252912213";

		elementValidationRequest.setRequestNo(StringUtil.uuid().replaceAll("-",""));
		elementValidationRequest.setRealName(idCardName);
		elementValidationRequest.setCardNo(bankCardNum);
		elementValidationRequest.setPhone(mobile);
		elementValidationRequest.setCertificateNo(idCard);
		elementValidationRequest.setCardOrderId("BA2017020300000111");
		ElementValidaResponse elementValidaResponse= sdk.bindConfirmWithoutSms(elementValidationRequest);
		log.info("elementValidaResponse:{}",JSONObject.toJSON(elementValidaResponse));
	}

}
