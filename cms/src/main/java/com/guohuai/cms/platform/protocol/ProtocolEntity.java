package com.guohuai.cms.platform.protocol;

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
import com.guohuai.cms.platform.protocol.type.ProtocolTypeEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 协议
 * @author xjj
 *
 */
@Entity
@Table(name = "T_PLATFORM_PROTOCOL")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ProtocolEntity extends UUID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 所属协议 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "protocolTypeId", referencedColumnName = "id")
	private ProtocolTypeEntity protocolType;
			
	/** 协议内容 */
	private String content;
	
	private Timestamp updateTime;
	private Timestamp createTime;

}
