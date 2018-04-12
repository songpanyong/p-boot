package com.guohuai.account.api.response;

import java.math.BigDecimal;

import com.guohuai.account.api.response.entity.AccountInfoDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 初始化账户查询返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class InitAccountQueryResponse extends BaseResponse {
	
	
	/**
	* @Fields serialVersionUID : 
	*/
	private static final long serialVersionUID = 6984920923773172183L;
	
	public InitAccountQueryResponse(AccountInfoDto entity){
		this.userType = entity.getUserType();
		this.accountType = entity.getAccountType();
		this.relationProductNo = entity.getRelationProduct();
		this.accountNo = entity.getAccountNo();
		this.userOid = entity.getUserOid();
		this.status = entity.getStatus();
		this.balance = entity.getBalance();
	}
	/**
	 * 用户类型
	 */
	private String userType; 
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 关联产品编号
	 */
	private String relationProductNo;
	/**
	 * 账户号
	 */
	private String accountNo;
	/**
	 * 会员id
	 */
	private String userOid;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 交易后余额
	 */
	private BigDecimal balance;
	
	
}
