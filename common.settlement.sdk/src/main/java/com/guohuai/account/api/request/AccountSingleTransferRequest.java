package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @ClassName: AccountSingleTransferRequest  
 * @Description: 账户单笔转账
 * @author CHENDONGHUI
 * @date 2017年11月16日 上午9:30:23 
 *
 */
@Data
public class AccountSingleTransferRequest implements Serializable{
	
	private static final long serialVersionUID = 2136893610165381231L;
	/**
	 * 转账人ID
	 */
	private String outputUserOid;
	/**
	 * 转入用户ID
	 */
	private String inputUserOid;
	/**
	 * 转账金额
	 */
	private BigDecimal balance;
	/**
	 * 代金券
	 */
	private BigDecimal voucher;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 用户类型
	 * 投资人账户:T1、发行人账户:T2、平台账户:T3  
	 */
	private String userType;
	/**
	 * 单据类型
	 */
	private String orderType;
	/**
	 * 交易用途
	 */
	private String remark; 
	/**
	 * 来源系统单据号
	 */
	private String orderNo; 
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 定单描述
	 */
	private String orderDesc;
	/**
	 * 业务系统订单创建时间 YYYY-MM-DD HH:mm:ss
	 */
	private String submitTime;
	
}
