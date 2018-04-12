package com.guohuai.cms.platform.channel;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guohuai.cms.component.persist.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_CHANNEL")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ChannelEntity extends UUID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 编号 */
	private String code;
	
	/** 名称 */
	private String name;
	
	private Timestamp updateTime;
	
	private Timestamp createTime;
}
