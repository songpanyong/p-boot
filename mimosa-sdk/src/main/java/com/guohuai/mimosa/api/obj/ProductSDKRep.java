package com.guohuai.mimosa.api.obj;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 产品列表查询返回信息
 * 
 * @author wanglei
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSDKRep extends MimosaSDKRep implements Serializable {

	private static final long serialVersionUID = 3901229909864126344L;

	/** 产品oid */
	private String oid;

	/** 产品名称 */
	private String name;

	/** 产品代码 */
	private String code;

	/** 存续期*/
	private int durationPeriodDays;

	/** 产品类型*/
	private String type;

}
