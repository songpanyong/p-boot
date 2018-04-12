package com.guohuai.points.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {
	private static final long serialVersionUID = -1748346599148347108L;
	private String returnCode;
	private String errorMessage;
}