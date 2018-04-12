package com.guohuai.account.api.request;

import com.guohuai.account.api.request.entity.TradeEvent;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: AccountTransferRequest
 * @Description: 账户交易请求参数
 * @author chendonghui
 * @date 2018年1月22日 上午10:01:12
 */
@Data
public class AccountTransferRequest implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -2841303548504156752L;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 发行人Id
	 */
	private String publisherUserOid;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 订单描述
	 */
	private String orderDesc;
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
	 * 业务系统订单创建时间 YYYY-MM-DD HH:mm:ss
	 */
	private String orderCreatTime;
	/**
	 * 登账事件类型List
	 */
	private List<TradeEvent> eventList;
	/**
	 * 产品户
	 */
	private String productAccountNo;

	/**
	 * 原订单号
	 */
	private String origOrderNo;

	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 出款账户号
	 */
	private String outputAccountNo;
	
	/**
	 * 入款账户号
	 */
	private String inputAccountNo;
	
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	
	/**
	 * 关联产品号
	 */
	private String relationProductNo;

	@Override
	public String toString() {
		return "AccountTransferRequest [userOid=" + userOid
				+ ", publisherUserOid=" + publisherUserOid + ", remark="
				+ remark + ", orderDesc=" + orderDesc + ", orderNo=" + orderNo
				+ ", systemSource=" + systemSource + ", requestNo=" + requestNo
				+ ", orderCreatTime=" + orderCreatTime + ", eventList="
				+ eventList + ", productAccountNo=" + productAccountNo
				+ ", origOrderNo=" + origOrderNo + ", orderType=" + orderType
				+ ", outputAccountNo=" + outputAccountNo + ", inputAccountNo="
				+ inputAccountNo + ", relationProductName="
				+ relationProductName + ", relationProductNo="
				+ relationProductNo + "]";
	}
	
}