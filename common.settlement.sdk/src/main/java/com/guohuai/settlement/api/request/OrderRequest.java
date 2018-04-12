package com.guohuai.settlement.api.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: OrderRequest
 * @Description: 支付
 */
@Data
public class OrderRequest implements Serializable {
	private static final long serialVersionUID = -112765746294580721L;

	/**
	 * 会员ID
	 */
	private String userOid;

	/**
	 * 来源系统类型
	 */
	private String systemSource;

	/**
	 * 来源单据编号
	 */
	private String orderNo;

	/**
	 * 交易类别 例如：入款:01; 出款：02:
	 */
	private String type;

	/**
	 * 金额
	 */
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 手续费
	 */
	private BigDecimal fee = BigDecimal.ZERO;

	/**
	 * 请求流水号
	 */
	private String requestNo;

	/**
	 * 支付备注
	 */
	private String remark;

	/**
	 * 订单描述
	 */
	private String describe;

	/**
	 * 收款账户银行开户省代码
	 */
	private String inAcctProvinceCode;

	/**
	 * 收款账户银行开户省名称
	 */
	private String inAcctProvinceName;

	/**
	 * 收款账户开户市
	 * 
	 */
	private String inAcctCityName;

	/**
	 * 短信验证码
	 */
	private String smsCode;

	/**
	 * 下单时间
	 */
	private String orderCreateTime;

	/**
	 * 支付流水号
	 */
	private String payNo;

	/**
	 * 支付方式 mobile ：手机 pc:PC电脑
	 */
	private String paymentMethod;

	/**
	 * 产品描述(网关支付)
	 */
	private String prodInfo;
	
	/**
	 * 产品名称（网关支付）
	 */
	private String prodName;

	/**
	 * 产品详情地址(网关支付)
	 */
	private String prodDetailUrl;

	/**
	 * 用户绑卡的手机号
	 */
	private String phone;

	/**
	 * 用户类型 T1投资人，T2发行人
	 */
	private String userType;

	// --------------------------------------------zby新加字段

	/**
	 * 银行卡编码
	 */
	private String payCode;

	/**
	 * 对公账户名称
	 */
	private String platformName;

	/**
	 * 开户支行
	 */
	private String branch;

	/**
	 * 证件号
	 */
	private String certificateNo;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 用户姓名
	 */
	private String realName;
	
	/**
	 * 支付渠道
	 */
	private String channel;
	
	/**
	 * 卡种：1：借记卡；2：信用卡
	 */
	private String cardType;
	

	/**
	 * 商户号
	 */
	private String merchantId;
}
