package com.guohuai.points.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryRequest extends BaseRequest {
	private String oid;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 下单用户名
	 */
	private String userName;
	/**
	 * 下单用户ID
	 */
	private String userOid;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 下单时间
	 */
	private Date orderedTime;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品数量
	 */
	private BigDecimal goodsCount;
	/**
	 * 发货时间
	 */
	private Date sendTime;
	/**
	 * 发货人
	 */
	private String sendOperater;
	/**
	 * 物流公司
	 */
	private String logisticsCompany;
	/**
	 * 物流号
	 */
	private String logisticsNumber;
	/**
	 * 发货状态（0：未发货、1：已发货、2：已取消）
	 */
	private Integer state;
	/**
	 * 取消原因
	 */
	private String cancelReason;
	/**
	 * 取消人
	 */
	private String cancelOperater;
	/**
	 * 取消时间
	 */
	private Date cancelTime;
	/**
	 * 订单积分金额
	 */
	private BigDecimal point;
	/**
	 * 下单用户手机号
	 */
	private String userPhone;
	/**
	 * 收货人手机号
	 */
	private String orderPhone;
	// 用于搜索（开始时间）
	private Date startTime;
	// 用于搜索（结束时间）
	private Date endTime;
}
