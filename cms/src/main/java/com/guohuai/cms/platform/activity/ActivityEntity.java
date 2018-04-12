package com.guohuai.cms.platform.activity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guohuai.cms.component.persist.UUID;
import com.guohuai.cms.platform.channel.ChannelEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_ACTIVITY")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActivityEntity extends UUID implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 活动位置-左  */
	public static final String ACTIVITY_location_left = "left";
	/** 活动位置-右  */
	public static final String ACTIVITY_location_right = "right";
	/** 活动位置-轮播  */
	public static final String ACTIVITY_location_carousel = "carousel";
	/** 活动位置-我的页面活动  */
	public static final String ACTIVITY_location_mypage = "mypage";
	
	/** 活动状态-上架  */
	public static final String ACTIVITY_status_on = "on";
	/** 活动状态-下架  */
	public static final String ACTIVITY_status_off = "off";
	/** 活动状态-已审核  */
	public static final String ACTIVITY_status_reviewed = "reviewed";
	/** 活动状态-待审核  */
	public static final String ACTIVITY_status_pending= "pending";
	/** 活动状态-已驳回  */
	public static final String ACTIVITY_status_refused = "reject";
	
	/** 审批结果状态-通过  */
	public static final String ACTIVITY_reviewStatus_pass = "pass"; 
	/** 审批结果状态-驳回  */
	public static final String ACTIVITY_reviewStatus_refused = "refused";
	
	/** 所属渠道 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelOid", referencedColumnName = "oid")
	private ChannelEntity channel;
	
	/** 标题 */
	private String title;
	
	/** 图片 */
	private String picUrl;
	
	/** 位置 */
	private String location;
	
	/** 区分链接和调整 0：链接  1：跳转 */
	private int linkType;
	
	/** 链接 */
	private String linkUrl;
	
	/** 跳转的APP页面 */
	private String toPage;
	
	/** 状态 */
	private String status;
	
	/** 编辑者 */
	private String creator;
	
	/** 编辑时间 */
	private Timestamp createTime;
	
	/** 审核者 */
	private String review;
	
	/** 审核时间 */
	private Timestamp reviewTime;
	
	/** 发布者 */
	private String publisher;
	
	/** 发布时间 */
	private Timestamp publishTime;
	
	/** 审核意见 */
	private String reviewRemark;
	
	/** 活动开始时间 */
	private Timestamp beginTime;
	
	/** 活动结束时间 */
	private Timestamp endTime;
		
}
