package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;
/**
 * @ClassName: ReconciliationRequest
 * @Description: 交易订单查询
 */
@Data
public class TransactionRequest  implements Serializable{
	private static final long serialVersionUID = -112765746294580721L;
	
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 支付状态
	 */
	private String status;
	
	/**
	 * 交易类别
	 * 例如：申购：01，赎回：02
	 */
	private String type;
	
	/**
	 * 开始时间
	 */
	private String beginTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 当前页
	 */
	private int page=1;
	
	/**
	 * 每页显示的条数
	 */
	private int row=500;
}
