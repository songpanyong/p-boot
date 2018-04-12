package com.guohuai.account.api.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: PlatformReservedAccountDetailResponse 
* @Description: 平台备付金详情信息查询返回参数
* @author chendonghui
* @date 2018年2月3日 上午12:15:16 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PlatformReservedAccountDetailResponse extends BaseResponse {
	
	private static final long serialVersionUID = -3932633907319932568L;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 可用余额 
	 */
	private BigDecimal availableBalance;
	/**
	 * 运行出金类型 
	 */
	private List<String> useType;
	/**
	 * 授权额度 
	 */
	private BigDecimal lineOfCredit;
	/**
	 * 账户净额(账户净额=可用余额-授权额度) 
	 */
	private BigDecimal netBalance;
	/**
	 * 状态 
	 */
	private String status;
	/**
	 * 状态（转义） 
	 */
	private String statusDisp;
	
}
