package com.guohuai.account.api.response;

import java.util.List;

import com.guohuai.account.api.response.entity.AccountInfoDto;
import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * @Description: 产品户可用余额响应参数结果集
 * @author ZJ   
 * @date 2018年1月22日 下午2:43:23 
 * @version V1.0   
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductAccountListResponse extends PageResp<ProductAccountResponse> {
	public ProductAccountListResponse() {
		super();
	}

	public ProductAccountListResponse(List<AccountInfoDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}

	public ProductAccountListResponse(List<AccountInfoDto> infos, long total) {
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (AccountInfoDto info : infos) {
				super.rows.add(new ProductAccountResponse(info));
			}
		}
	}
}