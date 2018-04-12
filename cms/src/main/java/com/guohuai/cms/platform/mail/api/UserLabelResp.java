package com.guohuai.cms.platform.mail.api;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class UserLabelResp extends BaseResp {

	/**标签code
	 * 
	 */
	private String labelCode;
	
	/**
	 * 标签name
	 */
	private String labelName;
	
	/**
	 * 投资人列表
	 */
	private List<LabelInvestor> rows;
	
	@Data
	public static class LabelInvestor{
		/**
		 * 投资人标签和投资人关系表的主键oid
		 */
		private String oid;  
		/**
		 * 投资人id
		 */
		private String investorOid;
		
		/**
		 * 用户账户
		 */
		private String phoneNum;
		
		/**
		 * 用户名（用户的姓名）
		 */
		private String realName;
		
		/**
		 * 用户分组（用户标签）
		 */
		private String investorLabel;
		
		/**
		 * 认证时间（即创建时间）
		 */
		private Timestamp createTime;
		
		/**
		 * 累计投资金额（元）
		 */
		private BigDecimal totalInvestAmount=BigDecimal.ZERO;
		
		/**
		 * 隶属公司
		 */
		private String company;
		
		/**
		 * 隶属部门
		 */
		private String department;
	}
}
