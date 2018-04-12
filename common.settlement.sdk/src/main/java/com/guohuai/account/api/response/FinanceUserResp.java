package com.guohuai.account.api.response;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FinanceUserResp extends BaseResponse {
	
	private static final long serialVersionUID = -4621221866479224344L;
	
	private String oid;
	private String userType; 
	private String systemUid; // 用户业务系统ID
	private String userOid; // 用户ID
	private String systemSource; // 来源系统
	private String name; // 姓名
	private String idCard; // 身份证号
	private String bankName; // 开户行
	private String cardNo; // 银行账号
	private String phone; // 手机号
	private String remark; // 备注
	private Timestamp updateTime; // 更新时间
	private Timestamp createTime; // 创建时间

}
