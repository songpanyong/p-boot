package com.guohuai.account.api.response;

import java.math.BigDecimal;

import com.guohuai.account.api.response.entity.TransDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户交易明细返回参数
 * @ClassName: NewUserResponse
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:19:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TransDetailQueryResponse extends BaseResponse {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 993168139997541647L;

	public TransDetailQueryResponse(TransDto entity) {
		this.userOid = entity.getUserOid();
		this.accountOid = entity.getAccountOid();
		this.accountName = entity.getAccountName();
		this.systemSource = entity.getSystemSource();
		this.orderType = entity.getOrderType();
		this.userType = entity.getUserType();
		this.relationProductNo = entity.getRelationProductNo();
		this.relationProductName = entity.getRelationProductName();
		this.oid = entity.getOid();
		this.orderNo = entity.getOrderNo();
		this.direction = entity.getDirection();
		this.orderBalance = entity.getOrderBalance();
		this.balance = entity.getBalance();
		this.updateTime = entity.getUpdateTime();
		this.ramark = entity.getRamark();
		this.financeMark = entity.getFinanceMark();
		this.requestNo = entity.getRequestNo();
		this.accountOrderOid = entity.getAccountOrderOid();
		this.orderDesc = entity.getOrderDesc();
		this.dataSource = entity.getDataSource();
		this.isDelete = entity.getIsDelete();
		this.currency = entity.getCurrency();
		this.inputAccountNo = entity.getInputAccountNo();
		this.outpuptAccountNo = entity.getOutpuptAccountNo();
		this.phone = entity.getPhone();
		this.accountType = entity.getAccountType();
	}

	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户编码
	 */
	private String accountOid;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 单据类型
	 */
	private String orderType;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品编号
	 */
	private String relationProductNo;
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 账务明细id
	 */
	private String oid;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 金额方向
	 */
	private String direction;
	/**
	 * 交易额
	 */
	private BigDecimal orderBalance;
	/**
	 * 交易后余额
	 */
	private BigDecimal balance;
	/**
	 * 交易时间
	 */
	private String updateTime;
	/**
	 * 交易用途
	 */
	private String ramark;
	/**
	 * 财务入账标识
	 */
	private String financeMark;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 收单OID
	 */
	private String accountOrderOid;
	/**
	 * 订单描述
	 */
	private String orderDesc;
	/**
	 * 数据来源
	 */
	private String dataSource;
	/**
	 * 删除标记
	 */
	private String isDelete;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 入账账户
	 */
	private String inputAccountNo;
	/**
	 * 出账账户
	 */
	private String outpuptAccountNo;

	private String phone;
}