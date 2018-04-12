package com.guohuai.mimosa.api.obj;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 请求参数对象的基类
 * 
 * @author wanglei
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MimosaSDKReq implements Serializable {

	private static final long serialVersionUID = 610820698397903078L;

	/** 页号 */
	private int page;

	/** 分页大小,0代表查询所有 */
	private int rows;

}
