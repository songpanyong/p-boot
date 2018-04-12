package com.guohuai.points.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPointsRequest extends BaseRequest {
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 01：签到，02：卡券，03：充值，04：消费，05：撤单， 06：过期
	 */
	private String orderType;
	/**
	 * 积分方向，增ADD 减REDUCE
	 */
	private String direction;
	/**
	 * 最小交易积分
	 */
	private BigDecimal minOrderPoint;
	/**
	 * 最大交易积分
	 */
	private BigDecimal maxOrderPoint;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
}
