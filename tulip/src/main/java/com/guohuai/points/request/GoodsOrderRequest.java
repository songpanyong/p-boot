package com.guohuai.points.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分商品兑换下单request
 * @author mr_gu
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsOrderRequest extends BaseRequest {

	/**
	 * 商品兑换用户Oid
	 */
	private String userOid;
	
	private String userName;
	
	private String goodsOid;

	private String name;
	/**
	 * 枚举: real实物、virtual虚拟
	 */
	private String type;
	/**
	 * 所需积分
	 */
	private BigDecimal needPoints;
	
	private BigDecimal minPoints;
	
	private BigDecimal maxPoints;
	
	/**
	 * 商品总数量
	 */
	private BigDecimal totalCount;
	
	/**
	 * 已兑换数量
	 */
	private BigDecimal exchangedCount;
	
	/**
	 * 收货地址Id
	 */
	private String addressOid;
	/**
	 * 订单备注
	 */
	private String remark;

	private Timestamp updateTime;
	
	private Timestamp createTime;
	
}
