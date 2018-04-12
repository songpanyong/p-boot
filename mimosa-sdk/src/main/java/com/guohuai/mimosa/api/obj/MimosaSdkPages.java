package com.guohuai.mimosa.api.obj;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 分页工具
 * 
 * @author wanglei
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MimosaSdkPages<T> implements Serializable {

	private static final long serialVersionUID = -3347969204353388183L;

	/** 总记录数 */
	private Integer total;

	/** 当前页包含的信息 */
	private List<T> rows;
}
