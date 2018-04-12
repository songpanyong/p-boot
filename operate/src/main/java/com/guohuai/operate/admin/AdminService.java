package com.guohuai.operate.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guohuai.basic.common.SeqGenerator;
import com.guohuai.operate.admin.log.AdminLog;
import com.guohuai.operate.admin.log.AdminLogDao;
import com.guohuai.operate.admin.role.AdminRole;
import com.guohuai.operate.admin.role.AdminRoleService;
import com.guohuai.operate.component.exception.OPException;
import com.guohuai.operate.role.Role;
import com.guohuai.operate.role.RoleResp;
import com.guohuai.operate.system.Sys;
import com.guohuai.operate.system.SysService;

@Service
public class AdminService {

	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private AdminLogDao adminLogDao;
	@Autowired
	private SeqGenerator seqGenerator;
	@Autowired
	private SysService sysService;

	public String genSn() {
		String sn = this.seqGenerator.next("20");
		return sn;
	}

	@Transactional
	public Admin create(CreateAdminReq req, String operator) {
		return this.create(req, Admin.STATUS_VALID, operator);
	}

	@Transactional
	public Admin create(CreateAdminReq req, String status, String operator) {
		Sys system = this.sysService.get(req.getSystem());
		
		if (this.existsAccount(req.getAccount(), system)) {
			// 管理员已存在
			throw OPException.getException(10001);
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());
		Admin.AdminBuilder ab = Admin.builder().sn(this.genSn()).account(req.getAccount()).password(req.getPassword()).name(req.getName()).email(req.getEmail()).phone(req.getPhone());
		ab.resources(req.getResources()).status(status).comment(req.getComment());
		if (null != req.getValidTime()) {
			ab.validTime(new Timestamp(req.getValidTime().getTime() + 86399000));
		} else {
			ab.validTime(null);
		}
		ab.loginIp("0.0.0.0").loginTime(now).operator(operator).updateTime(now).createTime(now);
		ab.system(this.sysService.get(req.getSystem()));
		Admin a = ab.build();
		a = this.adminDao.saveAndFlush(a);

		this.adminRoleService.save(a, req.getRoles(), operator);
		
		return a;
	}

	@Transactional
	public Admin update(UpdateAdminReq req, String operator) {
		
		if (!this.existsOid(req.getOid())) {
			// 管理员不存在
			throw OPException.getException(10103);
		}
		Admin a = this.read(req.getOid());
		a.setName(req.getName());
		a.setEmail(req.getEmail());
		a.setPhone(req.getPhone());
		if (null != req.getValidTime()) {
			a.setValidTime(new Timestamp(req.getValidTime().getTime() + 86399000));
		} else {
			a.setValidTime(null);
		}
		a.setComment(req.getComment());
		a.setOperator(operator);
		a.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		a = this.adminDao.saveAndFlush(a);

		this.adminRoleService.save(a, req.getRoles(), operator);
		return a;
	}

	@Transactional
	public Admin upStatus(String oid, String status, String operator) {
		// 管理员不存在
		if (!this.existsOid(oid)) {
			throw OPException.getException(10103);
		}
		Admin a = this.read(oid);
		return this.upStatus(a, status, operator);
	}

	@Transactional
	public Admin upStatus(Admin admin, String status, String operator) {
		admin.setStatus(status);
		admin.setOperator(operator);
		admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		admin = this.adminDao.save(admin);
		return admin;
	}

	@Transactional
	public Admin login(String account, String password, String system, String ip) {
		Sys sys = this.sysService.get(system);
		Admin admin = this.adminDao.findByAccountAndSystem(account, sys);
		if (null == admin) {
			// 管理员不存在
			throw OPException.getException(10103);
		}
		
		if (!admin.getPassword().equals(password)) {
			// 密码校验失败
			throw OPException.getException(10104);
		}

		if (admin.getStatus().equals(Admin.STATUS_FREEZE)) {
			// 管理员已锁定
			throw OPException.getException(10105);
		}

		if (null != admin.getValidTime() && admin.getValidTime().getTime() < System.currentTimeMillis()) {
			// 管理员已过期
			throw OPException.getException(10107);
		}

		admin.setLoginIp(ip);

		Timestamp now = new Timestamp(System.currentTimeMillis());
		admin.setLoginTime(now);
		this.adminDao.save(admin);

		// 记录日志
		AdminLog adminLog = AdminLog.builder().admin(admin).type(AdminLog.TYPE_LOGIN).operateIp(ip).operator(admin.getOid()).createTime(now).updateTime(now).build();
		this.adminLogDao.saveAndFlush(adminLog);
		return admin;
	}

	@Transactional
	public boolean existsAccount(String account, Sys system) {
		Admin admin = this.adminDao.findByAccountAndSystem(account, system);
		return null != admin;
	}

	public boolean existsOid(String oid) {
		Admin admin = this.adminDao.findOne(oid);
		return null != admin;
	}

	@Transactional
	public Admin read(String oid) {
		Admin a = this.adminDao.findOne(oid);
		if (null == a) {
			throw OPException.getException(10103);
		}
		return a;
	}

	@Transactional
	public List<Admin> read(String[] oids) {
		List<Admin> list = this.adminDao.findByOidIn(oids);
		return list;
	}

