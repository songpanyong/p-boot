package com.guohuai.operate.role;

import java.util.List;

import com.guohuai.operate.component.web.view.PageResp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoleListResp extends PageResp<RoleResp> {

	public RoleListResp(List<Role> roles, long total) {
		super.total = total;
		if (null != roles && roles.size() > 0) {
			for (Role role : roles) {
				super.rows.add(new RoleResp(role));
			}
		}
	}

}
