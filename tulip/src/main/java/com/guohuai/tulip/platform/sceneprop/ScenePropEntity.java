package com.guohuai.tulip.platform.sceneprop;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 场景属性实体
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_SCENE_PROP")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ScenePropEntity extends UUID {

	public static final String ISDEL_YES="yes";
	
	public static final String ISDEL_NO="no";
	
	private static final long serialVersionUID = 1972414236116198001L;
	/** 场景名称 */
	String name;
	/** 场景描述 */
	String description;
	/** 场景编号 */
	String code;
	/** 删除标志 */
	String isdel;
	/** 类型 */
	String type;
	
	/**
	 * 获取 name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取 isdel
	 */
	public String getIsdel() {
		return isdel;
	}
	/**
	 * 设置 isdel
	 */
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}
