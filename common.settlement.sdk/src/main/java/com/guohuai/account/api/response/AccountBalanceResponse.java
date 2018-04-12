package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询账户余额返回参数
* @ClassName: AccountBalanceResponse 
* @Description: 
* @author chendonghui
* @date 2017年3月30日 上午11:35:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountBalanceResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 3356030702384518601L;
	
	/**
	 * 投资人基本账户余额
	 */
	private BigDecimal balance;
	
	/**
	 * 投资人提现冻结账户余额
	 */
	private BigDecimal withdrawFrozenBalance;
	
	/**
	 * 投资人充值冻结账户余额
	 */
	private BigDecimal rechargeFrozenBalance;
	
	/**
	 * 投资人账户申购可用余额
	 */
	private BigDecimal applyAvailableBalance;
	
	/**
	 * 投资者账户提现可用余额
	 */
	private BigDecimal withdrawAvailableBalance;
	
	/**
	 * 续投账户余额
	 */
	private BigDecimal continuedInvestmentFrozenBalance;
	
}
