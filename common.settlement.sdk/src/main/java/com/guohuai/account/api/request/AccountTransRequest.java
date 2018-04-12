package com.guohuai.account.api.request;

import com.guohuai.account.api.request.entity.TradeEvent;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 账户交易请求参数
* @ClassName: AccountTransRequest 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:10:41 
*
 */
@Data
public class AccountTransRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 2136893610065389423L;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 用户类型
	 * 投资人账户:T1、发行人账户:T2、平台账户:T3  
	 */
	private String userType; 
	/**
	 * 单据类型
	 */
	private String orderType; 
	
	/**
	 * 产品类别 活期  01、定期 06
	 * 在申购 赎回 增加发行人份额记账 派系时需要传入 
	 */
	private String productType;
	/**
	 * 关联产品编号
	 */
	private String relationProductNo; 
	/**
	 * 关联产品名称
	 */
	private String relationProductName;

	/**
	 * 产品户号
	 */
	private  String productAccountNo;
	/**
	 * 交易额
	 */
	private BigDecimal balance;	
	/**
	 * 代金券
	 */
	private BigDecimal voucher;
	
	/**
	 * 手续费
	 */
	private BigDecimal fee=BigDecimal.ZERO;
	
	/**
	 * 冻结金额（用于续投、转换）
	 */
	private BigDecimal frozenBalance=BigDecimal.ZERO;
	
	
	/**
	 * 交易用途(在赎回中：T0、T1)
	 */
	private String remark; 
	/**
	 * 来源系统单据号
	 */
	private String orderNo; 
	
    /**
     * 原订单号
     */
    private String oldOrderNo;
    
	/**
	 * 来源系统类型
	 */
	private String systemSource; 
	/**
	 * 请求流水号
	 */
	private String requestNo;
	/**
	 * 定单描述
	 */
	private String orderDesc;
	
	/**
	 * 转出产品编号
	 */
	private String outputRelationProductNo;
	
	/**
	 * 转出产品名称
	 */
	private String outputRelationProductName;
	
	/**
	 * 业务系统订单创建时间 YYYY-MM-DD HH:mm:ss
	 */
	private String orderCreatTime;
	
	private String errorMessage,returnCode;
	
	/**
	 * 交易账户类型 
	 */
	private String transAccountType;

	/**
	 * 事件类型
	 */
	private List<TradeEvent> tradeEvents;
}