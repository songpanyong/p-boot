package com.guohuai.operate.admin.role;

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
import com.guohuai.operate.role.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_OP_ADMIN_ROLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRole {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String oid;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "adminOid", referencedColumnName = "oid")
	private Admin admin;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleOid", referencedColumnName = "oid")
	private Role role;
	private String operator;
	private Timestamp updateTime;
	private Timestamp createTime;

}
