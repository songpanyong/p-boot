package com.guohuai.account.api.request;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 发行人处理金额请求参数
 * @ClassName: NewUserRequest
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:10:41
 */
@Data
public class TransPublishRequest implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 2961719825208991131L;
	/**
	 * 单据号
	 */
	private String orderNo;
	/**
	 * 交易额
	 */
	private BigDecimal balance;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 关联产品编号
	 */
	private String relationProductNo;
	/**
	 * 发行人账户ID
	 */
	private String accountNo;
	/**
	 * 来源系统类型
	 */
	private String systemSource;
}