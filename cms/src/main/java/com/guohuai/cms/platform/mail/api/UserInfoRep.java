package com.guohuai.cms.platform.mail.api;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfoRep extends BaseResp{
	private String phoneNum;
	  
	private boolean regist;
		
	private String investorOid;
}
