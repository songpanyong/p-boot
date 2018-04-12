package com.guohuai.tulip.platform.userinvest;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

public class UserInvestReq {
	String oid;
	/**
	 * 友人编号
	 */
	String friendId;
	/**
	 * 用户编号
	 */
	String userId;
	/**
	 * 好友联系方式
	 */
	String friendPhone;
	/**
	 * 用户联系方式
	 */
	String userPhone;
	/**
	 * 注册时间
	 */
	Timestamp createTime;
	/**
	 * 推广数量
	 */
	Integer friends;
	/**
	 * 推广渠道类型
	 */
	String type;
	/**
	 * 累计返佣金额
	 */
	BigDecimal totalReward;
	/**
	 * 设置返佣金额
	 */
	@NotNull(message = "返佣金额不能为空")
	BigDecimal channelAmount;
	/**
	 * 渠道审核人(升渠道/降推广人)
	 */
	String operateName;
	/**
	 * 获取 oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * 设置 oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
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
	 * 获取 friendPhone
	 */
	public String getFriendPhone() {
		return friendPhone;
	}
	/**
	 * 设置 friendPhone
	 */
	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
	}
	/**
	 * 获取 userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * 设置 userPhone
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * 获取 createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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
	 * 获取 operateName
	 */
	public String getOperateName() {
		return operateName;
	}
	/**
	 * 设置 operateName
	 */
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	
	
}
