package com.guohuai.points.request;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 创建账户请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class CreateAccountRequest implements Serializable{

	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -5247704351104399270L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品、卡券
	 */
	private String relationProduct;
	/**
	 * 关联产品、卡券名称
	 */
	private String relationProductName;
	
	/**
	 * 过期时间
	 */
	private Timestamp overdueTime;
	/**
	 * 备注
	 */
	private String remark;
}
