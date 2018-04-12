package com.guohuai.tulip.platform.event.rule;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class EventRuleQueryRep {

	private String oid, status, active, createUser, updateUser, remark, description, title;
	private Timestamp createTime, updateTime;
	private Date start, finish;
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
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取 active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 设置 active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 获取 createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置 createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取 updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置 updateUser
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取 remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * 获取 updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置 updateTime
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取 start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * 设置 start
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * 获取 finish
	 */
	public Date getFinish() {
		return finish;
	}
	/**
	 * 设置 finish
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	
	
}
