package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新建用户返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateUserResponse extends BaseResponse {
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -7416307372130930405L;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 账户号
	 */
	private String accountNo;
	/**
	 * 账户类型
	 */
	private String userType;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 状态
	 */
	private String status;
}
