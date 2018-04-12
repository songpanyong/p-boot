package com.guohuai.tulip.platform.eventAnno;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.guohuai.rules.event.EventAnno;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注册事件
 * @author suzhicheng
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@EventAnno("注册事件")
public class RegisterEvent extends CommonRuleProp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6096771494279681645L;
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
	private String eventType = EventConstants.EVENTTYPE_REGISTER;
	
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getFriendPhone() {
		return friendPhone;
	}

	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
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

}