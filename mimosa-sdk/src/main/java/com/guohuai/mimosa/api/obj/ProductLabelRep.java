package com.guohuai.mimosa.api.obj;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 查询产品标签返回信息
 * 
 * @author wanglei
 *
 */
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductLabelRep extends MimosaSDKRep  {

	private static final long serialVersionUID = 3901229909864126344L;
	
	private String oid;
	/** 标签代码 */
	private String code;

	/** 产品标签名称 */
	private String name;

	/**
	 * 获取 oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * 设置 oid
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 获取 code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
