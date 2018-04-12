package com.guohuai.points.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建订单返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateAccOrderResponse extends BaseResponse {

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 8145936663283289843L;
	/**
	 * 
	 */
	private String orderOid;
	/**
	 * 来源系统单据号
	 */
	private String orderNo;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 交易类别
	 */
	private String transType;
	/**
	 * 交易额
	 */
	private BigDecimal balance;	
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 状态
	 */
	private String orderStatus;
}
