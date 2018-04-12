/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.guohuai.basic.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.Validate;

import com.guohuai.basic.component.exception.GHException;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 * 
 * @author calvin
 */
public class Digests {

	public static final int SALT8 = 8;

	private static final String utf8 = "utf-8";
	public static final Charset UTF8 = Charset.forName(utf8);
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

	private static SecureRandom random = new SecureRandom();

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw GHException.getException(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String encodeBase64URLSafeString(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[(input[i] & 0xFF) % BASE62.length];
		}
		return new String(chars);
	}

	public static String escapeXml10(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}

	public static String escapeXml11(String xml) {
		return StringEscapeUtils.escapeXml11(xml);
	}

	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml4(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml4(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String part) throws UnsupportedEncodingException {
		return URLEncoder.encode(part, utf8);
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String part) throws UnsupportedEncodingException {
		return URLDecoder.decode(part, utf8);
	}

	public static byte[] genSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);
		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(salt);
			byte[] result = digest.digest(input);
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw GHException.getException(e);
		}
	}

}
