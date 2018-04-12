package com.guohuai.cms.platform.images;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImagesAddReq {
	
	@NotBlank(message = "图片名称不能为空！")
	@Length(max = 60, message = "图片名称长度不能超过60（包含）！")
	private String imgName;
	
	@NotBlank(message = "图片访问地址不能为空！")	
	private String imgUrl;
}
