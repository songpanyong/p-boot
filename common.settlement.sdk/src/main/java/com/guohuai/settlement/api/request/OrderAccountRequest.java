package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;
/**
 * @ClassName: OrderAccountRequest
 * @Description: 对账获取订单信息接口
 */
@Data
public class OrderAccountRequest  implements Serializable{
	private static final long serialVersionUID = 3558033106423990871L;
	
	/**
	 * 查询日期
	 * 格式：yyyy-MM-dd
	 */
	private String date;
	
	/**
	 * 查询偏移定位数
	 */
	private long countNum=0;
	/***
	 * 开始时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String beginTime;
	/***
	 * 结束时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String endTime;
	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 订单类型
	 */
	private String orderType;

}
