package com.guohuai.account.api.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.guohuai.account.api.response.entity.AccOrderDto;

/**
 * 账户订单返回参数
 * @ClassName: NewUserResponse
 * @Description:
 * @author longyunbo
 * @date 2016年11月8日 上午10:19:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderQueryResponse extends BaseResponse {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 495630589392544565L;

	public OrderQueryResponse(AccOrderDto entity) {
		this.requestNo = entity.getRequestNo();
		this.systemSource = entity.getSystemSource();
		this.orderNo = entity.getOrderNo();
		this.userOid = entity.getUserOid();
		this.orderType = entity.getOrderType();
		this.relationProductNo = entity.getRelationProductNo();
		this.relationProductName = entity.getRelationProductName();
		this.balance = entity.getBalance();
		this.orderStatus = entity.getOrderStatus();
		this.orderDesc = entity.getOrderDesc();
		this.inputAccountNo = entity.getInputAccountNo();
		this.outpuptAccountNo = entity.getOutpuptAccountNo();
		this.submitTime = entity.getSubmitTime();
		this.receiveTime = entity.getReceiveTime();
		this.businessStatus = entity.getBusinessStatus();
		this.financeStatus = entity.getFinanceStatus();
		this.remark = entity.getRemark();
		this.createTime = entity.getCreateTime();
		this.updateTime = entity.getUpdateTime();
		this.phone = entity.getPhone();
	}

	private String requestNo;
	private String systemSource; // 来源系统类型
	private String orderNo; // 来源系统单据号
	private String userOid; // 用户ID
	private String orderType; // 申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
	private String relationProductNo; // 关联产品编号
	private String relationProductName; // 关联产品名称
	private BigDecimal balance; // 单据金额
	private String orderStatus; // 订单状态
	private String orderDesc; // 订单描述
	private String inputAccountNo; // 入账账户，根据单据类型，做转账时用
	private String outpuptAccountNo; // 出账账户，根据单据类型，做转账时用
	private String submitTime; // 单据时间
	private String receiveTime; // 系统接收时间
	private String businessStatus; // 业务系统对账状态
	private String financeStatus; // 账务系统对账状态
	private String remark; // 订单描述
	private String createTime;
	private String updateTime;
	private String phone;
}