package com.guohuai.tulip.platform.eventAnno;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.BaseEvent;
import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 员工认证事件
 * 
 * @author hugo
 *	
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("员工认证事件")
@Component
public class EmployeeEvent extends BaseEvent {

	/** 用户id */
	private String userId;
	/** 事件类型 */
	private String eventType = "employee";
	
	// 匹配规则的字段
	/**
	 * 累计推荐人数量
	 */
	@EventAnno("累计推荐人数量")
	private Integer friends = 0;
	/**
	 * 累计投资额度
	 */
	@EventAnno("累计投资额度")
	private BigDecimal investAmount = BigDecimal.ZERO;
	/**
	 * 累计投资次数
	 */
	@EventAnno("累计投资次数")
	private Integer investCount = 0;
	/**
	 * 投资额度
	 */
	@EventAnno("投资额度")
	private BigDecimal orderAmount = BigDecimal.ZERO;
	/**
	 * 首投金额
	 */
	@EventAnno("首投金额")
	private BigDecimal firstInvestAmount = BigDecimal.ZERO;
	
	@EventAnno("产品列表")
	private String productId;
	
	@EventAnno("单笔投资额度")
	private BigDecimal singleInvestAmount = BigDecimal.ZERO;
	
	@EventAnno("用户所在组")
	private List<String> userGroup = new ArrayList<String>();
	
	/**
	 * 注册时间
	 */
	@EventAnno("注册时间")
	private Date registerTime;

	/**
	 * 获取 userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置 userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 friends
	 */
	public Integer getFriends() {
		return friends;
	}

	/**
	 * 设置 friends
	 */
	public void setFriends(Integer friends) {
		this.friends = friends;
	}

	/**
	 * 获取 investAmount
	 */
	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	/**
	 * 设置 investAmount
	 */
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	/**
	 * 获取 investCount
	 */
	public Integer getInvestCount() {
		return investCount;
	}

	/**
	 * 设置 investCount
	 */
	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	/**
	 * 获取 orderAmount
	 */
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	/**
	 * 设置 orderAmount
	 */
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * 获取 firstInvestAmount
	 */
	public BigDecimal getFirstInvestAmount() {
		return firstInvestAmount;
	}

	/**
	 * 设置 firstInvestAmount
	 */
	public void setFirstInvestAmount(BigDecimal firstInvestAmount) {
		this.firstInvestAmount = firstInvestAmount;
	}

	/**
	 * 获取 registerTime
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * 设置 registerTime
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * 获取 eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 设置 eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public List<String> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(List<String> userGroup) {
		this.userGroup = userGroup;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getSingleInvestAmount() {
		return singleInvestAmount;
	}

	public void setSingleInvestAmount(BigDecimal singleInvestAmount) {
		this.singleInvestAmount = singleInvestAmount;
	}

}