	@Transactional
	public AdminListResp search(Specification<Admin> spec, String system, boolean roleable, boolean authable, boolean passwordable, int page, int rows) {

		Sys sys = this.sysService.get(system);

		Specification<Admin> systemSpec = new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("system").as(Sys.class), sys);
			}
		};

		Sort sort = new Sort(new Order(Direction.ASC, "account"), new Order(Direction.ASC, "name"));
		Pageable pageable = new PageRequest(page, rows, sort);
		Page<Admin> resultSet = this.adminDao.findAll(Specifications.where(spec).and(systemSpec), pageable);

		AdminListResp resp = new AdminListResp(resultSet.getTotalElements());

		if (resultSet.getContent().size() != 0) {

			Map<String, List<Role>> roles = new HashMap<String, List<Role>>();
			if (roleable) {
				List<AdminRole> ars = this.adminRoleService.findByAdmin(resultSet.getContent());
				if (null != ars && ars.size() > 0) {
					for (AdminRole ar : ars) {
						if (!roles.containsKey(ar.getAdmin().getOid())) {
							roles.put(ar.getAdmin().getOid(), new ArrayList<Role>());
						}
						roles.get(ar.getAdmin().getOid()).add(ar.getRole());
					}
				}
			}

			for (Admin admin : resultSet.getContent()) {
				AdminResp r = new AdminResp(admin, passwordable);
				if (roleable && roles.containsKey(admin.getOid())) {
					List<Role> rs = roles.get(admin.getOid());
					List<RoleResp> rsr = new ArrayList<RoleResp>();
					for (Role role : rs) {
						rsr.add(new RoleResp(role));
					}
					r.setRoles(rsr);
				}
				resp.getRows().add(r);
			}

		}

		return resp;
	}
	
	@Transactional
	public Admin freeze(String oid, String operator, String operateIp) {
		Admin admin = this.read(oid);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		admin.setStatus(Admin.STATUS_FREEZE);
		admin.setUpdateTime(now);
		admin.setOperator(operator);
		admin = this.adminDao.save(admin);

		// 记录日志
		AdminLog adminLog = new AdminLog();
		adminLog.setAdmin(admin);
		adminLog.setType(AdminLog.TYPE_FREEZE);
		adminLog.setOperateIp(operateIp);
		adminLog.setOperator(operator);
		adminLog.setCreateTime(now);
		adminLog.setUpdateTime(now);
		this.adminLogDao.saveAndFlush(adminLog);
		return admin;
	}

	@Transactional
	public Admin unfreeze(String oid, String operator, String operateIp) {
		Admin admin = this.read(oid);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		admin.setStatus(Admin.STATUS_VALID);
		admin.setUpdateTime(now);
		admin.setOperator(operator);
		admin = this.adminDao.save(admin);

		// 记录日志
		AdminLog adminLog = AdminLog.builder().admin(admin).type(AdminLog.TYPE_UNFREEZE).operateIp(operateIp).operator(operator).createTime(now).updateTime(now).build();
		this.adminLogDao.saveAndFlush(adminLog);
		return admin;
	}

	@Transactional
	public AdminInfoResp info(String oid) {
		Admin admin = this.read(oid);
		AdminInfoResp r = new AdminInfoResp(admin);
		List<AdminRole> roles = this.adminRoleService.findByAdmin(admin);
		if (null != roles && roles.size() > 0) {
			for (AdminRole ar : roles) {
				r.getRoles().add(ar.getRole().getOid());
			}
		}
		return r;
	}

	/**
	 * 密码重置
	 * 
	 * @param adminOid
	 * @param password
	 * @return
	 */
	@Transactional
	public Admin resetPassword(String operator, ResetPasswordReq form, String operateIp) {
		if (form.getOriginalPassword() == null || "".equals(form.getOriginalPassword()) || form.getNewPassword() == null || "".equals(form.getNewPassword())) {
			// error.define[10109]=输入的密码不能为空
			throw OPException.getException(10109);
		}
		Admin admin = this.read(operator);
		
		// 判断原密码是否正确，新密码是否和原密码相同
		if (!admin.getPassword().equals(form.getOriginalPassword())) {
			// error.define[10106]=输入的原登录密码和用户登录密码不一致
			throw OPException.getException(10106);
		}
		
		if (form.getOriginalPassword().equals(form.getNewPassword())) {
			// error.define[10108]=输入的原登录密码和输入的新登录密码不能一致
			throw OPException.getException(10108);
		}
		admin.setPassword(form.getNewPassword());
		admin = this.adminDao.saveAndFlush(admin);

		// 记录日志
		Timestamp now = new Timestamp(System.currentTimeMillis());
		AdminLog adminLog = AdminLog.builder().admin(admin).updateTime(now).createTime(now).type(AdminLog.TYPE_RESETPWD).operateIp(operateIp).operator(operator).build();
		this.adminLogDao.saveAndFlush(adminLog);
		return admin;
	}

	/**
	 * 退出登录
	 * 
	 * @param adminOid
	 * @return
	 */
	@Transactional
	public void logout(String adminOid, String operateId) {
		Admin admin = this.read(adminOid);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		AdminLog adminLog = AdminLog.builder().admin(admin).type(AdminLog.TYPE_LOGOUT).operator(adminOid).operateIp(operateId).updateTime(now).createTime(now).build();
		this.adminLogDao.saveAndFlush(adminLog);
	}

	@Transactional
	public Admin genPassword(String operator, String adminOid, String operateIp) {
		Admin admin = this.read(adminOid);
		Random r = new Random();
		String pwd = "";
		for (int i = 0; i < 6; i++) {
			pwd += r.nextInt(10);
		}
		admin.setPassword(pwd);
		admin = this.adminDao.saveAndFlush(admin);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		AdminLog adminLog = AdminLog.builder().admin(admin).updateTime(now).createTime(now).type(AdminLog.TYPE_GENPWD).operateIp(operateIp).operator(operator).build();
		this.adminLogDao.saveAndFlush(adminLog);
		return admin;
	}
	
}
