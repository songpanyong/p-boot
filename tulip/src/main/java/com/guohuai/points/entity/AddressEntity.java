package com.guohuai.points.entity;

import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "T_TULIP_TAKE_ADDRESS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressEntity extends UUID  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3064658040506542193L;
	/**
	 * 用户oid
	 */
	private String userOid;
	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 收货地址
	 */
	private String takeAddress;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 是否为默认地址(1默认，0否)
	 */
	private int isDefault;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 是否删除（yes：是  no：否）
	 */
	private String isdel;
	/**
	 * 邮编
	 */
	private String zipCode;

}
