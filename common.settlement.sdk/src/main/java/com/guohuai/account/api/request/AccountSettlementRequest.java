package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @ClassName: AccountSettlementRequest 
 * @Description: 账户交易请求参数
 * @author CHENDONGHUI
 * @date 2017年6月8日 下午4:50:11 
 *
 */
@Data
public class AccountSettlementRequest implements Serializable{
	
	private static final long serialVersionUID = 2136893610065389434L;
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 轧差额
	 */
	private BigDecimal nettingBalance;
	/**
	 * 静申购额
	 */
	private BigDecimal applyBalance=BigDecimal.ZERO;
	/**
	 * 赎回额
	 */
	private BigDecimal redeemBalance=BigDecimal.ZERO;
	/**
	 * 用户类型
	 * 投资人账户:T1、发行人账户:T2、平台账户:T3  
	 */
	private String userType; 
	/**
	 * 单据类型
	 */
	private String orderType;
	/**
	 * 交易用途
	 */
	private String remark; 
	/**
	 * 来源系统单据号
	 */
	private String orderNo; 
	/**
	 * 来源系统类型
	 */
	private String systemSource; 
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 定单描述
	 */
	private String orderDesc;
	/**
	 * 业务系统订单创建时间 YYYY-MM-DD HH:mm:ss
	 */
	private String orderCreatTime;
	
}
