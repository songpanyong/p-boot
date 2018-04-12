package com.guohuai.cms.component.web;

import java.util.HashMap;
import java.util.Map;

/**
 * response unit
 * @author Jeffrey.Wong
 * 2015年7月22日下午4:24:31
 */
public class UnitRep extends HashMap<String, Object> {

	private static final long serialVersionUID = 5088494912966887333L;

	public UnitRep() {
		super();
	}

	private static final String ERROR_CODE_KEY = "errorCode";
	private static final String ERROR_MSG_KEY = "errorMessage";

	public final static int VALID = 0;

	public UnitRep withError(Throwable error) {
		super.put(ERROR_CODE_KEY, -1);
		super.put(ERROR_MSG_KEY, error.getMessage());
		return this;
	}

	public UnitRep with(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public UnitRep with(Map<String, Object> attrs) {
		for (String key : attrs.keySet()) {
			this.with(key, attrs.get(key));
		}
		return this;
	}

}
