package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设置密码返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreatePasswordResponse extends BaseResponse {
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 7061695572728830340L;
	
	/**
	 * 会员id
	 */
	private String userOid;
}
