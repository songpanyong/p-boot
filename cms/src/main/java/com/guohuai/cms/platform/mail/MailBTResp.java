package com.guohuai.cms.platform.mail;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class MailBTResp extends BaseResp{
	
	private String oid;
	private String userOid;			// 所属用户
	private String mailType;		// all站点信息  person个人信息
	private String mesType;			// 类型  注册 待审核 等 待定
	private String mesTitle;		// 标题
	private String mesContent;		// 内容
	private String isRead;			// 是否已读 is是 no否
	private String status;			// 站内信状态 toApprove待审核  pass已发送  refuse已驳回  delete已删除
	private String requester;		// 申请人
	private String approver;		// 审核人
	private String approveRemark;			// 审核备注
	private Timestamp updateTime;
	private Timestamp createTime;
	private String remark;			// 备注
	
	private String phone;			// 电话
	private String labelCode;       // 标签编码
	private String labelCodeName;	// 标签编码名称
}
