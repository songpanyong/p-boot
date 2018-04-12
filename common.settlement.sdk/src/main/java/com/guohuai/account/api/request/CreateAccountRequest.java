package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 创建账户请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class CreateAccountRequest implements Serializable{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -5247704351104399270L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 用户类型
	 * 投资人账户:T1、发行人账户:T2、平台账户:T3  
	 */
	private String userType; 
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品
	 */
	private String relationProduct;
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
}
