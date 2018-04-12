package com.guohuai.account.api.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: QueryProductAccountRequest 
* @Description: 发行人产品户信息查询
* @author chendonghui
* @date 2018年2月5日 上午9:31:23 
*
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PublisherAccountInfoRequest implements Serializable {
	
	private static final long serialVersionUID = 2593882605700468159L;
	/**
	 * 发行人 
	 */
	private String userOid;
	/**
	 * 产品户账户号
	 */
	private String productAccountNo;
}