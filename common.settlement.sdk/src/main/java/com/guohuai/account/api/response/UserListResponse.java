package com.guohuai.account.api.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.guohuai.account.api.response.entity.UserDto;
import com.guohuai.basic.component.ext.web.PageResp;

/**
 * 查询用户列表返回参数
 * @author wangyan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserListResponse extends PageResp<UserQueryResponse> {
	
	public UserListResponse(){
		super();
	}
	
	public UserListResponse(List<UserDto> infos) {
		this(infos, null == infos ? 0 : infos.size());
	}
	
	public UserListResponse(List<UserDto> infos, long total){
		super.total = total;
		if (null != infos && infos.size() > 0) {
			for (UserDto info : infos) {
				super.rows.add(new UserQueryResponse(info));
			}
		}
	}
	
	
}
