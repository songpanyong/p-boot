package com.guohuai.cms.platform.activity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ActivityAppRep {
	
	private String oid, title, picUrl, location, linkUrl, toPage, beginTimeFull, endTimeFull, beginTime, endTime;

}
