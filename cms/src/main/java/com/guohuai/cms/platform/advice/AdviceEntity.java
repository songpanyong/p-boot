package com.guohuai.cms.platform.advice;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;
import com.guohuai.cms.platform.advice.tab.TabEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_ADVICE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class AdviceEntity extends UUID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** 意见反馈处理状态-已处理  */
	public static final String ADVICE_dealStatus_ok = "ok";
	/** 意见反馈处理状态-未处理  */
	public static final String ADVICE_dealStatus_no = "no";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tabOid", referencedColumnName = "oid")
	private TabEntity tab;
	/**
	 * 反馈者ID
	 */
	private String userID;
	/**
	 * 反馈者姓名
	 */
	private String userName;
	/**
	 * 反馈机型
	 */
	private String phoneType;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 反馈时间
	 */
	private Timestamp createTime;
	
	private String operator;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 处理时间
	 */
	private Timestamp dealTime;

}
