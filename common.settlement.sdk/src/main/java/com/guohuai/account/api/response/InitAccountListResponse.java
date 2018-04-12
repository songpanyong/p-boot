package com.guohuai.account.api.response;

import java.util.List;

import com.guohuai.account.api.response.entity.AccountInfoDto;
import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 初始化账户集合返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class InitAccountListResponse extends PageResp<InitAccountQueryResponse> {
	
	public InitAccountListResponse(){
		super();
	}
	
	public InitAccountListResponse(List<AccountInfoDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}
	
	public InitAccountListResponse(List<AccountInfoDto> infos, long total){
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (AccountInfoDto info : infos) {
				super.rows.add(new InitAccountQueryResponse(info));
			}
		}
	}
	
	
}
