package com.guohuai.cms.platform.legal;

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
@Table(name = "T_PLATFORM_LEGAL")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class LegalEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 是否可用
	 */
	public static final String LEGAL_STATUS_disabled = "disabled";	// 已禁用
	public static final String LEGAL_STATUS_enabled = "enabled";		// 已启用
	
	/** 编号 */
	private String code;
	
	/** 类型名称 */
	private String name;
	/** 状态 */
	private String status;
	/** 操作人 */
	private String operator;
	/** 生成时间 */
	private Timestamp createTime;
	/** 修改时间*/
	private Timestamp updateTime;
}
