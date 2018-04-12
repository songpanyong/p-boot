package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户列表查询请求参数
 * @author wangyan
 *
 */
@Data
public class UserQueryRequest implements Serializable{

	private static final long serialVersionUID = 8165437587297956578L;
	/**
	 * 用户业务系统id
	 */
	private String systemUid;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 用户类型
	 * 投资人账户:T1、发行人账户:T2、平台账户:T3  
	 */
	private String userType; 
	
}
