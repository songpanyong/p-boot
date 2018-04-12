package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 平台、投资人入账请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class EnterAccountRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -5130441578637015662L;
	/**
	 * 入账账户
	 */
	private String inputAccountNo;
	/**
	 * 交易额
	 */
	private BigDecimal balance;	
	/**
	 * 请求流水号
	 */
	private String requestNo;
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
	 * 业务系统订单创建时间
	 */
//	private String orderCreatTime;
}
