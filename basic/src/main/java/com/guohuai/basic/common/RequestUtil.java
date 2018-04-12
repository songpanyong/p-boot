package com.guohuai.basic.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.guohuai.basic.common.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RequestUtil {

	public final static RealIp getRealIp(HttpServletRequest request, HttpSession session) {

		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtil.isEmpty(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtil.isEmpty(ip)) {
			ip = request.getRemoteHost();
		}
		return new RealIp(ip, request.getRemotePort());
	}

	@Data
	@AllArgsConstructor
	public static class RealIp {
		private String host;
		private int port;
	}

}
