package com.guohuai.account.api.response;

import java.math.BigDecimal;

import com.guohuai.account.api.response.entity.AccountInfoDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @Description: 产品户可用余额响应参数  
 * @author ZJ   
 * @date 2018年1月22日 下午2:35:23 
 * @version V1.0   
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAccountResponse extends BaseResponse {
	public ProductAccountResponse(AccountInfoDto info){
		this.productBalance = info.getBalance();
	}
	
	private static final long serialVersionUID = -6506386330067549275L;
	
	/** 发行人 */
	private String userOid;
	/** 产品id */
	private String relationProduct;
	/** 产品户可用余额 */
	private BigDecimal productBalance = BigDecimal.ZERO;
}