package com.guohuai.qams.answerQue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 投资人-基本账户
 * 
 * @author yuechao
 *
 */
@Entity
@Table(name = "T_MONEY_INVESTOR_BASEACCOUNT")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class InvestorBaseAccountInfoEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3103110921829885852L;

	/** 用户类型--投资者T1 */
	public static final String BASEACCOUNT_userType_T1 = "T1";
	
	/** 状态--正常 */
	public static final String BASEACCOUNT_status_normal = "normal";
	/** 状态--禁用 */
	public static final String BASEACCOUNT_status_forbidden = "forbidden";

	/** 账户所有者--投资者 */
	public static final String BASEACCOUNT_owner_investor = "investor";
	/** 账户所有者--平台 */
	public static final String BASEACCOUNT_owner_platform = "platform";
	
	/** 是新手 */
	public static final String BASEACCOUNT_isFreshMan_yes = "yes";
	/** 非新手 */
	public static final String BASEACCOUNT_isFreshMan_no = "no";

	/** 来源系统类型--mimosa */
	public static final String BASEACCOUNT_systemSource_mimosa = "mimosa";
	
	/** 注册来源--后台添加 */
	public static final String BASEACCOUNT_SOURCE_backEnd = "backEnd";
	/** 注册来源--前台注册 */
	public static final String BASEACCOUNT_SOURCE_frontEnd = "frontEnd";
	
	@Id
	private String oid;
	
	/**
	 * 会员OID
	 */
	private String memberId;

	/**
	 * 手机号
	 */
	private String phoneNum;

	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 身份证号
	 */
	private String idNum;
	
	/**
	 * 邀请用户标识
	 */
	private String uid;

	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 新手否
	 */
	private String isFreshMan;

	/**
	 * 余额 =  提现冻结 + 充值冻结 + 提现可用   //  申购可用 + 提现冻结
	 */
	private BigDecimal balance = BigDecimal.ZERO;
	
	/**
	 * 提现冻结
	 */
	private BigDecimal withdrawFrozenBalance = BigDecimal.ZERO;
	
	/**
	 * 充值冻结(充值到账，但不能提现)
	 */
	private BigDecimal rechargeFrozenBalance = BigDecimal.ZERO;
	
	/**
	 * 申购可用
	 */
	private BigDecimal applyAvailableBalance = BigDecimal.ZERO;
	
	/**
	 * 提现可用
	 */
	private BigDecimal withdrawAvailableBalance = BigDecimal.ZERO;

	/**
	 * 账户所有者
	 */
	private String owner;
	
	/**登录密码 */
	private String userPwd;
	
	/**随机密钥 */
	private String salt;
	
	/**支付密码 */
	private String payPwd;
	
	/**支付密码随机密钥 */
	private String paySalt;
	
	/**注册来源 */
	private String source;
	
	/**注册渠道 */
	private String channelid;

	private Timestamp updateTime;

	private Timestamp createTime;

}
