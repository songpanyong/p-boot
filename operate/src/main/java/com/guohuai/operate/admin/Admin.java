package com.guohuai.operate.admin;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.guohuai.operate.system.Sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_OP_ADMIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin implements Serializable {

	private static final long serialVersionUID = 140194393240628514L;

	/** 注册来源--用户注册 */
	public static final String RESOURCES_USERREGIST = "USERREGIST";
	/** 注册来源--平台注册 */
	public static final String RESOURCES_PLATFORM = "PLATFORM";

	/** 账号状态--正常 */
	public static final String STATUS_VALID = "VALID";
	/** 账号状态--无效 */
	public static final String STATUS_INVALID = "INVALID";
	/** 账号状态--冻结 */
	public static final String STATUS_FREEZE = "FREEZE";
	/** 账号状态--使用期已过 */
	public static final String STATUS_EXPIRED = "EXPIRED";
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String oid;
	private String sn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "systemOid", referencedColumnName = "oid")
	private Sys system;
	
	private String account;
	
	@Type(type = "password")
	private String password;
	
	private String name;
	private String phone;
	private String email;
	private String resources;
	private String comment;
	private String status;
	private String loginIp;
	private Timestamp loginTime;
	private String operator;
	private Timestamp validTime;
	private Timestamp updateTime;
	private Timestamp createTime;

}
