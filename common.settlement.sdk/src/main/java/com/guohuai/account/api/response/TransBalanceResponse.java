package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改余额返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TransBalanceResponse extends BaseResponse {/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 3356030702384518601L;
	
	/**
	 * 投资人账户，转出账户 交易后余额
	 */
	private BigDecimal balance;
	
	/**
	 * 发行人，平台账户，转入账户  交易后余额
	 */
	private BigDecimal otherBalance;	
	
}
