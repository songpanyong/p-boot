package com.guohuai.cms.platform.legal.file;

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
import com.guohuai.cms.platform.legal.LegalEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "T_PLATFORM_LEGAL_FILE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class LegalFileEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 是否可用
	 */
	public static final String LEGAL_STATUS_disabled = "disabled";	// 已禁用
	public static final String LEGAL_STATUS_enabled = "enabled";		// 已启用
	
	/** 类型 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type", referencedColumnName = "oid")
	private LegalEntity type;
	
	/** 文件名称 */
	private String name;
	/** 文件地址 */
	private String fileUrl;
	/** 状态 */
	private String status;
	/** 操作人 */
	private String operator;
	/** 上传时间 */
	private Timestamp uploadTime;
	/** 生成时间 */
	private Timestamp createTime;
	/** 修改时间*/
	private Timestamp updateTime;
}
