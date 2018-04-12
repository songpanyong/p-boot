package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 入账单请求参数
* @ClassName: NewUserRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateOrderRequest extends PageBase implements Serializable{
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
	 * 申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
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
	 * 入账账户
	 */
	private String inputAccountNo;
	/**
	 * 出账账户
	 */
	private String outpuptAccountNo;
	
	private String businessStatus;
	
	private String financeStatus;
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
	 * 转出产品编号
	 */
	private String outputRelationProductNo;
	
	/**
	 * 转出产品名称
	 */
	private String outputRelationProductName;
	
	/**
	 * 业务系统订单创建时间（用于业务和账户对账）
	 */
	private String orderCreatTime;
	/**
	 * 发行人用户Id
	 */
	private String publisherUserOid;
	/**
	 * 代金券
	 */
	private BigDecimal voucher=new BigDecimal(0);
	
	/**
	 * 手续费
	 */
	private BigDecimal fee=BigDecimal.ZERO;
	
	/**
	 * 冻结金额（用于续投、转换）
	 */
	private BigDecimal frozenBalance=BigDecimal.ZERO;
	
	/**
	 * 订单关联产品类型20170410新增
	 */
	private String productType;
	
	/**
	 * 用户类型 T1投资人 T2发行人
	 */
	private String userType;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 原订单号
	 */
	private String origOrderNo;

	/**
	 * 续投解冻金额，申购续投时使用
	 */
	public BigDecimal continUnfreezBalance=BigDecimal.ZERO;

	/**
	 * 加息券收益
	 */
	public BigDecimal rateBalance=BigDecimal.ZERO;
}
