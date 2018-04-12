package com.guohuai.points.request;

import lombok.Data;

@Data
public abstract class BaseRequest {

	private int page=1;
	
	private int rows=10;
	
}
