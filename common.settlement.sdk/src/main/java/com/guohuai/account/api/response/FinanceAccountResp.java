package com.guohuai.account.api.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FinanceAccountResp extends BaseResponse {
	
	private static final long serialVersionUID = -4621221866479224344L;
	
	private String oid;
	private String accountNo; 
	private String userOid;
	private String userType; 
	private String userName;
	private String phone;
	private String accountType;
	private String relationProduct;
	private String relationProductName;
	private String accountName;
	private Timestamp openTime;
	private BigDecimal balance;
	private String status;
	private String frozenStatus;
	private String remark;
	private Timestamp updateTime;
	private Timestamp createTime;
	private String auditStatus;
	private BigDecimal lineOfCredit;//授信额度

}
