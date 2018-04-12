package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.guohuai.account.api.response.entity.UserDto;

/**
 * 查询用户列表返回参数
 * @author wangyan
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -6068602276562611028L;

	public UserQueryResponse(UserDto entity) {
		this.oid = entity.getOid();
		this.userType = entity.getUserType();
		this.systemUid = entity.getSystemUid();
		this.userOid = entity.getUserOid();
		this.systemSource = entity.getSystemSource();
		this.createTime = entity.getCreateTime();
	}

	private String oid;
	private String userType; // T1--投资人账户、T2--发行人账户、T3--平台账户
	private String systemUid; // 用户业务系统ID
	private String userOid; // 用户ID
	private String systemSource; // 来源系统
	private String createTime; // 创建时间
}