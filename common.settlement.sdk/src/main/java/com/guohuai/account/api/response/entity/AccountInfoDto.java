package com.guohuai.account.api.response.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AccountInfoDto implements Serializable {
	// 冻结状态：0为正常，1为冻结，3为冻结审批中，4为解冻审批中
	public static final String FROZENSTATUS_NORMAL = "0";
	public static final String FROZENSTATUS_FROZEN = "1";
	public static final String FROZENSTATUS_AUDI = "3";
	public static final String FROZENSTATUS_THAW = "4";

	// 0：保存，1：提交
	public static final String STATUS_SAVE = "0";
	public static final String STATUS_SUBMIT = "1";
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -922483069560508853L;
	/**
	 * 账户号
	 */
	private String accountNo;
	/**
	 * 用户ID
	 */
	private String userOid;
	/**
	 * T1--投资人账户、T2--发行人账户、T3--平台账户
	 */
	private String userType;
	/**
	 * 01--活期，02--活期利息，03--体验金，04--在途，05--冻结户
	 */
	private String accountType;
	/**
	 * 关联产品
	 */
	private String relationProduct;
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 开户时间
	 */
	private String openTime;
	/**
	 * 开户人
	 */
	private String openOperator;
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 0：保存，1：提交
	 */
	private String status;
	/**
	 * 0为正常，1为冻结，3为冻结审批中，4为解冻审批中
	 */
	private String frozenStatus;
	/**
	 * 备注
	 */
	private String remark;
	private String updateTime;
	private String createTime;
	/**
	 * 审核状态
	 */
	private String auditStatus;
	/**
	 * 授信额度
	 */
	private BigDecimal lineOfCredit;
}