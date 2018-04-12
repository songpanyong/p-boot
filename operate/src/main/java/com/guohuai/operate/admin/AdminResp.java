package com.guohuai.operate.admin;

import java.util.List;

import com.guohuai.operate.component.util.DateUtil;
import com.guohuai.operate.component.web.view.BaseResp;
import com.guohuai.operate.role.RoleResp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class AdminResp extends BaseResp {

	private String oid;
	private String sn;
	private String account;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String resources;
	private String comment;
	private String status;
	private String loginIp;
	private String loginTime;
	private String validDate;
	private String validTime;
	private List<RoleResp> roles;

	public AdminResp(Admin admin) {
		this(admin, false);
	}

	public AdminResp(Admin admin, boolean passwordable) {
		super();
		this.oid = admin.getOid();
		this.sn = admin.getSn();
		this.account = admin.getAccount();
		if (passwordable) {
			this.password = admin.getPassword();
		} else {
			this.password = "******";
		}
		this.name = admin.getName();
		this.email = admin.getEmail();
		this.phone = admin.getPhone();
		this.resources = admin.getResources();
		this.comment = admin.getComment();
		this.status = admin.getStatus();
		this.loginIp = admin.getLoginIp();
		if (null != admin.getLoginTime()) {
			this.loginTime = DateUtil.formatDatetime(admin.getLoginTime().getTime());
		}
		if (null != admin.getValidTime()) {
			this.validDate = DateUtil.formatDate(admin.getValidTime().getTime());
			this.validTime = DateUtil.formatDatetime(admin.getValidTime().getTime());
			if (admin.getValidTime().getTime() < System.currentTimeMillis()) {
				this.status = Admin.STATUS_EXPIRED;
			}
		}
	}

}
