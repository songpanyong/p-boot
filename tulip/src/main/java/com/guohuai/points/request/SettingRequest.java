package com.guohuai.points.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SettingRequest extends BaseRequest {

	private String oid;

	/**
	 * 积分产品名称
	 */
	private String name;
	
	/**
	 * 积分
	 */
	private BigDecimal points;
	
	/**
	 * 所需金额
	 */
	private BigDecimal amount;
	/**
	 * 商品总数量
	 */
	private BigDecimal totalCount;
	/**
	 * 剩余数量
	 */
	private BigDecimal remainCount;
	/**
	 * 商品状态(0:未上架、1:已上架、2:已下架)
	 */
	private Integer state;
	
	/**
	 * 修改人
	 */
	private String updateOperater;

	/**
	 * 添加人
	 */
	private String createOperater;

	private Timestamp updateTime;
	
	private Timestamp createTime;
	
}
