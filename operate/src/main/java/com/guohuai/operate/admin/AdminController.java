package com.guohuai.operate.admin;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.operate.component.web.view.BaseResp;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/operate/admin", produces = "application/json;charset=utf-8")
public class AdminController extends BaseController {

	final static Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<Object> login(@RequestParam String account, @RequestParam String password, @RequestParam String system) {
		log.info("登陆=" + "account=" + account + ",password=" + password + ",system=" + system);
		Admin admin = this.adminService.login(account, password, system, super.request.getRemoteAddr() + ":" + super.request.getRemotePort());
		super.setLoginUser(admin.getOid());
		
		return new ResponseEntity<Object>(new AdminResp(admin), HttpStatus.OK);
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<BaseResp> logout(HttpServletRequest request) {
		String adminOid = super.getLoginUser();
		if (StringUtils.isNotEmpty(adminOid)) {
			String operateId = request.getRemoteAddr();
			this.adminService.logout(adminOid, operateId);
			super.setLogoutUser();
		} else {
			super.session.invalidate();
		}
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminResp> create(@RequestBody CreateAdminReq req) {
		String operator = super.getLoginUser();
		Admin admin = adminService.create(req, operator);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	@RequestMapping(value = "/create/form", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody ResponseEntity<AdminResp> createByForm(@Valid CreateAdminReq req) {
        String operator = super.getLoginUser();
        Admin admin = adminService.create(req, operator);
        return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
    }

	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<AdminResp> update(UpdateAdminReq req) {
		super.getLoginUser();
		String operator = this.getLoginUser();
		Admin admin = this.adminService.update(req, operator);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<Object> read(@RequestParam String oid) {
		String[] oids = oid.split(",");
		if (oids.length == 1) {
			Admin admin = this.adminService.read(oids[0]);
			return new ResponseEntity<Object>(new AdminResp(admin), HttpStatus.OK);
		} else {
			List<Admin> list = this.adminService.read(oids);
			if (null != list && list.size() > 0) {
				AdminListResp resp = new AdminListResp(list, list.size());
				return new ResponseEntity<Object>(resp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new AdminListResp(), HttpStatus.OK);
			}
		}

	}

	@RequestMapping(value = "/info", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminInfoResp> info() {
		String oid = super.getLoginUser();
		AdminInfoResp r = this.adminService.info(oid);
		return new ResponseEntity<AdminInfoResp>(r, HttpStatus.OK);
	}

	/**
	 * 冻结
	 * @param requst
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/freeze", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminResp> freeze(HttpServletRequest requst, @RequestParam String oid) {
		String operator = super.getLoginUser();
		String operateIp = request.getRemoteAddr();
		Admin admin = this.adminService.freeze(oid, operator, operateIp);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	/**
	 * 解冻
	 * @param request
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/unfreeze", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminResp> unfreeze(HttpServletRequest request, @RequestParam String oid) {
		String operator = super.getLoginUser();
		String operateIp = request.getRemoteAddr();
		Admin admin = this.adminService.unfreeze(oid, operator, operateIp);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	/**
	 * 管理员查询
	 * @param request
	 * @param spec
	 * @param system
	 * @param validable
	 * @param roleable
	 * @param authable
	 * @param passwordable
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<AdminListResp> search(HttpServletRequest request,
			@Or({ @Spec(params = "keyword", path = "account", spec = Like.class), 
				  @Spec(params = "keyword", path = "name", spec = Like.class) }) 
			Specification<Admin> spec,
			@RequestParam String system, 
			@RequestParam(defaultValue = "false") boolean validable,
			@RequestParam(defaultValue = "false") boolean roleable,
			@RequestParam(defaultValue = "false") boolean authable, 
			@RequestParam(defaultValue = "false") boolean passwordable, 
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam int rows) {
		super.getLoginUser();

		if (validable) {
			spec = Specifications.where(spec).and(new Specification<Admin>() {

				@Override
				public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate status = cb.equal(root.get("status").as(String.class), "VALID");
					Predicate validTimeIsNull = cb.isNull(root.get("validTime").as(Timestamp.class));
					Predicate validTimeGreatThan = cb.greaterThanOrEqualTo(root.get("validTime").as(Timestamp.class), new Timestamp(System.currentTimeMillis()));
					return cb.and(status, cb.or(validTimeIsNull, validTimeGreatThan));
				}
			});
		}

		AdminListResp adminList = this.adminService.search(spec, system, roleable, authable, passwordable, page - 1, rows);
		return new ResponseEntity<AdminListResp>(adminList, HttpStatus.OK);
	}

	/**
	 * 重置密码
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/reset/password", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<AdminResp> resetPassword(@RequestBody ResetPasswordReq form) {
		super.getLoginUser();
		String operator = super.getLoginUser();
		String operateIp = super.request.getRemoteAddr();
		Admin admin = this.adminService.resetPassword(operator, form, operateIp);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	@RequestMapping(value = "/reset/password/form", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<AdminResp> resetPasswordByForm(HttpServletRequest request, ResetPasswordReq form) {
		String operator = super.getLoginUser();
		String operateIp = request.getRemoteAddr();
		Admin admin = this.adminService.resetPassword(operator, form, operateIp);
		this.logout(request);
		return new ResponseEntity<AdminResp>(new AdminResp(admin), HttpStatus.OK);
	}

	@RequestMapping(value = "/reset/password/gen", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<AdminResp> resetPasswordByGen(@RequestParam String adminOid) {
		String operator = super.getLoginUser();
		String operateIp = request.getRemoteAddr();
		Admin admin = this.adminService.genPassword(operator, adminOid, operateIp);
		return new ResponseEntity<AdminResp>(new AdminResp(admin, true), HttpStatus.OK);
	}
}
