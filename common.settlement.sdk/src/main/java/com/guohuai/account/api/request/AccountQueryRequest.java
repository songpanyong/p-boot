package com.guohuai.account.api.request;

import java.io.Serializable;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户查询请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountQueryRequest extends PageBase implements Serializable{/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 9203755036071799233L;
	/**
	 * 会员ID
	 */
	private String userOid;
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
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 账户id
	 */
	private String accountNo;
	
}
