package com.guohuai.tulip.platform.event;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventDetailRep extends BaseResp{

	private Integer eventCount, isseCoupon,useCoupon;//活动数、下发卡券数、卡券使用量

	/**
	 * 获取 eventCount
	 */
	public Integer getEventCount() {
		return eventCount;
	}

	/**
	 * 设置 eventCount
	 */
	public void setEventCount(Integer eventCount) {
		this.eventCount = eventCount;
	}

	/**
	 * 获取 isseCoupon
	 */
	public Integer getIsseCoupon() {
		return isseCoupon;
	}

	/**
	 * 设置 isseCoupon
	 */
	public void setIsseCoupon(Integer isseCoupon) {
		this.isseCoupon = isseCoupon;
	}

	/**
	 * 获取 useCoupon
	 */
	public Integer getUseCoupon() {
		return useCoupon;
	}

	/**
	 * 设置 useCoupon
	 */
	public void setUseCoupon(Integer useCoupon) {
		this.useCoupon = useCoupon;
	}

	
}
