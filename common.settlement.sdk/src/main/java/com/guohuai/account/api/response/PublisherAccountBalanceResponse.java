package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 查询发行人账户余额返回参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PublisherAccountBalanceResponse extends BaseResponse {

	private static final long serialVersionUID = -6919156177921475210L;
	/**
     * 基本账户余额
     */
    private BigDecimal basicBalance=BigDecimal.ZERO;
    /**
     * 清算户余额
     */
    private BigDecimal collectionSettlementBalance=BigDecimal.ZERO;
    /**
     * 可用金户余额
     */
    private BigDecimal availableAmountBalance=BigDecimal.ZERO;
    /**
     * 提现可用金余额
     */
    private BigDecimal withdrawAvailableAmountBalance=BigDecimal.ZERO;
    /**
     * 冻结户余额
     */
    private BigDecimal frozenAmountBalance=BigDecimal.ZERO;
    /**
     * 充值冻结户余额
     */
    private BigDecimal rechargFrozenBalance=BigDecimal.ZERO;
    /**
     * 授信额度
     */
    private BigDecimal lineOfCredit=BigDecimal.ZERO;
    /**
     * 已使用授信额度
     */
    private BigDecimal usedLineOfCredit=BigDecimal.ZERO;


}
