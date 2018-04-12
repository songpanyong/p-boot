package com.guohuai.cms.platform.share;

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
@Table(name = "T_PLATFORM_SHARECONFIG")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ShareConfigEntity extends UUID implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 946906009568139991L;
	
	public static final String SHARECONFIG_pageCode_invite = "invite";
	public static final String SHARECONFIG_pageCode_regist = "regist";
	public static final String SHARECONFIG_pageCode_specialSubject = "specialSubject";
	public static final String SHARECONFIG_pageCode_else = "else";
	
	/**
	 * 页面CODE
	 */
	private String pageCode;
	
	/**
	 * 页面类型
	 */
	private String pageType;
	
	/**
	 * 页面名称
	 */
	private String pageName;
	
	/**
	 * 链接
	 */
	private String shareUrl;
	
	/**
	 * 分享标题
	 */
	private String shareTitle;
	
	/**
	 * 分享文字
	 */
	private String shareWords;
	
	
	/** 生成时间 */
	private Timestamp createTime;
	/** 修改时间*/
	private Timestamp updateTime;
}
