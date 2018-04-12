package com.guohuai.settlement.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
/**
 * @ClassName: OrderRequest
 * @Description: 支付
 */
@Data
public class CheckInAccountOrderRequest  implements Serializable{
	
	private static final long serialVersionUID = -1546404174882688875L;

	/**
	 * 会员ID
	 */
	private String userOid;
	
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	
	/**
	 * 来源单据编号
	 */
	private String orderNo;
	
	/**
	 * 交易类别
	 * 例如：充值:01、 提现：02
	 */
	private String type;
	
	/**
	 * 金额
	 */
	private BigDecimal amount=BigDecimal.ZERO;
		
	/**
	 * 手续费
	 */
	private BigDecimal fee=BigDecimal.ZERO;
	
	/**
	 * 请求流水号
	 */
	private String requestNo;
	
	/**
	 * 订单描述
	 */
	private String describe;
	
	/**
	 * 下单时间
	 */
	private String orderCreateTime;
	
	/**
	 * 外部订单时间
	 */
	private String outOrderTime;
	
	/**
	 * 支付流水号
	 */
	private String payNo;
	
	/**
	 * 用户绑卡的手机号
	 */
	private String phone;
	
	/**
	 * 银行卡号
	 */
	private String cardNo;
	
	/**
	 * 支付通道
	 */
	private String channelNo;
	/**
	 * 操作人id
	 */
	private String operatorId;
	/**
	 * 操作人名称
	 */
	private String operatorName;
}
