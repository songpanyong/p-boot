package com.guohuai.cms.platform.actrule;

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
import com.guohuai.cms.platform.actrule.type.ActRuleTypeEntity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 活动规则
 * @author xjj
 *
 */
@Entity
@Table(name = "T_PLATFORM_ACTRULE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ActRuleEntity extends UUID implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 所属活动规则类型 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "actRuleTypeId", referencedColumnName = "id")
	private ActRuleTypeEntity actRuleType;
			
	/** 活动规则内容 */
	private String content;
	
	private Timestamp updateTime;
	private Timestamp createTime;

}
