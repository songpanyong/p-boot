package com.guohuai.points.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.guohuai.basic.component.ext.hibernate.UUID;


@Entity
@Table(name = "T_TULIP_ACCOUNT_TRANS")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AccountTransEntity extends UUID {
	/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 4140226088022063676L;
	
	//积分增加标记
	public static final String ADD = "ADD";
	//积分减少标记
	public static final String REDUCE = "REDUCE";
	
	
	
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
	 * 01：签到，02：卡券，03：充值，04：消费，05：撤单， 06：过期
	 */
	private String orderType;
	/**
	 * 关联产品编码
	 */
	private String relationProductCode;
	/**
	 * 关联产品编码
	 */
	private String relationProductName;
	/**
	 * 账户名称：01：积分基本户、02：签到积分户、03：卡券积分户、04：充值积分户
	 */
	private String accountName;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 定单描述
	 */
	private String orderDesc;
	/**
	 * 积分方向，增add 减reduce
	 */
	private String direction;
	/**
	 * 订单金额
	 */
	private BigDecimal orderPoint;
	/**
	 * 交易后余额
	 */
	private BigDecimal point;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 交易账户
	 */
	private String transAccountNo;
	/**
	 * 财务入账标识
	 */
	private String financeMark;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
}
