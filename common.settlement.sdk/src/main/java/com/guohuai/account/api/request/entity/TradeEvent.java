package com.guohuai.account.api.request.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: xueyunlong
 * @Description: 交易事件
 * @Date:Created in 2018/1/25 19:55.
 * @Modified By:
 */
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class TradeEvent implements Serializable {
	
	private static final long serialVersionUID = -8472725768159438360L;
	/**
	 * 事件类型
	 */
	private String eventType,mainEvent;
	/**
	 * 单据金额
	 */
	private BigDecimal balance=BigDecimal.ZERO;

	@Override
	public String toString() {
		return "TradeEvent{" +
				"eventType='" + eventType + '\'' +
				", mainEvent='" + mainEvent + '\'' +
				", balance=" + balance +
				'}';
	}
}
