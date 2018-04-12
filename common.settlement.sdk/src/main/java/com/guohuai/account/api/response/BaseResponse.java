package com.guohuai.account.api.response;

import com.guohuai.basic.common.StringUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseResponse implements Serializable {

	private static final long serialVersionUID = -1748346599148347108L;
	private String returnCode;
	private String errorMessage;

	public static boolean isSuccess(BaseResponse baseResponse) {
		if (null == baseResponse) {
			return false;
		} else {
			String code = baseResponse.getReturnCode();
			if (StringUtil.isEmpty(code)) {
				return false;
			} else {
				return "0000".equals(code);
			}
		}
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"returnCode='" + returnCode + '\'' +
				", errorMessage='" + errorMessage + '\'' +
				'}';
	}
}
