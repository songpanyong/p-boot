package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: PlatformAccountInfoRequest 
* @Description: 平台信息查询请求参数
* @author chendonghui
* @date 2018年2月3日 上午12:10:11 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformAccountInfoRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 4679047933408002241L;
	/**
	 * 平台id
	 */
	private String userOid;
	
}
