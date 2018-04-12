package com.guohuai.account.api.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.guohuai.account.api.response.entity.ProductAccountDto;

/**
 * @ClassName: PublisherAccountInfoResponse 
* @Description: 产品户查询返回结果
* @author chendonghui
* @date 2018年2月5日 上午9:43:12 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PublisherAccountInfoResponse extends BaseResponse {
	private static final long serialVersionUID = -1257697552325581748L;
	/**
	 * 发行人id
	 */
	private String userOid;
	/**
     * 基本账户余额
     */
    private BigDecimal basicBalance=BigDecimal.ZERO;
    /**
     * 清算户余额
     */
    private BigDecimal collectionSettlementBalance=BigDecimal.ZERO;
    /**
     * 提现可用金余额
     */
    private BigDecimal withdrawAvailableAmountBalance=BigDecimal.ZERO;
    /**
     * 冻结户余额
     */
    private BigDecimal frozenAmountBalance=BigDecimal.ZERO;
	/**
	 * 产品户可用余额总额 
	 */
	private BigDecimal productAvailableAmountBalance;
	/**
	 * 产品户授信额度总额
	 */
	private BigDecimal productCreditAmountBalance;
	/**
	 * 产品户账户净额总额
	 */
	private BigDecimal productNetAmountBalance;
	/**
	 * 产品户信息
	 */
	private List<ProductAccountDto> productAccountList;
	
}