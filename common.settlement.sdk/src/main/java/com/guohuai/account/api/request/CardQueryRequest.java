package com.guohuai.account.api.request;

import java.io.Serializable;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡查询请求参数
 * @ClassName: NewUserRequest
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:10:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CardQueryRequest extends PageBase implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 8910623495114378741L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String identityNo;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 业务标签
	 */
	private String businessTag;

	/**
	 * 绑卡状态 0：绑卡；1:解绑：“”：全部
	 */
	private String status;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 预留手机号
	 */
	private String reservedCellPhone;

	/**
	 * 绑卡类型
	 */
	private String cardType;

	private String startTime;

	private String endTime;
}