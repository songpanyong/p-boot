package com.guohuai.basic.component.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.guohuai.basic.config.ErrorDefineConfig;

public class GHException extends RuntimeException {

	private int code;

	private static final long serialVersionUID = -7344147416330238244L;

	public GHException(int code, Object... args) {
		super(String.format(ErrorDefineConfig.define.get(code), args));
		this.code = code;
	}

	public GHException(String message) {
		super(message);
		this.code = -1;
	}

	public GHException(int code, String message) {
		super(message);
		this.code = code;
	}

	public GHException(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public int getCode() {
		return this.code;
	}

	public static GHException getException(int errorCode, Object... args) {
		if (ErrorDefineConfig.define.containsKey(errorCode)) {
			return new GHException(errorCode, args);
		}
		return new GHException(String.valueOf(errorCode));
	}

	public static GHException getException(String errorMessage) {
		return new GHException(errorMessage);
	}

	public static GHException getException(Throwable error) {
		if (error instanceof GHException) {
			return (GHException) error;
		}
		return new GHException(error);
	}

	public static String getStacktrace(Throwable error) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			// 将出错的栈信息输出到printWriter中
			error.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		return sw.toString();
	}

}
