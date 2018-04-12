package com.guohuai.operate.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gson.TypeAdapter;
import com.guohuai.operate.api.ext.TimestampTypeAdapter;
import com.guohuai.operate.api.objs.admin.AdminListObj;
import com.guohuai.operate.api.objs.admin.AdminObj;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public class AdminSdk {

	protected static final String SESSION_OID_KEY = "operateoid";

	private AdminOpenApi api;

	public AdminSdk(final HttpSession session, String apihost, Level level) {
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
				.logLevel(level).target(AdminOpenApi.class, apihost);
	}

	public String getLoginAdmin(HttpSession session) {
		Object obj = session.getAttribute(SESSION_OID_KEY);
		if (null == obj) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public AdminObj getAdmin(String oid) {
		AdminObj admin = this.api.getAdmin(oid);
		return admin;
	}

	public AdminListObj getAdmins(String[] oids) {
		if (null == oids || oids.length == 0) {
			return new AdminListObj();
		}

		if (oids.length == 1) {
			AdminObj amp = this.getAdmin(oids[0]);
			if (null != amp) {
				AdminListObj result = new AdminListObj();
				if (amp.getErrorCode() == 0) {
					result.setTotal(1);
					result.setRows(new ArrayList<AdminObj>());
					result.getRows().add(amp);
					return result;
				} else {
					result.setErrorCode(amp.getErrorCode());
					result.setErrorMessage(amp.getErrorMessage());
					return result;
				}
			} else {
				return new AdminListObj();
			}
		}

		AdminListObj result = this.api.getAdmins(oids);
		return result;
	}

	public AdminListObj getAdmins() {
		return this.api.search();
	}

	public AdminObj getInfo() {
		AdminObj info = this.api.getInfo();
		return info;
	}

	public AdminListObj findByAuth(String[] auth, String[] status) {
		if (null == auth || auth.length == 0) {
			return null;
		}
		return this.api.findByAuth(auth, status);
	}

	public void checkLogin(HttpSession session) {
		Object obj = session.getAttribute(SESSION_OID_KEY);
		if (null == obj || !(obj instanceof String) || obj.toString().trim().equals("")) {
			throw new RuntimeException("forbiden without login.");
		}
	}

}
