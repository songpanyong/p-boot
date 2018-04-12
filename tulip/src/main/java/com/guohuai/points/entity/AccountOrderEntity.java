package com.guohuai.points.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "T_TULIP_ACCOUNT_ORDER")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AccountOrderEntity extends UUID {
	
	private static final long serialVersionUID = 1060337252245115641L;
	
	//订单状态1-成功，0-末处理，2-失败
	public static final String ORDERSTATUS_INIT = "0";
	public static final String ORDERSTATUS_SUCC = "1";
	public static final String ORDERSTATUS_Fail = "2";

	
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 来源系统单据号
	 */
	private String orderNo;
	/**
	 * 用户ID
	 */
	private String userOid;
	/**
	 * 订单类型：01：签到，02：卡券，03：充值，04：消费
	 */
	private String orderType;
	/**
	 * 关联产品编号
	 */
	private String relationProductCode;
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 单据金额
	 */
	private BigDecimal point;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 订单异常信息
	 */
	private String errorMessage;
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
}
