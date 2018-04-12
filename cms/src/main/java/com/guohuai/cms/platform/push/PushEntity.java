package com.guohuai.cms.platform.push;

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
@Table(name = "T_PLATFORM_PUSH")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class PushEntity extends UUID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PUSH_clientSys_android = "Android";
	
	/** 活动状态-已发布  */
	public static final String PUSH_status_on = "on";

	/** 活动状态-已审核  */
	public static final String PUSH_status_reviewed = "reviewed";
	/** 活动状态-未审核  */
	public static final String PUSH_status_pending= "pending";
	/** 活动状态-已驳回  */
	public static final String PUSH_status_refused = "reject";
	
	/** 审批结果状态-通过  */
	public static final String PUSH_reviewStatus_pass = "pass"; 
	/** 审批结果状态-驳回  */
	public static final String PUSH_reviewStatus_refused = "refused";
	
	public static final String PUSH_pushType_all = "all";
	public static final String PUSH_pushType_person = "person";
	public static final String PUSH_pushType_group = "team";
	
	public static final String PUSH_type_mail = "mail";	// 站内信
	
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 推送链接地址
	 */
	private String url;
	
	/**
	 * 类型
	 */
	private String type;
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
	private String pusher;
	/**
	 * 发布时间
	 */
	private Timestamp pushTime;
	/**
	 * 审核意见
	 */
	private String reviewRemark;
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 推送类型   个人  全站
	 */
	private String pushType;
	
	/**
	 * 推送目标用户
	 */
	private String pushUserOid;
	
	/**
	 * 推送目标用户账号
	 */
	private String pushUserAcc;

	/**
	 * 标签编码
	 */
	private String labelCode;
}
