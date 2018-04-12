package com.guohuai.points.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 账户交易请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class AccountTradeRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 2136893610065389423L;
	/**
	 * 系统来源
	 */
	private String systemSource;
	/**
	 * 来源系统单据号
	 */
	private String orderNo;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 会员id
	 */
	private String userOid; 
	/**
	 * 单据类型
	 */
	private String orderType; 
	/**
	 * 关联产品、卡券编号
	 */
	private String relationProductNo; 
	/**
	 * 关联产品、卡券名称
	 */
	private String relationProductName;
	/**
	 * 交易额
	 */
	private BigDecimal balance;	
	/**
	 * 过期时间
	 */
	private Timestamp overdueTime;
	/**
	 * 定单描述
	 */
	private String orderDesc;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 来源系统原单据号
	 */
	private String oldOrderNo;
}
