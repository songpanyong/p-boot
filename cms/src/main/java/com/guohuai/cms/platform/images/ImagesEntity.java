package com.guohuai.cms.platform.images;

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
@Table(name = "T_PLATFORM_IMAGES")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ImagesEntity extends UUID implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图片名称
	 */
	private String imgName;
	/**
	 * 图片路径
	 */
	private String imgUrl;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

}
