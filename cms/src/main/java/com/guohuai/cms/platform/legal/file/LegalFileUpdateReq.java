package com.guohuai.cms.platform.legal.file;

import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LegalFileUpdateReq {

	/** 文件名称 */
	@NotBlank(message="修改文件不存在！")
	private String oid;
	
	/** 文件名称 */
	@NotBlank(message="文件名称不能为空！")
	private String name;
	
	/** 文件地址 */
	@NotBlank(message="文件地址不能为空！")
	private String fileUrl;
}
