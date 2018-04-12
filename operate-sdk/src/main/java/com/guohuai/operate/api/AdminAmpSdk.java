package com.guohuai.operate.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gson.TypeAdapter;
import com.guohuai.operate.api.ext.TimestampTypeAdapter;
import com.guohuai.operate.api.objs.admin.amp.AdminAmpListObj;
import com.guohuai.operate.api.objs.admin.amp.AdminAmpObj;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

public class AdminAmpSdk {

	private AdminAmpOpenApi api;

	public AdminAmpSdk(final HttpSession session, String apihost, Level level) {
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
				.logLevel(level).target(AdminAmpOpenApi.class, apihost);

	}

	public AdminAmpObj getInfo() {
		AdminAmpObj obj = this.api.getInfo();
		return obj;
	}

	public AdminAmpObj getAdmin(String oid) {
		AdminAmpObj admin = this.api.getAdmin(oid);
		return admin;
	}

	public AdminAmpListObj getAdmins(String... oids) {
		if (null == oids || oids.length == 0) {
			return new AdminAmpListObj();
		}

		if (oids.length == 1) {
			AdminAmpObj amp = this.getAdmin(oids[0]);
			if (null != amp) {
				AdminAmpListObj result = new AdminAmpListObj();
				if (amp.getErrorCode() == 0) {
					result.setTotal(1);
					result.setRows(new ArrayList<AdminAmpObj>());
					result.getRows().add(amp);
					return result;
				} else {
					result.setErrorCode(amp.getErrorCode());
					result.setErrorMessage(amp.getErrorMessage());
					return result;
				}
			} else {
				return new AdminAmpListObj();
			}
		}

		AdminAmpListObj result = this.api.getAdmins(oids);
		return result;
	}

	public AdminAmpListObj findByCompany(String company) {
		AdminAmpListObj result = this.api.findByCompany(company);
		return result;
	}

}
