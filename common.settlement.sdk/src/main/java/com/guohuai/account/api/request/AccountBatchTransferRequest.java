package com.guohuai.account.api.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.guohuai.account.api.request.entity.AccountOrderDto;

/**
 * @ClassName: AccountBatchTransferRequest 
 * @Description: 批量转账req
 * @author CHENDONGHUI
 * @date 2017年11月14日 上午11:23:34 
 *
 */
@Data
public class AccountBatchTransferRequest implements Serializable{
	
	private static final long serialVersionUID = 2136893610065312335L;
	/**
	 * 请求批次号
	 */
	private String requestNo;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 转账人id
	 */
	private String outputUserOid;
	/**
	 * 订单List
	 */
	private List<AccountOrderDto> orderList;
}
