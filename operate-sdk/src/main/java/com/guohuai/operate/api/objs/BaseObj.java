package com.guohuai.operate.api.objs;

import java.io.Serializable;

public abstract class BaseObj implements Serializable {

	private static final long serialVersionUID = 7180896188146724913L;

	private int errorCode;
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
