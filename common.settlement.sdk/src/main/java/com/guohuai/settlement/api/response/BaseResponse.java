package com.guohuai.settlement.api.response;

import java.io.Serializable;

import com.guohuai.basic.common.StringUtil;

import lombok.Data;

@Data
public  class BaseResponse implements Serializable {

	private static final long serialVersionUID = -1748346599148347108L;
	private String returnCode;
	private String errorMessage;
	
	public static boolean isSuccess(BaseResponse baseResponse) {
		if (null == baseResponse) {
			return false;
		}

		String code = baseResponse.getReturnCode();
		if (StringUtil.isEmpty(code)) {
			return false;
		}

		if ("0000".equals(code)) {
			return true;
		}
		return false;
	}
}
