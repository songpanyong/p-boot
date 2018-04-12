package com.guohuai.operate.admin;

import java.util.List;

import com.guohuai.operate.component.web.view.PageResp;

public class AdminListResp extends PageResp<AdminResp> {

	public AdminListResp() {
		super.total = 0;
	}

	public AdminListResp(long total) {
		super.total = total;
	}

	public AdminListResp(List<Admin> admins, long total) {
		this(admins, total, false);
	}

	public AdminListResp(List<Admin> admins, long total, boolean passwordable) {
		super.total = total;
		if (null != admins && admins.size() > 0) {
			for (Admin admin : admins) {
				super.rows.add(new AdminResp(admin, passwordable));
			}
		}
	}

}
