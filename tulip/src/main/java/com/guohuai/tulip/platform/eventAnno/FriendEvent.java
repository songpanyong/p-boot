package com.guohuai.tulip.platform.eventAnno;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 推荐人事件
 * 
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("推荐人事件")
@Component
public class FriendEvent extends CommonRuleProp {
	/**
	 * 友人编号
	 */
	private String friendId;
	/**
	 * 好友联系方式
	 */
	private String friendPhone;
	/**
	 * 用户联系方式
	 */
	private String phone;
	/**
	 * 用户生日
	 */
	private Date birthday;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 注册时间
	 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());
	/** 事件类型 */
	private String eventType = EventConstants.EVENTTYPE_FRIEND;
	
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

}
