package com.guohuai.cms.platformext.mail;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "T_PLATFORM_MAIL")
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class MailExtEntity extends UUID  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String MAIL_mesType_person = "person"; 					// 个人信息
	public static final String MAIL_mesType_all = "all"; 						// 全站信息
	public static final String MAIL_mesType_system = "system"; 					// 系统信息
	
	public static final String MAIL_mailType_person = "person"; 				// 个人信息
	public static final String MAIL_mailType_all = "all"; 						// 全站信息
	public static final String MAIL_mailType_team = "team"; 					// 组内信息
	
	public static final String MAIL_status_toApprove = "toApprove";				// 待审核
	public static final String MAIL_status_pass = "pass";						// 已发送
	public static final String MAIL_status_refused = "refused";					// 已驳回
	public static final String MAIL_status_delete = "delete";					// 已删除
	
	public static final String MAIL_isRead_is = "is"; 							// 已读
	public static final String MAIL_isRead_no = "no"; 							// 未读
	
	private String userOid;			// 所属用户
	private String phone;			// 手机 	
	private String mailType;		// all全站信息  person个人信息
	private String mesType;			// 类型  注册 待审核 等 待定
	private String mesTitle;		// 标题
	private String mesContent;		// 内容
	private String isRead;			// 是否已读 is是 no否
	private String status;			// 站内信状态 toApprove待审核  pass已发送  refused已驳回  delete已删除
	private String requester;		// 申请人
	private String approver;		// 审核人
	private String approveRemark;	// 审核备注
//	private String readUserNote;	// 已读人员信息
	private String remark;			// 备注
	private Timestamp updateTime;
	private Timestamp createTime;
	private String labelCode;       //标签编码
	

}
