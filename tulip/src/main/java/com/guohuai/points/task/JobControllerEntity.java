package com.guohuai.points.task;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.guohuai.basic.component.ext.hibernate.UUID;


@Entity
@Table(name = "T_TULIP_JOB_CONTROLLER")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class JobControllerEntity extends UUID {

	private static final long serialVersionUID = 1060337252245115645L;
	
	/**
	 * 定时任务名称
	 */
	private String jobName;
	/**
	 * 定时任务表达式
	 */
	private String jobCorn;
	/**
	 * 更新码
	 */
	private String updateBy;
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
}
