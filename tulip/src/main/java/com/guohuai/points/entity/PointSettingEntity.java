package com.guohuai.points.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_TULIP_SETTING")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PointSettingEntity extends UUID {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6653357380412407567L;
	
	//商品状态(-1:删除、0:未上架、1:已上架、2:已下架)
	public static final String SETTING_STATE_INIT = "0";
	public static final String SETTING_STATE_PUTON = "1";
	public static final String SETTING_STATE_PULLOFF = "2";
	public static final String SETTING_STATE_DELE = "-1";
	
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
