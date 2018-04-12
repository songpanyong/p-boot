package com.guohuai.operate.role;

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

import com.guohuai.operate.system.Sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_OP_ROLE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	private String oid;
	private String name;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "systemOid", referencedColumnName = "oid")
	private Sys system;
	private String operator;
	private Timestamp updateTime;
	private Timestamp createTime;

}
