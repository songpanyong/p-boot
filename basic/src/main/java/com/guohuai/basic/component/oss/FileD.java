package com.guohuai.basic.component.oss;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class FileD extends BaseResp {
	/**
	 * 文件名称
	 */
	private String realname;
	
	/**
	 * 文件后缀/扩展名
	 */
	private String fileExte;
	
	/**
	 * request url
	 */
	private String url;
}
