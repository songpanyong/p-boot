package com.guohuai.cms.platform.information.type;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.cms.component.persist.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_INFORMATION_TYPE")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class InformationTypeEntity extends UUID implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 资讯类型启用状态--开启 */
	public static final String INFORMATIONTYPE_status_oN = "1";
	/** 资讯类型启用状态--关闭 */
	public static final String INFORMATIONTYPE_status_off = "0";
	
	/** 资讯类型名称 */
	private String name;
	
	/** 排序 */
	private Integer sort;
	
	/** 1-开启 0-关闭 */
	private Integer status;

}
