package com.guohuai.cms.platform.version;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "T_PLATFORM_VERSION")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class VersionEntity extends UUID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 活动状态-已发布  */
	public static final String VERSION_status_on = "on";

	/** 活动状态-已审核  */
	public static final String VERSION_status_reviewed = "reviewed";
	/** 活动状态-未审核  */
	public static final String VERSION_status_pending= "pending";
	/** 活动状态-已驳回  */
	public static final String VERSION_status_refused = "reject";
	
	/** 审批结果状态-通过  */
	public static final String VERSION_reviewStatus_pass = "pass"; 
	/** 审批结果状态-驳回  */
	public static final String VERSION_reviewStatus_refused = "refused";
	
	public static final String VERSION_system_ios="ios";
	public static final String VERSION_system_android="android";
	public static final String VERSION_system_increment="increment";
	
	public static final String VERSION_upgradeType_increment="increment";
	
	/**
	 * 版本号
	 */
	private String versionNo;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String fileUrl;
	/**
	 * 版本大小
	 */
	private String versionSize;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 升级类型  increment ：增量 version：现有版本
	 */
	private String upgradeType;
	/**
	 * 是否强制升级0：否 1：是
	 */
	private Integer compulsory;
	/**
	 * 提醒更新频率
	 */
	private Integer checkInterval;
	/**
	 * 系统版本类型 ios:ios,android:安卓,increment:增量
	 */
	private String system;
	/**
	 * 预计发布时间
	 */
	private Date expectPublishTime;	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 编辑者
	 */
	private String creator;
	/**
	 * 编辑时间
	 */
	private Timestamp createTime;
	/**
	 * 审核者
	 */
	private String review;
	/**
	 * 审核时间
	 */
	private Timestamp reviewTime;
	/**
	 * 发布者
	 */
	private String publisher;
	/**
	 * 发布时间
	 */
	private Timestamp publishTime;
	/**
	 * 审核意见
	 */
	private String reviewRemark;
	
}
