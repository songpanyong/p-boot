package com.guohuai.tulip.platform.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 活动-实体
 * 
 * @author tidecc
 */
@Entity
@Table(name = "T_TULIP_EVENT")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class EventEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 3297827842886697187L;
	/** 状态--待审批 */
	public static final String  STATUS_TOCHECK = "pending";
	/** 状态--通过 */
	public static final String  STATUS_PASSED = "pass";
	/** 状态--驳回 */
	public static final String  STATUS_REJECTED = "rejected";
	/** 上架状态--待上架 */
	public static final String  ACTIVE_WAIT = "wait";
	/** 上架状态--已上架 */
	public static final String  ACTIVE_ON = "on";
	/** 上架状态--已下架 */
	public static final String  ACTIVE_OFF = "off";
	
	/** 签到 */
	public static final String EVENTTYPE_SIGN="sign";
	/** 申购 */
	public static final String EVENTTYPE_INVESTMENT="investment";
	/** 实名认证 */
	public static final String EVENTTYPE_AUTHENTICATION="authentication";
	/** 赎回 */
	public static final String EVENTTYPE_REDEEM="redeem";
	/** 注册 */
	public static final String EVENTTYPE_REGISTER="register";
	/** 到期兑付 */
	public static final String EVENTTYPE_BEARER="bearer";
	/** 提现 */
	public static final String EVENTTYPE_CASH="cash";
	/** 退款 */
	public static final String EVENTTYPE_REFUND="refund";
	/** 被推荐人首次投资 */
	public static final String EVENTTYPE_FIRSTFRIENDINVEST="firstFriendInvest";
	/** 软文转发 */
	public static final String EVENTTYPE_FORWARDED="forwarded";
	
	/** 流标 */
	public static final String EVENTTYPE_INVALIDBIDS="invalidBids";
	/** 推荐人 */
	public static final String EVENTTYPE_FRIEND="friend";
	/** 充值 */
	public static final String EVENTTYPE_RECHARGE="recharge";
	/** 手机认证 */
	public static final String EVENTTYPE_PHONEAUTHENTICATION="phoneAuthentication";
	/** 绑卡 */
	public static final String EVENTTYPE_BINDINGCARD="bindingCard";
	/** 调度活动 */
	public static final String EVENTTYPE_SCHEDULE="schedule";
	/** 自定义活动 */
	public static final String EVENTTYPE_CUSTOM="custom";
	/** 用户生日活动 */
	public static final String EVENTTYPE_BIRTHDAY="birthday";
	
	

	/** 活动名称 */
	@NotBlank(message="活动标题不能为空")
	@Length(max=100,message="最大长度不能大于100个字符")
	private String title;
	/** 活动描述 */
	private String description;
	/** 生效时间 */
	private Date start;
	/** 失效时间 */
	private Date finish;
	/** 状态 */
	private String status=STATUS_TOCHECK;
	/** 上下架状态 */
	private String active=ACTIVE_WAIT;
	/** 创建用户 */
	private String createUser="sys";
	/** 更新用户 */
	private String updateUser="sys";
	/** 创建时间 */
	private Timestamp createTime;
	/** 更新时间 */
	private Timestamp updateTime;
	private String type;
	private String isdel;
	/** 审批备注 */
	/**
	 * 获取 title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * 获取 start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * 设置 start
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	/**
	 * 获取 finish
	 */
	public Date getFinish() {
		return finish;
	}
	/**
	 * 设置 finish
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	/**
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取 active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 设置 active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 获取 createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置 createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取 updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置 updateUser
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取 createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置 updateTime
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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
	
	
} 
