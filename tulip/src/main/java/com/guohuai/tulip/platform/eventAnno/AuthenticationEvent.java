package com.guohuai.tulip.platform.eventAnno;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.guohuai.rules.event.EventAnno;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 实名认证事件
 * 
 * @author suzhicheng
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EventAnno("实名认证事件")
@Component
public class AuthenticationEvent extends CommonRuleProp {

	/** 姓名 */
	private String name;
	/** 生日 */
	private Date birthday;
	private String eventType = EventConstants.EVENTTYPE_AUTHENTICATION;
	

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
