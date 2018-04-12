package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询请求参数
 * @ClassName: NewUserRequest
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:10:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderQueryRequest extends PageBase implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -4515080369360429725L;

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
	 * 申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
	 */
	private String orderType;
	/**
	 * 关联产品编号
	 */
	private String relationProductNo;
	/**
	 * 从单据金额
	 */
	private BigDecimal sBalance;
	/**
	 * 到单据金额
	 */
	private BigDecimal eBalance;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 手机号
	 */
	private String phone;
}