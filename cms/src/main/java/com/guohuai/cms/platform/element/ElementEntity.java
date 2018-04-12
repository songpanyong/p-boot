package com.guohuai.cms.platform.element;

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
@Table(name = "T_PLATFORM_CONFIGURABLE_ELEMENTS")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ElementEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 类型
	 */
	public static final String ELEMENT_TYPE_BUTTON = "button";	// 按钮
	public static final String ELEMENT_TYPE_LINK = "link";		// 连接
	public static final String ELEMENT_TYPE_DATA = "data";		// 数据
	/**
	 * 是否显示
	 */
	public static final String ELEMENT_ISDISPLAY_YES = "yes";	// 显示
	public static final String ELEMENT_ISDISPLAY_NO = "no";		// 不显示
	
	/** 编号 */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 类型 */
	private String type;
	
	/** 是否显示 */
	private String isDisplay;
	/** 内容 */
	private String content;
	/** 操作人 */
	private String operator;
	/** 创建人 */
	private String creator;
	/** 生成时间 */
	private Timestamp createTime;
	/** 修改时间*/
	private Timestamp updateTime;
}
