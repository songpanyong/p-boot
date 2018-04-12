package com.guohuai.operate.component.exception;

import com.guohuai.basic.config.ErrorDefineConfig;

public class OPException extends RuntimeException {

	private int code;

	private static final long serialVersionUID = -7344147416330238244L;

	private OPException(int code) {
		super(ErrorDefineConfig.define.get(code));
		this.code = code;
	}

	public OPException(String message) {
		super(message);
		this.code = -1;
	}

	private OPException(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public int getCode() {
		return this.code;
	}

	public static OPException getException(int errorCode) {
		if (ErrorDefineConfig.define.containsKey(errorCode)) {
			return new OPException(errorCode);
		}
		return new OPException(String.valueOf(errorCode));
	}

	public static OPException getException(String errorMessage) {
		return new OPException(errorMessage);
	}

	public static OPException getException(Throwable error) {
		if (error instanceof OPException) {
			return (OPException) error;
		}
		return new OPException(error);
	}

}
