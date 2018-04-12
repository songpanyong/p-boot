package com.guohuai.settlement.api.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @ClassName: ReconciliationResponse
 * @Description: 交易订单查询
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TransactionResponse  extends BaseResponse{
	private static final long serialVersionUID = -112765746294580721L;
	
	/**
	 * 会员ID
	 */
	private String userOid;
	
	/**
	 * 来源系统类型
	 */
	private String systemSource;
	
	/**
	 * 来源单据编号
	 */
	private String orderNo;
	
	/**
	 * 交易类别
	 * 例如：申购：01，赎回：02
	 */
	private String type;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	
	/**
	 * 请求流水号
	 */
	private String requestNo;
	
	/**
	 * 支付备注
	 */
	private String remark;
	
	/**
	 * 订单描述
	 */
	private String describe;
	
	/**
	 * 下单时间
	 */
	private String orderTime;
	
	/**
	 * 结果码
	 */
	private String result;
	
	/**
	 * 支付时间
	 */
	private String payTime;
	
	/**
	 * 支付状态
	 */
	private String status;
	
	/**
	 * 对账码
	 */
	private String reconciliationCode;
	
	/**
	 * 对账结果状态
	 */
	private String reconciliationStatus;
	
	/**
	 * 当前页
	 */
	private int page=1;
	
	/**
	 * 每页显示的条数
	 */
	private int row=500;
	
	/**
	 * 总也数
	 */
	private int totalPage;
	
	/**
	 * 总条数
	 */
	private long total;
	
	/**
	 * 返回集合
	 */
	private List<TransactionResponse> rows;

}
