package com.guohuai.operate.admin.role;

import java.util.List;

import com.guohuai.operate.component.web.view.PageResp;

public class AdminRoleListResp extends PageResp<AdminRoleResp> {

	public AdminRoleListResp(List<AdminRole> adminRoles, long total) {
		super.total = total;
		if (null != adminRoles && adminRoles.size() > 0) {
			for (AdminRole adminRole : adminRoles) {
				super.rows.add(new AdminRoleResp(adminRole));
			}
		}
	}

}
