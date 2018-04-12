package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: AccountSettlementResponse 
 * @Description: 轧差结算返回参数
 * @author chendonghui
 * @date 2017年6月8日 下午4:55:48 
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountRebateResponse extends BaseResponse {

	private static final long serialVersionUID = 3356030702384518622L;
	
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 返佣总额
	 */
	private BigDecimal rebateBalance;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 来源系统单据号
	 */
	private String orderNo;
	
}
