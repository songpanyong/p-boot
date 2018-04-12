package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TiedCardResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 5248895134491957723L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户ID
	 */
	private String accountOid;
	
}
