package com.guohuai.cms.platform.version;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class VersionResp extends BaseResp {
	
	/** 升级版本号 */
	private String version;
	/** 是否为整包升级。true为是，false为否 */
	private Boolean wholePackage;
	/** 升级提示频率，单位为”天”，0为每次打开APP都提示 */
	private Integer checkInterval;
	/** 安装包名称 */
	private String fileName;
	/** 是否为强制更新。true为是，false为否 */
	private Boolean compulsory;
	/** 升级说明 */
	private String description;
	/** 升级文件下载地址 */
	private String downLoadUrl;
	/** 是否有更新 */
	private boolean isNew=false;
	
	
}
