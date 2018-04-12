package com.guohuai.account.api.response;

import java.util.List;

import com.guohuai.account.api.response.entity.SignDto;
import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡查询集合返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CardListResponse extends PageResp<CardQueryResponse> {
	
	public CardListResponse(){
		super();
	}
	
	public CardListResponse(List<SignDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}
	
	public CardListResponse(List<SignDto> infos, long total){
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (SignDto info : infos) {
				super.rows.add(new CardQueryResponse(info));
			}
		}
	}
	
	
}
