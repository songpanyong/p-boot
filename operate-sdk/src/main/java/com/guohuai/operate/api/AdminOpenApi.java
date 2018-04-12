package com.guohuai.operate.api;

import java.lang.reflect.Array;

import com.guohuai.operate.api.objs.admin.AdminListObj;
import com.guohuai.operate.api.objs.admin.AdminObj;

import feign.Param;
import feign.Param.Expander;
import feign.RequestLine;

public interface AdminOpenApi {

	@RequestLine("POST /operate/admin/read?oid={oid}")
	public AdminObj getAdmin(@Param("oid") String oid);

	@RequestLine("POST /operate/admin/info")
	public AdminObj getInfo();

	@RequestLine("POST /operate/admin/read?oid={oid}")
	public AdminListObj getAdmins(@Param(value = "oid", expander = ArrayExpander.class) String[] oids);

	@RequestLine("POST /operate/admin/list/auth?auth={auth}&status={status}")
	public AdminListObj findByAuth(@Param(value = "auth", expander = ArrayExpander.class) String[] auth, @Param(value = "status", expander = ArrayExpander.class) String[] status);

	@RequestLine("POST /operate/admin/search?system=GAH&rows=2000")
	public AdminListObj search();

	final class ArrayExpander implements Expander {

		@Override
		public String expand(Object value) {
			if (null == value) {
				return "";
			}
			if (value.getClass().isArray()) {
				if (Array.getLength(value) > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < Array.getLength(value); i++) {
						sb.append(",").append(Array.get(value, i).toString());
					}
					return sb.substring(1);
				} else {
					return "";
				}
			}
			return value.toString();
		}

	}

}
