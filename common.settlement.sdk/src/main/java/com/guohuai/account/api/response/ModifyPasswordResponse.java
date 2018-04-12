package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 修改密码返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ModifyPasswordResponse extends BaseResponse {
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 8793675540531022973L;
	/**
	 * 会员id
	 */
	private String userOid;
	
}
