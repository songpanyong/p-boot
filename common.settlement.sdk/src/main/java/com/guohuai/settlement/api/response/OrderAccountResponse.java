package com.guohuai.settlement.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: OrderAccountResponse
 * @Description: 对账获取订单信息接口
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OrderAccountResponse  extends BaseResponse{
	private static final long serialVersionUID = 9099127634049128035L;
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 支付类型
	 * invest:申购，redeem: 赎回
	 */
	private String orderType;
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount=BigDecimal.ZERO;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 订单日期
	 */
	private String buzzDate;
	
	/**
	 * 投资者id
	 */
	private String investorOid;
	
	/**
	 * 订单关联产品类型
	 */
	private String productType;
	
	/**
	 * 收单时间
	 */
	private String orderCreateTime; 
	
	/**
	 * 分页偏移量
	 */
	private long countNum=0;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 手续费
	 */
	private BigDecimal fee=BigDecimal.ZERO;
	
	
	/**
	 * 卡券金额
	 */
	private BigDecimal voucher=BigDecimal.ZERO;
	
	/**
	 * 用户id
	 */
	private String userOid;
	
	/**
	 * 投资人手机号
	 */
	private String userPhone;
	
	/**
	 * 投资人姓名
	 */
	private String userName;
	
	/**
	 * 是否完成三方对账
	 */
	private String reconlicationStatus;//Y，已完成对账；N，未完成对账

}
