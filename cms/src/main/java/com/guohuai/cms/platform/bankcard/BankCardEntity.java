package com.guohuai.cms.platform.bankcard;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_BANKCARD")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class BankCardEntity extends UUID implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/** 审核状态-待审核 */
	public static final String BANKCARD_status_toApprove = "toApprove";
	/** 审核状态-拒绝 */
	public static final String BANKCARD_status_refused = "refused";
	/** 审核状态-通过 */
	public static final String BANKCARD_status_pass = "pass";
	
	/** 银行编号 */
	private String bankCode;
	
	/** 银行名称 */
	private String bankName;
	
	/** 人行代码 */
	private String peopleBankCode;
	
	/** 银行小logo */
	private String bankLogo;
	
	/** 银行大logo */
	private String bankBigLogo;
	
	/** 背景颜色 */
	private String bgColor;
	
	/** 单月提现限额 */
	private Long withdrawMoonLimit;
	
	/** 单月支付限额 */
	private Long payMoonLimit;
	
	/** 单日提现限额 */
	private Long withdrawDayLimit;
	
	/** 单日支付限额 */
	private Long payDayLimit;
	
	/** 单笔提现限额 */
	private Long withdrawOneLimit;
	
	/** 单笔支付限额 */
	private Long payOneLimit;
	
	
	/** 状态 pass:通过，refused:驳回，toApprove:待审批 */
	private String status;
	
	/** 编辑者 */
	private String creator;
	
	/** 审核者 */
	private String approver;
	
	/** 审核意见 */
	private String approveRemark;	
	
	/** 审批时间 */
	private Timestamp approveTime;
	
	private Timestamp updateTime;
	
	private Timestamp createTime;
	
}
