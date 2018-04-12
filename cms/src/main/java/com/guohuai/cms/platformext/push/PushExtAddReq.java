package com.guohuai.cms.platformext.push;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PushExtAddReq {
	
    private String oid;
	
    
	@NotNull(message = "标题不能为空！")
	@Length(max = 50, message = "标题长度不能超过50（包含）！")
	private String title;
	
	/**
	 * 类型
	 */
	private String type;
	
	@Length(max =200, message = "链接地址长度不能超过200（包含）！")
	private String url;
	
	/**
	 * 摘要
	 */
	@NotNull(message = "摘要不能为空！")
	@Length(max = 200, message = "摘要长度不能超过200（包含）！")
	private String summary;

	private String pushUserAcc;
	
	/**
	 * 推送类型   个人  全站
	 */
	private String pushType;
	private String labelCode;
}
