package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BankInfoResponse extends BaseResponse{
	
	private static final long serialVersionUID = 5692189676061037695L;
	/**
	 * 发卡行名称
	 */
	private String bankName;
	/**
	 * 机构代码
	 */
	private String bankCode;
	/**
	 * 卡名
	 */
	private String cardName;
	/**
	 * 卡号长度
	 */
	private int cardLength;
	/**
	 * BIN长度
	 */
	private int BINLength;
	/**
	 * 卡BIN
	 */
	private String bankBin; 
	/**
	 * 卡种
	 */
	private String kindOfCard;
}
