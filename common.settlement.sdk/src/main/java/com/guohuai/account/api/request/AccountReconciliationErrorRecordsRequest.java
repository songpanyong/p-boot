package com.guohuai.account.api.request;

import com.guohuai.account.component.PageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 三方对账文件异常订单请求参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountReconciliationErrorRecordsRequest extends PageBase implements Serializable {
	private static final long serialVersionUID = -91110545656002171L;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 备注
	 */
	private String remark;
}