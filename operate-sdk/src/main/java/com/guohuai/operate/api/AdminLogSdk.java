package com.guohuai.operate.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.TypeAdapter;
import com.guohuai.basic.common.RequestUtil;
import com.guohuai.operate.api.ext.TimestampTypeAdapter;
import com.guohuai.operate.api.objs.admin.log.AdminLogListObj;
import com.guohuai.operate.api.objs.admin.log.AdminLogObj;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.Param;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

/**
 * 日志
 * @author wzx
 *
 */
public class AdminLogSdk {
	private AdminLogOpenApi api;
	protected static final String SESSION_OID_KEY = "UID";
	
	
	
	public AdminLogSdk(final HttpSession session,String apihost, Level level) {
		List<TypeAdapter<?>> adapters = new ArrayList<TypeAdapter<?>>();
		adapters.add(new TimestampTypeAdapter());

		RequestInterceptor ri = new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate rt) {
				rt.header("Cookie", "SESSION=" + session.getId());
			}
		};

		this.api = Feign.builder().requestInterceptor(ri).encoder(new GsonEncoder(adapters))
				.decoder(new GsonDecoder(adapters)).logger(new Slf4jLogger())
				.logLevel(level).target(AdminLogOpenApi.class, apihost);
	}
	public AdminLogObj saveAdminLog(HttpServletRequest request,HttpSession session,String content) {
		return this.api.saveAdminLog(session.getAttribute(SESSION_OID_KEY)+"",content);
	}
	public AdminLogListObj listByAdmin(String keyword,String type,int page,int rows) {
		return this.api.listByAdmin(keyword,type,page,rows);
		}



}
