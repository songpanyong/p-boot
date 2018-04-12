package com.guohuai.cms.platform.mail;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class MailCTResp extends BaseResp{
	
	private String oid;
	private String mailType;		// all站点信息  person个人信息
	private String mesType;			// 类型  注册 待审核 等 待定
	private String mesTitle;		// 标题
	private String mesContent;		// 内容
	private String isRead;			// 是否已读 ok是 no否
	private Timestamp updateTime;
	private Timestamp createTime;
}
