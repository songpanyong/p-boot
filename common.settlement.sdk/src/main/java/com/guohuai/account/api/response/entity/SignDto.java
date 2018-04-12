package com.guohuai.account.api.response.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SignDto implements Serializable {
	private static final long serialVersionUID = -9142925618360157430L;
	private String realName; // 用户名
	private String phone; // 手机号
	private String identityNo; // 身份证号
	private String protocolNo; // 协议号
	private String status; // 签约状态 0-已签约 1-已解约
	private String userOid; // 用户编号
	private String bankCard; // 银行卡号
	private String bankGroup; // 银行组号
	private String cardType;
	private String createTime; // 创建时间
	private String updateTime; // 修改时间
	private String busiTag; // 业务标签
	private String reservedCellPhone;// 预留手机号
	private String accountName;// 用户名称
	private String userType;// 用户类型
	private String bankName;// 银行名称
	private String certificates;// 证件类型
}