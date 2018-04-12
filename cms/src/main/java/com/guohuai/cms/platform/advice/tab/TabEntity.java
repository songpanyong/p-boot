package com.guohuai.cms.platform.advice.tab;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_ADVICE_TAB")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TabEntity extends UUID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 删除状态-已删除 */
	public static final String TAB_delStatus_yes = "yes";
	/** 删除状态-未删除 */
	public static final String TAB_delStatus_no = "no";
	
	/**
	 * 标签名称
	 */
	private String name;
	/**
	 * 删除状态
	 */
	private String delStatus;
	
	private String operator;
	
	private Timestamp updateTime;
	
	private Timestamp createTime;

}
