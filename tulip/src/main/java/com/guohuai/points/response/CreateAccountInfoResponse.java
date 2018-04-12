package com.guohuai.points.response;

import java.math.BigDecimal;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: CreateAccountInfoResponse 
 * @Description: 新建账户返回参数. 此类与CreateAccountResponse唯一的区别就是继承的父类不一样
 * <p> 目的：为了返回参数与积分商城的其他模块保持一致
 * @author hugo
 * @date 2017年5月31日15:36:22
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateAccountInfoResponse extends BaseResp {

	/**
	 * 账户oid
	 */
	private String oid;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品\卡券
	 */
	private String relationProduct;
	/**
	 * 关联产品名称\卡券
	 */
	private String relationProductName;
	/**
	 * 账户号
	 */
	private String accountNo; 
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private String createTime;
}
