package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: AccountBatchRedeemResponse 
 * @Description: 批量赎回返回参数
 * @author chendonghui
 * @date 2017年6月13日 上午10:45:44 
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountBatchRedeemResponse extends BaseResponse {

	private static final long serialVersionUID = 3356030702384518612L;
	
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 请求批次号
	 */
	private String requestNo;
}
