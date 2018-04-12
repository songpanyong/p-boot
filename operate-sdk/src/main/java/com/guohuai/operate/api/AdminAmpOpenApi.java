package com.guohuai.operate.api;

import java.lang.reflect.Array;

import com.guohuai.operate.api.objs.admin.amp.AdminAmpListObj;
import com.guohuai.operate.api.objs.admin.amp.AdminAmpObj;

import feign.Param;
import feign.Param.Expander;
import feign.RequestLine;

public interface AdminAmpOpenApi {

	@RequestLine("POST /operate/admin/amp/info")
	public AdminAmpObj getInfo();

	@RequestLine("POST /operate/admin/amp/read?oid={oid}")
	public AdminAmpObj getAdmin(@Param("oid") String oid);

	@RequestLine("POST /operate/admin/amp/read?oid={oid}")
	public AdminAmpListObj getAdmins(@Param(value = "oid", expander = ArrayExpander.class) String[] oid);

	@RequestLine("POST /operate/admin/amp/search?company={company}&page=1&rows=9999")
	public AdminAmpListObj findByCompany(@Param("company") String company);
	
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
