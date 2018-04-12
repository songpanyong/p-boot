package com.guohuai.cms.platform.version;

import java.sql.Timestamp;
import java.util.Date;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class VersionQueryRep {
	
	private String oid, title, status, publisher, fileName, fileUrl, creator, 
				   review, versionNo, description, reviewRemark, system;
	
	private Timestamp createTime, publishTime, reviewTime;
	
	/**
	 * 版本大小
	 */
	private String versionSize;
	
	/**
	 * 预计发布日期
	 */
	private Date expectPublishTime;
	
	/**
	 * 升级类型
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

}
