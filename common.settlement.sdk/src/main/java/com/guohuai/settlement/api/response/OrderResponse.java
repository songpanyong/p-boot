package com.guohuai.settlement.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: OrderResponse
 * @Description: 支付
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OrderResponse  extends BaseResponse{
	private static final long serialVersionUID = -112765746294580721L;
	
	/**
	 * 会员ID
	 */
	private String userOid;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 交易类别
	 * 例如：充值：01，提现：02  赎回：03返佣：60
	 */
	private String type;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	
	/**
	 * 支付备注
	 */
	private String remark;
	
	/**
	 * 支付时间
	 */
	private String payTime;
	
	/**
	 * 支付状态
	 */
	private String status;
	
	/**
	 * 对账码
	 */
	private String reconciliationCode;
	
	/**
	 * 对账结果状态
	 */
	private String reconciliationStatus;
	
	/**
	 * 短信验证码
	 */
	private String smsCode;
	
	/**
	 * 支付流水号
	 */
	private String payNo;

	/**
	 * 网关支付返还页面
	 */
	private String respHtml;
	
	/**
	 * 用户绑卡的手机号
	 */
	private String phone;
	
	/**
	 * 用户类型  T1投资人，T2发行人，
	 */
	private String userType;
	
	/**
	 * 先锋会员id
	 */
	private String memberUserId;
	/**
	 * 先锋交易流水号
	 */
	private String outTradeNo;
	/**
	 * 先锋支付流水号
	 */
	private String outPaymentId;
	/**
	 * 支付通道
	 */
	private String channelNo;
	
}
