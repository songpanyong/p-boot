package com.guohuai.account.api.response.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto implements Serializable {
	private static final long serialVersionUID = 4525524111323273294L;
	private String oid;
	private String userType; // T1--投资人账户、T2--发行人账户、T3--平台账户
	private String systemUid; // 用户业务系统ID
	private String userOid; // 用户ID
	private String systemSource; // 来源系统
	private String createTime; // 创建时间
}