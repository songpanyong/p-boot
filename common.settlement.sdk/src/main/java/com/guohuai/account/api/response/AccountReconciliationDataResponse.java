package com.guohuai.account.api.response;

import java.util.List;

import com.guohuai.settlement.api.response.OrderAccountResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询对账数据返回list
 * @author CHENDONGHUI
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountReconciliationDataResponse extends BaseResponse {
	
	private static final long serialVersionUID = 5283583427184099460L;
	
	List<OrderAccountResponse> orderList;
}
