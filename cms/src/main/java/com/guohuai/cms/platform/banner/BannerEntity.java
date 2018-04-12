package com.guohuai.cms.platform.banner;

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
import com.guohuai.cms.platform.channel.ChannelEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_BANNER")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class BannerEntity extends UUID implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 审核状态-待审核 */
	public static final String BANNER_approveStatus_toApprove = "toApprove";
	/** 审核状态-拒绝 */
	public static final String BANNER_approveStatus_refused = "refused";
	/** 审核状态-通过 */
	public static final String BANNER_approveStatus_pass = "pass";
	
	/** 发布状态-待发布 */
	public static final String BANNER_releaseStatus_wait = "wait";
	/** 发布状态-未发布 */
	public static final String BANNER_releaseStatus_no = "no";
	/** 发布状态-已发布 */
	public static final String BANNER_releaseStatus_ok = "ok";

	/** 所属渠道 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelOid", referencedColumnName = "oid")
	private ChannelEntity channel;
	
	/** 标题 */
	private String title;
	
	/** 图片路径 */
	private String imageUrl;
	
	/** 连接地址 */
	private String linkUrl;
	
	/** 区分链接和调整 0：链接  1：跳转 */
	private int isLink;
	
	/** 跳转的页面 */
	private String toPage;
	
	/** 审核状态 */
	private String approveStatus;
	
	/** 审核意见 */
	private String remark;
	
	/** 发布状态 */
	private String releaseStatus;
	
	/** 排序 */
	private Integer sorting;
	
	/** 编辑者 */
	private String operator;
	
	/** 审核者 */
	private String approveOpe;
	
	/** 发布者 */
	private String releaseOpe;	
	
	/** 审批时间 */
	private Timestamp approveTime;
	
	/** 上下架时间 */
	private Timestamp releaseTime;
	
	private Timestamp updateTime;
	
	private Timestamp createTime;
	
}
