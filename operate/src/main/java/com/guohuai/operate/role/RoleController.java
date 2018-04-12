package com.guohuai.operate.role;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.guohuai.operate.component.web.view.BaseResp;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/operate/admin/ctrl/role", produces = "application/json;charset=utf-8")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<RoleResp> save(@Valid CreateRoleReq req) {
		String operator = super.getLoginUser();
		Role role = this.roleService.save(req, operator);
		return new ResponseEntity<RoleResp>(new RoleResp(role), HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<RoleResp> update(UpdateRoleReq req) {
		String operator = super.getLoginUser();
		Role role = this.roleService.update(req, operator);
		return new ResponseEntity<RoleResp>(new RoleResp(role), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<BaseResp> delete(@RequestParam String oid) {
		String operator = super.getLoginUser();
		this.roleService.delete(oid, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<RoleListResp> list(HttpServletRequest request,
			@And({ @Spec(params = "name", path = "name", spec = Like.class), 
				   @Spec(params = "system", path = "system.oid", spec = Equal.class) }) 
			Specification<Role> spec,
			@RequestParam(defaultValue = "-1") int page, 
			@RequestParam(defaultValue = "-1") int rows, 
			boolean stats) {
		
		if (page == -1) {
			RoleListResp roles = this.roleService.list(spec, stats);
			return new ResponseEntity<RoleListResp>(roles, HttpStatus.OK);
		} else {
			RoleListResp roles = this.roleService.list(spec, page - 1, rows, stats);
			return new ResponseEntity<RoleListResp>(roles, HttpStatus.OK);

		}
	}

}
