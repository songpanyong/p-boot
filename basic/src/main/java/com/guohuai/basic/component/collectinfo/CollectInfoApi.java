package com.guohuai.basic.component.collectinfo;

import feign.RequestLine;

public interface CollectInfoApi {
	@RequestLine("POST /mimosa/collectinfo")
	public String collectInfo(String info);
}
