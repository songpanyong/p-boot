package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * 绑卡请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class TiedCardRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 2088347455540283083L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 姓名
	 */
	private String realName; 
	/**
	 * 身份证号
	 */
	private String identityNo; 
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 手机号
	 */
	private String phone; 
	/**
	 * 业务标签
	 */
	private String busiTag;
}
