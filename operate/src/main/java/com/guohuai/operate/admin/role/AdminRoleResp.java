package com.guohuai.operate.admin.role;

import com.guohuai.operate.component.web.view.BaseResp;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AdminRoleResp extends BaseResp {

	private String oid;
	private String adminOid;
	private String adminSn;
	private String adminAccount;
	private String adminName;
	private String roleOid;
	private String roleName;

	public AdminRoleResp(AdminRole adminRole) {
		super();
		this.oid = adminRole.getOid();
		this.adminOid = adminRole.getAdmin().getOid();
		this.adminSn = adminRole.getAdmin().getSn();
		this.adminAccount = adminRole.getAdmin().getAccount();
		this.adminName = adminRole.getAdmin().getName();
		this.roleOid = adminRole.getRole().getOid();
		this.roleName = adminRole.getRole().getName();
	}

}
