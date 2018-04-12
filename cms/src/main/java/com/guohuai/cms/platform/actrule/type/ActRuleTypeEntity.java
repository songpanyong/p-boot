package com.guohuai.cms.platform.actrule.type;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动规则类型
 * @author xjj
 *
 */
@Entity
@Table(name = "T_PLATFORM_ACTRULE_TYPE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActRuleTypeEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 活动规则类型ID */
	@Id
	private String id;
	
	/** 活动规则类型名称 */
	private String name;
}
