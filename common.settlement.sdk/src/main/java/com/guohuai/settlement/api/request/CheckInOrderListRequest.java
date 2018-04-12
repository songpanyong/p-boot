package com.guohuai.settlement.api.request;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @ClassName: OrderRequest
 * @Description: 支付
 */
@Data
public class CheckInOrderListRequest implements Serializable {
	private static final long serialVersionUID = -1546404174882688875L;

	private List<CheckInAccountOrderRequest> checkInList;
}