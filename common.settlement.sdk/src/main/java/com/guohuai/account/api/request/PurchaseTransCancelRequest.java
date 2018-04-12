package com.guohuai.account.api.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 申购废单
 */
@Data
public class PurchaseTransCancelRequest implements Serializable {
	private static final long serialVersionUID = -1590090393443366977L;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 原订单号
	 */
	private String oldOrderNo;
	/**
	 * 备注
	 */
	private String remark;
}