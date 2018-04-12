package com.guohuai.points.response;

import com.guohuai.basic.component.ext.web.BaseResp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPointsResponse extends BaseResp {
	private String oid;
	/**
		 * 请求流水号
		 */
		private String requestNo;
		/**
		 * 来源系统类型
		 */
		private String systemSource;
		/**
		 * 来源系统单据号
		 */
		private String orderNo;
		/**
		 * 用户ID
		 */
		private String userOid;
		/**
		 * 01：签到，02：卡券，03：充值，04：消费，05：过期， 06：撤单
		 */
		private String orderType;
		/**
		 * 关联产品编码
		 */
		private String relationProductCode;
		/**
		 * 关联产品编码
		 */
		private String relationProductName;
		/**
		 * 账户名称：01：积分基本户、02：签到积分户、03：卡券积分户、04：充值积分户
		 */
		private String accountName;
		/**
		 * 账户类型
		 */
		private String accountType;
		/**
		 * 定单描述
		 */
		private String orderDesc;
		/**
		 * 积分方向，增add 减reduce
		 */
		private String direction;
		/**
		 * 订单金额
		 */
		private BigDecimal orderPoint;
		/**
		 * 交易后余额
		 */
		private BigDecimal point;
		/**
		 * 备注
		 */
		private String remark;
		/**
		 * 交易账户
		 */
		private String transAccountNo;
		/**
		 * 财务入账标识
		 */
		private String financeMark;
		/**
		 * 更新时间
		 */
		private Timestamp updateTime;
		/**
		 * 创建时间
		 */
		private Timestamp createTime;

}
