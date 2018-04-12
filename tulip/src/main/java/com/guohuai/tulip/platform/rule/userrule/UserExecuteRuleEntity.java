package com.guohuai.tulip.platform.rule.userrule;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "T_TULIP_USER_EXECUTE_RULE")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserExecuteRuleEntity extends UUID {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6901195036144955700L;
	
	private String userId;
	
	private String ruleId;
	
	private String itemValue;
	
	private String isdel;
	
	private Timestamp createTime;
	
}
