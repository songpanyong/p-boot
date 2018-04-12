package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 账户交易返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountTransResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -892280947131977402L;

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
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 备付金出账账户
	 */
	private String reservedAccountNo;
	
	@Override
	public String toString() {
		return "AccountTransResponse [userOid=" + userOid + ", userType="
				+ userType + ", transType=" + transType + ", balance="
				+ balance + ", requestNo=" + requestNo + ", orderStatus="
				+ orderStatus + ", orderNo=" + orderNo + ", reservedAccountNo="
				+ reservedAccountNo + "]";
	}
	
}
