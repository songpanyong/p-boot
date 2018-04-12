package com.guohuai.account.api.request;

import java.io.Serializable;

import com.guohuai.account.component.PageBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @Description: 产品户可用余额请求参数 
 * @author ZJ   
 * @date 2018年1月22日 下午2:29:14 
 * @version V1.0   
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAccountRequest extends PageBase implements Serializable {
	private static final long serialVersionUID = 875001562822154703L;
	
	/**
	 * 发行人 
	 */
	private String userOid;
	/**
	 * 产品户账户号
	 */
	private String relationProduct;
}