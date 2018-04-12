package com.guohuai.cms.platform.images;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class ImagesQueryRep {
	
	private String oid, imgName, imgUrl;	

	private Timestamp createTime;
}
