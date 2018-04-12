package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 设置密码请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class ModifyPasswordRequest implements Serializable{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 3258099427966021546L;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 原交易密码
	 */
	private String oldPassword;
	/**
	 * 新交易密码
	 */
	private String newPassword;
}
