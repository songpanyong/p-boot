package com.guohuai.mimosa.api.obj;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mimosa sdk 接口返回对象的基类
 * 
 * @author wanglei
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MimosaSDKRep implements Serializable {

	private static final long serialVersionUID = 3383481435081136642L;

	/** 错误代码 */
	private String errorCode;

	/** 错误消息 */
	private String errorMessage;

}
