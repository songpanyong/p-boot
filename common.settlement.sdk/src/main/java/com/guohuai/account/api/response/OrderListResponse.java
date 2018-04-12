package com.guohuai.account.api.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.guohuai.account.api.response.entity.AccOrderDto;
import com.guohuai.basic.component.ext.web.PageResp;

/**
 * 订单集合返回参数
* @ClassName: NewUserResponse 
* @Description: 
* @author longyunbo
* @date 2016年11月8日 上午10:19:46 
*
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OrderListResponse extends PageResp<OrderQueryResponse> {
	
	public OrderListResponse(){
		super();
	}
	
	public OrderListResponse(List<AccOrderDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}
	
	public OrderListResponse(List<AccOrderDto> infos, long total){
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (AccOrderDto info : infos) {
				super.rows.add(new OrderQueryResponse(info));
			}
		}
	}
	
	
}
