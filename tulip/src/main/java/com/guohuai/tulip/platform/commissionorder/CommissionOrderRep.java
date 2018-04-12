package com.guohuai.tulip.platform.commissionorder;

import java.math.BigDecimal;
import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@lombok.Data
public class CommissionOrderRep {

	/** 用户编号、渠道类型、订单编号、被邀请用户、用户联系方式、审核/驳回人、驳回意见,订单状态 */
	private String userId, type, orderCode, friendPhone, phone, auditor,rejectAdvice,orderStatus;
	/** 订单金额/返佣金额,被邀请人投资金额 */
	private BigDecimal orderAmount,friendInvest,commissionAmount;
	/** 订单生成时间,审核/驳回时间 ,奖励截至时间,被邀请人投资时间*/
	private Timestamp createTime,auditTime,deadLine,friendInvestTime;
	private String oid;
}
