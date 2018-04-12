package com.guohuai.cms.component.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.guohuai.basic.config.ErrorDefineConfig;

public class MoneyException extends RuntimeException {

	private int code;

	private static final long serialVersionUID = -7344147416330238244L;

	public MoneyException(int code, Object... args) {
		super(String.format(ErrorDefineConfig.define.get(code), args));
		this.code = code;
	}

	public MoneyException(String message) {
		super(message);
		this.code = -1;
	}

	public MoneyException(Throwable cause) {
		super(cause);
		this.code = -1;
	}

	public int getCode() {
		return this.code;
	}

	public static MoneyException getException(int errorCode, Object... args) {
		if (ErrorDefineConfig.define.containsKey(errorCode)) {
			return new MoneyException(errorCode, args);
		}
		return new MoneyException(String.valueOf(errorCode));
	}

	public static MoneyException getException(String errorMessage) {
		return new MoneyException(errorMessage);
	}

	public static MoneyException getException(Throwable error) {
		if (error instanceof MoneyException) {
			return (MoneyException) error;
		}
		return new MoneyException(error);
	}
	
	public static String getStacktrace(Throwable error) {
		StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw =  new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
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
