package com.guohuai.payment.ucfpay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author chendonghui
 * @version 创建时间 ：2016年12月7日下午4:42:05
 *
 */
public class QueryString {
	
	private StringBuffer query = new StringBuffer();
	public QueryString(String name, String value) {
		encode(name, value);
	}
	public synchronized void add(String name, String value) {
		query.append('&');
		encode(name, value);
	}
	private synchronized void encode(String name, String value) {
		try {
			query.append(URLEncoder.encode(name, "UTF-8"));
			query.append('=');
			query.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("Broken VM does not support UTF-8");
		}
	}
	public String getQuery() {
		return query.toString();
	}
	public String toString() {
		return getQuery();
	}
}
