package com.guohuai.points.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 交易流水请求参数
* @ClassName: AccountTransRequest 
* @Description: 
* @author CHENDONGHUI
* @date 2017年3月22日 上午11:34:41 
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
	 * 01：签到，02：卡券，03：充值，04：消费，05：过期， 06：撤单
	 */
	private String orderType; 
	/**
	 * 账户类型
	 */
	public String accountType;
	/**
	 * 关联产品、卡券编号
	 */
	private String relationProductNo; 
	/**
	 * 关联产品、卡券名称
	 */
	private String relationProductName; 
	/**
	 * 交易积分额
	 */
	private BigDecimal orderPoint;
	/**
	 * 交易后积分余额
	 */
	private BigDecimal balance;
	/**
	 * 备注
	 */
	private String remark; 
	/**
	 * 来源系统单据号
	 */
	private String orderNo; 
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
	 * 交易账户
	 */
	private String transAccountNo;
	/**
	 * 积分方向，增ADD 减REDUCE
	 */
	private String direction;
	/**
	 * 最小交易积分
	 */
	private BigDecimal minOrderPoint;
	/**
	 * 最大交易积分
	 */
	private BigDecimal maxOrderPoint;
	/**
	 * 开始时间yyyy-MM-dd HH:mm:ss
	 */
	private String beginTime;
	/**
	 * 结束时间yyyy-MM-dd HH:mm:ss
	 */
	private String endTime;
	/**
	 * 查询页数
	 */
	private int page=1;
	/**
	 * 查询条数
	 */
	private int row=10;
}
