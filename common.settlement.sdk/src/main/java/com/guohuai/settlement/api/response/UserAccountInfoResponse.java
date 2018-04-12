package com.guohuai.settlement.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: UserAccountInfoResponse
 * @Description: 提现审核获取用户可用余额及用户创建时间
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserAccountInfoResponse  extends BaseResponse{
	private static final long serialVersionUID = 9099127634049128035L;
	
	/**
	 * 用户创建时间
	 */
	private String createTime;
	
	/**
	 * 用户userid
	 */
	private String memberId;
	
	/**
	 * 用户可用余额
	 */
	private BigDecimal balance;
	
	/**
	 * 是否锁定
	 */
	private boolean frozen;

}
