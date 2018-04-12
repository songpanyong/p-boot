package com.guohuai.operate.admin.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.operate.admin.Admin;
import com.guohuai.operate.admin.AdminService;
import com.guohuai.operate.role.Role;
import com.guohuai.operate.role.RoleListResp;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/operate/admin/role", produces = "application/json;charset=utf-8")
public class AdminRoleController extends BaseController {

	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminRoleListResp> list(HttpServletRequest request, @Spec(params = "adminOid", path = "admin.oid", spec = Equal.class) Specification<AdminRole> spec) {
		super.getLoginUser();
		List<AdminRole> list = this.adminRoleService.list(spec);
		return new ResponseEntity<AdminRoleListResp>(new AdminRoleListResp(list, list.size()), HttpStatus.OK);
	}

	@RequestMapping(value = "/roles", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<RoleListResp> roles(@RequestParam String adminOid) {
		super.getLoginUser();
		Admin admin = this.adminService.read(adminOid);
		List<AdminRole> ars = this.adminRoleService.findByAdmin(admin);
		List<Role> rs = new ArrayList<Role>();
		for (AdminRole ar : ars) {
			rs.add(ar.getRole());
		}
		return new ResponseEntity<RoleListResp>(new RoleListResp(rs, rs.size()), HttpStatus.OK);
	}

}
