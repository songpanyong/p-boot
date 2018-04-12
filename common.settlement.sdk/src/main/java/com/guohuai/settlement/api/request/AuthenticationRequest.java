package com.guohuai.settlement.api.request;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

/**
 * @ClassName: AuthenticationRequest
 * @Description: 签约代扣协议
 */
@Data
public class AuthenticationRequest implements Serializable {
	private static final long serialVersionUID = -3286518029485346965L;

	/**
	 * 会员id
	 */
	private String userOid;

	/**
	 * 请求流水号
	 */
	private String reuqestNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 说明
	 */
	private String note;

	/**
	 * 协议生效日期 格式：yyyyMMdd
	 */
	private Date startDate;

	/**
	 * 协议失效日期 格式：yyyyMMdd
	 */
	private Date endDate;

	/**
	 * 短信验证码 只在确认时候使用
	 */
	private String smsCode;

	/**
	 * 短信流水号 只在确认时候使用
	 */
	private String smsReq;
	/**
	 * 鉴权系统来源 例如：mimosa weiji
	 */
	private String systemSource;
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

}
