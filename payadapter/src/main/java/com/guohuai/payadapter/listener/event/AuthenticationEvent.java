package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @ClassName: AuthenticationEvent
 * @Description: 四要素验证（鉴权）
 * @author xueyunlong
 * @date 2016年11月8日 下午6:25:50
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthenticationEvent extends AbsEvent {

	private static final long serialVersionUID = -3837316389382385469L;
	/**
	 * 用户id
	 * 先锋支付
	 */
	private String userOid;
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
	 * 客户号
	 */
	private String custId;
	
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 绑卡编-金运通
	 */
	private String bindOrderId;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 银行编号
	 */
	private String bankCode;
	
	/**
	 * 绑卡标识号-宝付，区别于绑卡编号
	 */
	private String bindId;
	
	/**
	 * 交易流水号
	 */
	private String transNo;
}
