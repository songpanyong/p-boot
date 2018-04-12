package com.guohuai.settlement.api.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindBindResponse extends BaseResponse {
	private static final long serialVersionUID = -1630979089309767333L;

	List<BankCard> bankCards;

	@Data
	public static class BankCard {
		/**
		 * 用户编号
		 */
		private String userOid;

		/**
		 * 银行卡号
		 */
		private String cardNo;

		/**
		 * 代扣协议号
		 */
		private String protocolNo;

		/**
		 * 绑卡状态
		 */
		private String status;

		/**
		 * 开户支行名称
		 */
		private String branch;

		/**
		 * 用户名称
		 */
		private String realName;

		/**
		 * 银行名称
		 */
		private String bankName;
	}
}