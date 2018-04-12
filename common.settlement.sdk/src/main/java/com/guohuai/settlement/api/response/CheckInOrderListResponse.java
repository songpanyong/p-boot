package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: AuthenticationResponse
 * @Description: 签约代扣协议
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CheckInOrderListResponse extends BaseResponse{
	private static final long serialVersionUID = -3286518029485346925L;
	
	/**
	 * 总条数
	 */
	private int totalCount=0;
	/**
	 * 成功条数
	 */
	private int failCount=0;
}
