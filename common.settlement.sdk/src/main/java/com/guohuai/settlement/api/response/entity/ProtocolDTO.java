package com.guohuai.settlement.api.response.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ProtocolDTO
 * @Description: 查询用户绑卡信息
 * @author chendonghui
 * @date 2018年2月3日 下午3:12:18
 *
 */
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ProtocolDTO implements Serializable {
	private static final long serialVersionUID = -7213721274134157832L;
	/**
	 * 银行卡开户行类型,1:储蓄卡
	 */
	private String accountBankType;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 证件号
	 */
	private String certificateNo;
	/**
	 * 客户姓名	
	 */
	private String realName;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 手机号
	 */
	private String phone;
    /**
	 * 开户行所属省份
	 */
	private String province;
	/**
	 * 开户行所属城市
	 */
	private String city;
	/**
	 * 开户行所属省份
	 */
	private String branch;
	/**
	 * 开户行所属区、县
	 */
	private String county;
	/**
	 * 绑卡类型
	 */
	private String cardType;
	/**
	 * 证件类型
	 */
	private String certificates;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 是否为实名认证 Y N
	 */
	private String authenticationStatus;
}