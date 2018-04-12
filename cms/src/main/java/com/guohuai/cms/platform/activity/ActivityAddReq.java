package com.guohuai.cms.platform.activity;


import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActivityAddReq {
	
    private String oid;
	
    @NotBlank(message = "渠道不能为空！")
	private String channelOid;
    
	@NotBlank(message = "活动标题不能为空！")
	@Length(max = 50, message = "活动标题长度不能超过50（包含）！")
	private String title;
	
	@NotBlank(message = "位置不能为空！")
	@Length(max = 30, message = "位置长度不能超过30（包含）！")
	private String location;
	
	/** 区分链接和调整 0：链接  1：跳转 */
	@NotNull(message = "链接类型不能为空！")
	private int linkType;
	
	@Length(max = 250, message = "链接长度不能超过250（包含）！")
	private String linkUrl;
	
	/** 跳转到APP的页面 */
	private String toPage;
	
	private String picUrl;
	
	private Timestamp beginTime;
	
	private Timestamp endTime;

}
