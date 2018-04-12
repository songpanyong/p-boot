package com.guohuai.points.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_TULIP_GOODS")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PointGoodsEntity extends UUID  {

	//商品状态(0:未上架、1:已上架、2:已下架)
	public static final String GOODSSTATE_INIT = "0";
	public static final String GOODSSTATE_PUTON = "1";
	public static final String GOODSSTATE_PULLOFF = "2";
	public static final String GOODSSTATE_DELE = "-1";
	
	
	public static final String GOODSTYPE_REAL = "real";
	public static final String GOODSTYPE_VIRTUAL = "virtual";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4561872254473098052L;
	
	/**
	 * 兑换商品名
	 */
	private String name;
	
	/**
	 * 枚举: real实物、virtual虚拟
	 */
	private String type;
	
	/**
	 * 虚拟卡券类型
	 */
	private String virtualCouponType;
	
	/**
	 * 虚拟卡券id（待下发的卡券id）
	 */
	private String issueVirtualCouponId;
	
	/**
	 * 所需积分
	 */
	private BigDecimal needPoints;
	/**
	 * 商品总数量
	 */
	private BigDecimal totalCount;
	/**
	 * 已兑换数量
	 */
	private BigDecimal exchangedCount;
	
	/**
	 * 剩余数量（库存）
	 */
	private BigDecimal remainCount;
	
	/**
	 * 商品状态(0:未上架、1:已上架、2:已下架)
	 */
	private Integer state;
	
	/**
	 * 商品图片id
	 */
	private String fileOid;
	
	/**
	 * 商品介绍
	 */
	private String remark;
	
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
