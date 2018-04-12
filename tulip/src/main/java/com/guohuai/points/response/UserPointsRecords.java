package com.guohuai.points.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @author chendonghui
 * @version 创建时间 ：2017年4月28日上午10:51:09
 *
 */
@Data
public class UserPointsRecords implements Serializable{
	private static final long serialVersionUID = -3966810509224769751L;
	/**
	 * 积分记录类型
	 */
	private String pointType;
	/**
	 * 积分方向 add，REDUCE
	 */
	private String direction;
	/**
	 * 交易积分
	 */
	private BigDecimal point;
	/**
	 * 交易时间
	 */
	private Timestamp pointTime;
}
