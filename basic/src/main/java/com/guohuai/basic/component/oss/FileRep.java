package com.guohuai.basic.component.oss;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FileRep extends BaseResp {

	private String url;
}
