package com.guohuai.account.api.response;

import com.guohuai.account.api.response.entity.SignDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡查询返回参数
 * @ClassName: NewUserResponse
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:19:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CardQueryResponse extends BaseResponse {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -7792412529326908280L;

	public CardQueryResponse(SignDto entity) {
		this.userOid = entity.getUserOid();
		this.realName = entity.getRealName();
		this.identityNo = entity.getIdentityNo();
		this.bankCard = entity.getBankCard();
		this.phone = entity.getPhone();
		this.businessTag = entity.getBusiTag();
		// this.bankName = entity.getBankGroup();
		this.status = entity.getStatus();
		this.createTime = entity.getCreateTime();
		this.accountName = entity.getAccountName();
		this.userType = entity.getUserType();
		this.bankName = entity.getBankName();
		this.certificates = entity.getCertificates();
		this.reservedCellPhone = entity.getReservedCellPhone();
		this.cardType = entity.getCardType();
	}

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
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 证件类型
	 */
	private String certificates;

	/**
	 * 用户名称
	 */
	private String accountName;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 预留手机号
	 */
	private String reservedCellPhone;

	// 绑卡类型
	private String cardType;
}