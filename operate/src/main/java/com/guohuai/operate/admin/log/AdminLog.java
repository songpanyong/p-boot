package com.guohuai.operate.admin.log;

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

import com.guohuai.operate.admin.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "T_OP_ADMIN_LOG")
public class AdminLog implements Serializable {

	private static final long serialVersionUID = 4371625102013036310L;

	public static final String TYPE_REGIST = "REGIST";
	public static final String TYPE_FREEZE = "FREEZE";
	public static final String TYPE_UNFREEZE = "UNFREEZE";
	public static final String TYPE_LOGIN = "LOGIN";
	public static final String TYPE_LOGOUT = "LOGOUT";
	public static final String TYPE_RESETPWD = "RESETPWD";
	public static final String TYPE_GENPWD = "GENPWD";

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String oid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adminOid", referencedColumnName = "oid")
	private Admin admin;
	
	private String type;
	
	private String operator;
	private String operateIp;
	private Timestamp updateTime;
	private Timestamp createTime;

}
