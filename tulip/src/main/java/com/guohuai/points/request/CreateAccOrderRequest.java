package com.guohuai.points.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: CreateOrderRequest 
 * @Description: 入账单请求参数
 * @author CHENDONGHUI
 * @date 2017年3月20日 下午06:10:21 
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateAccOrderRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 4126498509422513638L;
	
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
	 * 关联产品编号
	 */
	private String relationProductNo; 
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 交易额
	 */
	private BigDecimal balance;	
	
	/**
	 * 系统来源
	 */
	private String systemSource;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 定单描述
	 */
	private String orderDesc;
	/**
	 * 原单据号
	 */
	private String oldOrderNo;
	
}
