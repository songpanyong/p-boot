package com.guohuai.cms.platform.version;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class VersionAddReq {
	
    private String oid;
	
	@NotBlank(message = "版本号不能为空！")
	@Length(max = 50, message = "版本号长度不能超过50（包含）！")
	private String versionNo;
	
//	@NotBlank(message = "文件名称不能为空！")
	@Length(max = 50, message = "文件名称长度不能超过50（包含）！")
	private String fileName;
	
	/**
	 * 版本大小
	 */
	private String versionSize;
	
	@Length(max = 100, message = "链接长度不能超过100（包含）！")
	private String fileUrl;
	
	@Length(max = 300, message = "版本描述长度不能超过300（包含）！")
	private String description;
	
	/**
	 * 预计发布日期
	 */
	private String expectPublishTime;
	
	/**
	 * 升级类型   increment ：增量 version：现有版本
	 */
	private String upgradeType;

	/**
	 * 是否强制升级0：否 1：是
	 */
	private Integer compulsory;
	
	/**
	 * 提醒更新频率
	 */
	private Integer checkInterval;
	
	/**
	 * 系统版本类型  ios:ios,android:安卓,increment:增量
	 */
	private String system;
	

}
