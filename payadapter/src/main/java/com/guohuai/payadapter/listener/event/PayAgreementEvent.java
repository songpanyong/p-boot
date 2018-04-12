package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PayAgreementEvent extends AbsEvent {

	private static final long serialVersionUID = -3837316389382385469L;
	/**
	 * 身份证号
	 */
	private String identityNo;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 客户姓名
	 */
	private String userName;
	/**
	 * 用户预留手机号
	 */
	private String mobileNum;
	
	/**
	 * 银行类型
	 */
	private String bankType;
	
	private String orderNo;//订单号
	private String merchantId;//商户身份
	private String effectiveDate;//协议生效日期
	private String expirationDate;//协议失效日期
	/**
	 * 短信流水号
	 */
	private String smsSeq;
	/**
	 * 返回状态
	 */
	private String status;
	
	/**
	 * 请求流水号
	 */
	private String rquestNo;
	
	/**
	 * 协议号
	 */
	private String treatyId;
	
	/**
	 * 商户与用户协议号
	 */
	private String merchantTreatyNo;
	
	/**
	 * 费项代码(目前04903消费贷款99920投资理财)
	 */
	private String paymentItem;
	
	
	/**
	 * 短信码
	 */
	private String smsCode;
	
}
