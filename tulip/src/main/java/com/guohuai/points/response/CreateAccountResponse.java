package com.guohuai.points.response;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: CreateAccountResponse 
 * @Description: 新建账户返回参数
 * @author CHENDONGHUI
 * @date 2017年3月20日 下午06:24:46 
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateAccountResponse extends BaseResponse {
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = -1973703396912515508L;
	
	/**
	 * 账户oid
	 */
	private String oid;
	/**
	 * 会员ID
	 */
	private String userOid;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品\卡券
	 */
	private String relationProduct;
	/**
	 * 关联产品名称\卡券
	 */
	private String relationProductName;
	/**
	 * 账户号
	 */
	private String accountNo; 
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private String createTime;
}
