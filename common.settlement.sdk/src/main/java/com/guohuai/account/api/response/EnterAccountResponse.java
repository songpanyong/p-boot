package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台、投资人入账返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EnterAccountResponse extends BaseResponse {

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -2890796406944122702L;
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
	 * 来源系统单据号
	 */
	private String orderNo; 
	
}
