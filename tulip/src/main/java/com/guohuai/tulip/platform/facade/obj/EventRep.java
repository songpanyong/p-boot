package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EventRep extends BaseResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 279678675333809785L;
	/**
	 * 活动ID
	 */
	private String eventId;
	/** 卡券金额 */
	private BigDecimal money;
	/** 卡券体验天数 */
	private Integer validPeriod;
	
	/**
	 * 获取 money
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置 money 
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取 validPeriod
	 */
	public Integer getValidPeriod() {
		return validPeriod;
	}
	/**
	 * 设置 validPeriod 
	 */
	public void setValidPeriod(Integer validPeriod) {
		this.validPeriod = validPeriod;
	}
	/**
	 * 获取 eventId
	 */
	public String getEventId() {
		return eventId;
	}
	/**
	 * 设置 eventId
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
}
