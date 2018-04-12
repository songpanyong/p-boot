package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 初始化账户查询请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class InitAccountQueryRequest implements Serializable{
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 1016531094143805824L;
	/**
	 * 用户类型
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
	
	
}
