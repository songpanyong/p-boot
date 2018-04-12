package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: AccountTransferResponse 
 * @Description: 转账返回参数
 * @author chendonghui
 * @date 2017年11月15日 下午21:49:23 
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountTransferResponse extends BaseResponse {

	private static final long serialVersionUID = 3356030702384518612L;
	
	/**
	 * 转账人id
	 */
	private String outputUserOid;
	/**
	 * 转账账户
	 */
	private String outputAccountNo;
	/**
	 * 转入人id
	 */
	private String inputUserOid;
	/**
	 * 转入账户
	 */
	private String inputAccountNo;
	/**
	 * 请求批次号
	 */
	private String requestNo;
}
