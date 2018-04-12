package com.guohuai.payadapter.bankutil;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 对账req
 * @author chendonghui
 *
 */
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BankUtilReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
