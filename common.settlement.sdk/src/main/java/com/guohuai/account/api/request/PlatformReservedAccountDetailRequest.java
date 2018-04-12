package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: PlatformReservedAccountDetailRequest 
* @Description: 平台备付金详情信息查询请求参数
* @author chendonghui
* @date 2018年2月3日 下午3:48:15 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformReservedAccountDetailRequest implements Serializable{
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -6681318821260147740L;
	/**
	 * 备付金户号
	 */
	private String accountNo;
	
}
