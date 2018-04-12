package com.guohuai.account.api.request;

import com.guohuai.account.component.PageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 发行人账户查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PublisherAccountQueryRequest extends PageBase implements Serializable {
	private static final long serialVersionUID = -2709038429407722836L;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户类型
	 */
	private String accountType;
}