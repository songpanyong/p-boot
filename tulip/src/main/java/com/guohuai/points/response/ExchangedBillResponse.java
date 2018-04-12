package com.guohuai.points.response;

import com.guohuai.basic.component.ext.web.BaseResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExchangedBillResponse extends BaseResp {
	/**
	 * 积分商品Id
	 */
	private String goodsOid;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品类型（枚举: real实物、virtual虚拟）
	 */
	private String type;
	/**
	 * 用户
	 */
	private String userOid;
	/**
	 * 用户手机号
	 */
	private String userPhone;
	/**
	 * 兑换数量
	 */
	private BigDecimal exchangedCount;
	
	/**
	 * 消耗积分数
	 */
	private BigDecimal expendPoints;
	
	/**
	 * 兑换时间
	 */
	private Date exchangedTime;
	/**
	 * 状态：0成功、1：失败
	 */
	private Integer state;

}
