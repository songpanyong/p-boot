package com.guohuai.cms.platform.information;

import com.guohuai.cms.component.web.BaseRep;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class InformationAppRep extends BaseRep{
	
	private String oid, title, summary, type, url, thumbnailUrl, content, publishTime, publishTimeFull;

}
