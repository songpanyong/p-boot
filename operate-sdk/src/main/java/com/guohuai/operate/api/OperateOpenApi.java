package com.guohuai.operate.api;

import java.lang.reflect.Array;

import com.guohuai.operate.api.objs.bank.BankListObj;
import com.guohuai.operate.api.objs.bank.BankObj;
import com.guohuai.operate.api.objs.corporate.CorporateListObj;
import com.guohuai.operate.api.objs.corporate.CorporateObj;

import feign.Param;
import feign.Param.Expander;
import feign.RequestLine;

public interface OperateOpenApi {

	@RequestLine("POST /operate/money/corporate/query")
	public CorporateListObj getCorporateList();

	@RequestLine("POST /operate/money/corporate/read?oid={oid}")
	public CorporateObj getCorporate(@Param("oid") String oid);

	@RequestLine("POST /operate/money/bank/userList")
	public BankListObj getUserBanks();

	@RequestLine("POST /operate/money/bank/corporateList")
	public BankListObj getCorporateBanks();

	@RequestLine("POST /operate/money/bank/read?oid={oid}")
	public BankObj getBank(@Param("oid") String oid);

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
