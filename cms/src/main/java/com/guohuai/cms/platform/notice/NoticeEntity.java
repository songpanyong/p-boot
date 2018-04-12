package com.guohuai.cms.platform.notice;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;
import com.guohuai.cms.platform.channel.ChannelEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "T_PLATFORM_NOTICE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class NoticeEntity extends UUID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 审核状态-待审核 */
	public static final String NOTICE_approveStatus_toApprove = "toApprove";
	/** 审核状态-拒绝 */
	public static final String NOTICE_approveStatus_refused = "refused";
	/** 审核状态-通过 */
	public static final String NOTICE_approveStatus_pass = "pass";
	
	/** 发布状态-待发布 */
	public static final String NOTICE_releaseStatus_wait = "wait";
	/** 发布状态-已下架 */
	public static final String NOTICE_releaseStatus_no = "no";
	/** 发布状态-已上架 */
	public static final String NOTICE_releaseStatus_ok = "ok";
	
	/** 角标-无 */
	public static final String NOTICE_subscript_no = "无";
	/** 角标-新 */
	public static final String NOTICE_subscript_new = "New";
	/** 角标-热 */
	public static final String NOTICE_subscript_hot = "Hot";
	
	/** 首页推荐-不是首页 */
	public static final String NOTICE_page_no = "no";
	/** 首页推荐-是首页 */
	public static final String NOTICE_page_is = "is";
	
	/** 是否置顶-置顶*/
	public static final String NOTICE_top_1 = "1";
	/** 是否置顶-不置顶*/
	public static final String NOTICE_top_2 = "2";
	
	/** 所属渠道 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelOid", referencedColumnName = "oid")
	private ChannelEntity channel;
	
	/** 标题 */
	private String title;
	
	/** 链接 */
	private String linkUrl;
	
	/** 跳转的html */
	private String linkHtml;
	
	/** 角标 */
	private String subscript;
	
	/** 发布来源 */
	private String sourceFrom;
	
	/** 首页推荐 */
	private String page;
	
	/** 置顶 */
	private String top;
	
	/** 审核状态 */
	private String approveStatus;
	
	/** 审核意见 */
	private String remark;
	
	/** 发布状态 */
	private String releaseStatus;
	
	/** 编辑者 */
	private String operator;
	
	/** 审核者 */
	private String approveOpe;
	
	/** 发布者 */
	private String releaseOpe;
	
	/** 审批时间 */
	private Timestamp approveTime;
	
	/** 发布时间 */
	private Timestamp releaseTime;
	
	/** 上架时间 */
	private Date onShelfTime;
	
	private Timestamp updateTime;
	
	private Timestamp createTime;
}
