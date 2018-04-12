package com.guohuai.account.api.response;

import java.util.List;

import com.guohuai.account.api.response.entity.TransDto;
import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户交易明细集合返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TransDetailListResponse extends PageResp<TransDetailQueryResponse> {
	
	public TransDetailListResponse(){
		super();
	}
	
	public TransDetailListResponse(List<TransDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}
	
	public TransDetailListResponse(List<TransDto> infos, long total){
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (TransDto info : infos) {
				super.rows.add(new TransDetailQueryResponse(info));
			}
		}
	}
	
	
}
