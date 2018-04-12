package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 验证密码返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ValidatePasswordResponse extends BaseResponse {
	
	
	/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = -2060903500208574635L;
	/**
	 * 会员id
	 */
	private String userOid;
	
}
