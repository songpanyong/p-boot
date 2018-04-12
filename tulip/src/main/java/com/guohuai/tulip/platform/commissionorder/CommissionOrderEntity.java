package com.guohuai.tulip.platform.commissionorder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "T_TULIP_COMMISSION_ORDER")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class CommissionOrderEntity extends UUID {
	
	private static final long serialVersionUID = 2661687184135972904L;
	
	/** 订单状态--通过:pass */
	public static final String ORDERTYPE_PASS="pass";
	/** 订单状态--驳回:refused */
	public static final String ORDERSTATUS_REFUSED="refused";
	/** 订单状态--待审批:pending */
	public static final String ORDERSTATUS_PENDING="pending";
	
	/** 用户ID */
	private String userId;
	/** 卡券批次ID */
	private String couponOid;
	/** 用户类型 */
	private String type;
	/** 订单状态 */
	private String orderStatus;
	/** 业务订单号 */
	private String orderCode;
	/** 被邀请人手机号 */
	private String friendPhone;
	/** 手机号 */
	private String phone;
	/** 审核人 */
	private String auditor;
	/** 拒绝内容 */
	private String rejectAdvice;
	/** 佣金订单金额 */
	private BigDecimal orderAmount;
	/** 被推荐人投资金额 */
	private BigDecimal friendInvest;
	/** 创建时间 */
	Timestamp createTime;
	/** 审核时间 */
	Timestamp auditTime;
	/**  被邀请人投资时间 */
	Timestamp friendInvestTime;
}
