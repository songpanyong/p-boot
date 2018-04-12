package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 申购废单返回参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PurchaseTransCancelResponse extends BaseResponse {
	private static final long serialVersionUID = -2262866457305378374L;
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
    private String oldOrderOid;
    /**
     * 废单状态
     */
    private String status;
}