package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新建账户返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateAccountResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -1973703396912515508L;
	
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
	 * 账户号
	 */
	private String accountNo; 
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private String createTime;
}
