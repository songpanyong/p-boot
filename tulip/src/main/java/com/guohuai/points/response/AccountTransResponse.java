package com.guohuai.points.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: AccountTransResponse 
 * @Description: 积分账户账户交易返回参数
 * @author CHENDONGHUI
 * @date 2017年3月20日 下午06:19:44 
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
