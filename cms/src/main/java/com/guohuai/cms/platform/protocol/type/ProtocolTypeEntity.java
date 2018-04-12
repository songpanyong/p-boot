package com.guohuai.cms.platform.protocol.type;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 协议类型
 * @author xjj
 *
 */
@Entity
@Table(name = "T_PLATFORM_PROTOCOL_TYPE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ProtocolTypeEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 协议类型ID */
	@Id
	private String id;
	
	/** 协议类型名称 */
	private String name;
}
