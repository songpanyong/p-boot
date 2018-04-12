package com.guohuai.account.api.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.guohuai.account.api.response.entity.PlatformBaseAccountDto;
import com.guohuai.account.api.response.entity.PlatformReservedAccountDto;

/**
 * @ClassName: PlatformAccountInfoResponse 
* @Description: 查询平台户基本信息返回参数
* @author chendonghui
* @date 2018年2月3日 上午12:15:16 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformAccountInfoResponse extends BaseResponse {
	
	private static final long serialVersionUID = 5919565003350403009L;

	public static final String ACCOUNT_STATUS_STOP = "0";
	public static final String ACCOUNT_STATUS_RUN = "1";
	/**
	 * 平台户ID 
	 */
	private String accountOid;
	/**
	 * 平台户名称 
	 */
	private String accountName;
	/**
	 * 平台户状态 
	 */
	private String accountState;
	/**
	 * 平台户创建时间 
	 */
	private String accountCreateTime;
	/**
	 * 可用余额 
	 */
	private BigDecimal availableBalanceAmount;
	/**
	 * 提现冻结
	 */
	private BigDecimal frozenBalanceAmount;
	/**
	 * 充值总额 
	 */
	private BigDecimal allDepositAmount;
	/**
	 * 提现总额
	 */
	private BigDecimal allWithdrawAmount;
	/**
	 * 转账总额 
	 */
	private BigDecimal allTransferAmount;
	/**
	 * 平台备付金户基本信息
	 */
	private List<PlatformReservedAccountDto> reservedAccountList;
	
}
