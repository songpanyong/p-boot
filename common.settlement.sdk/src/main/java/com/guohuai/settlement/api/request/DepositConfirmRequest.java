package com.guohuai.settlement.api.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 充值确认对账
 * @author hans
 *
 */
@Data
public class DepositConfirmRequest  implements Serializable{
	private static final long serialVersionUID = 2393871470225025334L;
	
	/**
	 * 会员ID
	 */
	private String userOid;
	
	/**
	 * 来源单据编号
	 */
	private String orderNo;
	
	/**
	 * 交易类别
	 * 例如：01充值；02提现
	 */
	private String tradeType;

}
