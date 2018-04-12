package com.guohuai.payadapter.redeem;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.Data;

@Data
@Entity
@Table(
    name = "t_bank_concurrent_manager"
)
public class ConcurrentManager extends UUID implements Serializable{
	private static final long serialVersionUID = -9204428670349628057L;
	private String taskTag;
	private String status; //1:为master 0:slave
	private String host;
	private Timestamp createTime;
	private Timestamp updateTime;

}
