package com.guohuai.cms.platform.information;

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
@Table(name = "T_PLATFORM_INFORMATION")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InformationEntity extends UUID implements Serializable{
	private static final long serialVersionUID = 1L;
	

	/** 资讯状态-下架  */
	public static final String INFORMATION_status_off = "off";
	/** 资讯状态-发布  */
	public static final String INFORMATION_status_published = "published";
	/** 资讯状态-待发布  */
	public static final String INFORMATION_status_publishing = "publishing";
	/** 资讯状态-待审核  */
	public static final String INFORMATION_status_pending= "pending";
	/** 资讯状态-已驳回  */
	public static final String INFORMATION_status_refused = "reject";
	
	/** 审批结果状态-通过  */
	public static final String INFORMATION_reviewStatus_pass = "pass"; 
	/** 审批结果状态-驳回  */
	public static final String INFORMATION_reviewStatus_refused = "refused";
	
	/** 所属渠道 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelOid", referencedColumnName = "oid")
	private ChannelEntity channel;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 缩略图
	 */
	private String thumbnailUrl;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 发布者
	 */
	private String publisher;
	/**
	 * 发布时间
	 */
	private Timestamp publishTime;
	/**
	 * 编辑者
	 */
	private String editor;
	/**
	 * 编辑时间
	 */
	private Timestamp editTime;
	/**
	 * 审核者
	 */
	private String review;
	/**
	 * 审核时间
	 */
	private Timestamp reviewTime;
	/**
	 * 文章来源
	 */
	private String origin;
	
	private String reviewRemark;
	/**
	 * 文章链接
	 */
	private String url;
	/**
	 * 是否推荐2：否认 1： 是
	 */
	private Integer isHome;

}
