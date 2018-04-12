package com.guohuai.account.api.request;

import java.io.Serializable;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户交易明细查询请求参数
 * @ClassName: NewUserRequest
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:10:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TransDetailQueryRequest extends PageBase implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 2299519493169076235L;

	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 单据类型
	 */
	private String orderType;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 账户编号
	 */
	private String accountOid;
	/**
	 * 关联产品
	 */
	private String relationProduct;
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

	private String orderNo;
}