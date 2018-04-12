package com.guohuai.tulip.platform.event;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EventAddPojo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7575560552559874743L;
	String oid;
	/**
	 * 活动标题
	 */
	String title;
	/**
	 * 活动内容描述
	 */
	String content;
	/**
	 * 表达式
	 */
	String expression;
	/**
	 * 活动起始时间
	 */
	Date start;
	/**
	 * 活动结束时间
	 */
	Date finish;
	/**
	 * 类型
	 */
	String type;
	/**
	 * 奖励条件
	 */
	List<RewardRuleReq> rrList = new ArrayList<RewardRuleReq>();
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
	 * 获取 content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * 设置 expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
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
	 * 获取 rrList
	 */
	public List<RewardRuleReq> getRrList() {
		return rrList;
	}
	/**
	 * 设置 rrList
	 */
	public void setRrList(List<RewardRuleReq> rrList) {
		this.rrList = rrList;
	}
	
	
}
