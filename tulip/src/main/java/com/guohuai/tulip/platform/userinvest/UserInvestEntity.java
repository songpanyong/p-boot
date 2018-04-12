package com.guohuai.tulip.platform.userinvest;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户投资日志实体
 * 
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_USER_INVEST_LOG")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserInvestEntity extends UUID {
	private static final long serialVersionUID = 1680790820263715374L;
	/** 渠道 */
	public static final String TYPE_CHANNEL = "channel";
	/** 推荐人 */
	public static final String TYPE_REFEREE = "referee";
	
	/** 用户ID */
	String userId;
	/** 投资金额 */
	String auditor;//渠道类型审核人(升渠道/降推广人)
	BigDecimal investAmount = BigDecimal.ZERO;
	/** 推荐人数 */
	Integer friends = 0;
	/** 推荐人数 */
	Integer investDuration = 0;
	
	Integer investCount = 0;
	
	Date firstInvestTime;
	Timestamp registerTime;
	
	Timestamp auditorTime;//渠道类型审核时间
	/** 首次投资金额 */
	BigDecimal firstInvestAmount = BigDecimal.ZERO;
	/**
	 * 推广渠道类型
	 */
	String type;
	/**
	 * 累计返佣金额
	 */
	BigDecimal totalReward = BigDecimal.ZERO;
	/**
	 * 友人编号
	 */
	String friendId;
	/**
	 * 用户联系方式
	 */
	String phone;
	/**
	 * 用户生日
	 */
	Date birthday;
	/**
	 * 用户名称
	 */
	String name;
	/**
	 * 渠道金额
	 */
	BigDecimal channelAmount = BigDecimal.ZERO;

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
	 * 获取 investDuration
	 */
	public Integer getInvestDuration() {
		return investDuration;
	}

	/**
	 * 设置 investDuration
	 */
	public void setInvestDuration(Integer investDuration) {
		this.investDuration = investDuration;
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
	 * 获取 firstInvestTime
	 */
	public Date getFirstInvestTime() {
		return firstInvestTime;
	}

	/**
	 * 设置 firstInvestTime
	 */
	public void setFirstInvestTime(Date firstInvestTime) {
		this.firstInvestTime = firstInvestTime;
	}

	/**
	 * 获取 registerTime
	 */
	public Timestamp getRegisterTime() {
		return registerTime;
	}

	/**
	 * 设置 registerTime
	 */
	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
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
	 * 获取 friendId
	 */
	public String getFriendId() {
		return friendId;
	}

	/**
	 * 设置 friendId
	 */
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	/**
	 * 获取 phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置 phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取 birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * 设置 birthday
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 获取 name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 totalReward
	 */
	public BigDecimal getTotalReward() {
		return totalReward;
	}

	/**
	 * 设置 totalReward
	 */
	public void setTotalReward(BigDecimal totalReward) {
		this.totalReward = totalReward;
	}

	/**
	 * 获取 channelAmount
	 */
	public BigDecimal getChannelAmount() {
		return channelAmount;
	}

	/**
	 * 设置 channelAmount
	 */
	public void setChannelAmount(BigDecimal channelAmount) {
		this.channelAmount = channelAmount;
	}
	/**
	 * 获取 auditor
	 */
	public String getAuditor() {
		return auditor;
	}
	/**
	 * 设置 auditor
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	/**
	 * 获取 auditorTime
	 */
	public Timestamp getAuditorTime() {
		return auditorTime;
	}
	/**
	 * 设置 auditorTime
	 */
	public void setAuditorTime(Timestamp auditorTime) {
		this.auditorTime = auditorTime;
	}
}
