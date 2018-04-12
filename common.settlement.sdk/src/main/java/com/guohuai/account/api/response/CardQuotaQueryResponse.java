package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单笔限额返回参数
 * @ClassName: CardQuotaQueryResponse
 * @Description:
 * @author chendonghui
 * @date 2017年8月6日 上午10:49:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CardQuotaQueryResponse extends BaseResponse {
	private static final long serialVersionUID = 4995722900462670232L;
	/**
	 * 银行卡号
	 */
	private String bankCard;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行code
	 */
	private String bankCode;
	/**
	 * 银行卡种
	 */
	private String kindOfCard;
	/**
	 * 充值单笔限额
	 */
	private BigDecimal rechageSingleQuota;
	/**
	 * 充值单日限额
	 */
	private BigDecimal rechageDailyLimit;
	/**
	 * 提现单笔限额
	 */
	private BigDecimal withdrawalsSingleQuota;
	/**
	 * 提现单日限额
	 */
	private BigDecimal withdrawalsDailyLimit;
}