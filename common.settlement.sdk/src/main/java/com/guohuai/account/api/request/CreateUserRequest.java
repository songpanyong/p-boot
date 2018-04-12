package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 创建用户请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class CreateUserRequest implements Serializable{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -264337167690208750L;
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
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 业务标签
	 */
	private String businessTag;
	
	/**
	 * 用户编号
	 */
	private String userOid;
}
