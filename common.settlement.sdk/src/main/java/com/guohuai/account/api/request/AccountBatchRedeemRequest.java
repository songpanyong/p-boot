package com.guohuai.account.api.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.guohuai.account.api.request.entity.AccountOrderDto;

/**
 * @ClassName: AccountBatchRedeemRequest 
 * @Description: 批量赎回req
 * @author CHENDONGHUI
 * @date 2017年6月13日 上午9:20:15 
 *
 */
@Data
public class AccountBatchRedeemRequest implements Serializable{
	
	private static final long serialVersionUID = 2136893610065389435L;
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 请求批次号
	 */
	private String requestNo;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 订单List
	 */
	private List<AccountOrderDto> orderList;
}
