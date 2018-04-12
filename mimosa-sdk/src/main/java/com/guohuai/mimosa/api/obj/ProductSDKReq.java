package com.guohuai.mimosa.api.obj;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 产品列表查询请求参数
 * 
 * <pre>
 * oid:产品oid(空表示查询所有产品)
 * type:产品类型(0:活期产品，1:定期产品，空表示查询所有类型)
 * label:产品标签(空表示查询所有标签)
 * </pre>
 * 
 * @author wanglei
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSDKReq extends MimosaSDKReq {

	private static final long serialVersionUID = -3673063314257511819L;

	/** 产品oid */
	private String oid = "";

	/** 产品类型(0:活期产品，1:定期产品，空:所有产品) */
	private String type;

	/** 产品标签 */
	private String label;
	
}
