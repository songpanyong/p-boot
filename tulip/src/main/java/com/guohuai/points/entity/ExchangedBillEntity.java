package com.guohuai.points.entity;

import com.guohuai.basic.component.ext.hibernate.UUID;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "T_TULIP_EXCHANGED_BILL")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExchangedBillEntity extends UUID {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7397226677501780362L;
	/**
	 * 积分商品Id
	 */
	private String goodsOid;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品类型（枚举: real实物、virtual虚拟）
	 */
	private String type;
	/**
	 * 用户
	 */
	private String userOid;
	/**
	 * 用户信息实体
	 */
//	@OneToOne(fetch = FetchType.LAZY)
	@OneToOne
	@JoinColumn(name = "userOid", referencedColumnName ="oid",insertable=false, updatable=false)
	private UserInvestEntity user;
	/**
	 * 兑换数量
	 */
	private BigDecimal exchangedCount;
	
	/**
	 * 消耗积分数
	 */
	private BigDecimal expendPoints;
	
	/**
	 * 兑换时间
	 */
	private Date exchangedTime;
	/**
	 * 状态：0成功、1：失败
	 */
	private Integer state;

}
