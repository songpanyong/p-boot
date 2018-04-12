package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询账户余额统计返回参数
* @ClassName: AccountBalanceStatisticalResponse 
* @Description: 
* @author chendonghui
* @date 2017年10月20日 上午11:48:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountBalanceStatisticalResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 3356030202384518601L;
	
	/**
	 * 用户资金总余额
	 */
	private BigDecimal userTotalBalance;
	
	/**
	 * 平台商户号总余额
	 */
	private BigDecimal merchantBalance;
	
}
