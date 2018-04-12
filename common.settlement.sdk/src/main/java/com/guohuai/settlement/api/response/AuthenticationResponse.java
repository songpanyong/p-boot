package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: AuthenticationResponse
 * @Description: 签约代扣协议
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthenticationResponse extends BaseResponse{
	private static final long serialVersionUID = -3286518029485346965L;
	
	/**
	 * 请求流水号
	 */
	private String reuqestNo;
	
	/**
	 * 结果码
	 */
	private String result;
	
	/**
	 * 短信流水号
	 */
	private String smsSeq;
	
	/**
	 * 订单号
	 */
	private String orderNo;

}
