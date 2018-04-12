package com.guohuai.operate.role;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.operate.admin.Admin;
import com.guohuai.operate.admin.role.AdminRole;
import com.guohuai.operate.admin.role.AdminRoleService;
import com.guohuai.operate.component.exception.OPException;
import com.guohuai.operate.system.Sys;
import com.guohuai.operate.system.SysService;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private SysService systemService;

	@Transactional
	public Role save(CreateRoleReq req, String operator) {
		if (this.exists(req.getName(), this.systemService.get(req.getSystemOid()))) {
			// 角色已存在
			throw OPException.getException(10601);
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());
		Role.RoleBuilder rb = Role.builder().name(req.getName()).system(this.systemService.get(req.getSystemOid())).operator(operator).updateTime(now).createTime(now);
		Role r = rb.build();
		r = this.roleDao.saveAndFlush(r);
		return r;
	}

	@Transactional
	public Role update(UpdateRoleReq req, String operator) {
		Role r = this.get(req.getOid());
		r.setName(req.getName());
		r.setSystem(this.systemService.get(req.getSystemOid()));
		r.setOperator(operator);
		r.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		r = this.roleDao.save(r);

		return r;
	}

	@Transactional
	public List<Role> list(String... oids) {
		return this.roleDao.findByOidIn(oids);
	}

	@Transactional
	public void delete(String oid, String operator) {
		Role role = this.get(oid);
		this.delete(role, operator);
	}

	@Transactional
	public void delete(Role role, String operator) {

		List<AdminRole> ars = this.adminRoleService.findByRole(role);
		Map<String, Admin> admins = new HashMap<String, Admin>();
		for (AdminRole ar : ars) {
			this.adminRoleService.delete(ar);
			if (!admins.containsKey(ar.getAdmin().getOid())) {
				admins.put(ar.getAdmin().getOid(), ar.getAdmin());
			}
		}

		this.roleDao.delete(role);
	}

	@Transactional
	public RoleListResp list(Specification<Role> spec, int page, int rows, boolean stats) {
		Sort sort = new Sort(new Order(Direction.ASC, "name"));
		Pageable split = new PageRequest(page, rows, sort);
		Page<Role> result = this.roleDao.findAll(spec, split);

		RoleListResp resp = new RoleListResp();
		resp.setTotal(result.getTotalElements());

		if (null != result && result.getContent().size() > 0) {
			Map<String, Integer> arcm = null;
			if (stats) {
				arcm = this.adminRoleService.countByRole();
			}

			for (Role role : result.getContent()) {
				RoleResp r = new RoleResp(role);
				if (stats) {
					int arc = arcm.containsKey(role.getOid()) ? arcm.get(role.getOid()) : 0;
					r.setArc(arc);
					r.setRac(0);
				}
				resp.getRows().add(r);
			}
		}

		return resp;
	}

	@Transactional
	public RoleListResp list(Specification<Role> spec, boolean stats) {
		Sort sort = new Sort(new Order(Direction.ASC, "name"));
		List<Role> result = this.roleDao.findAll(spec, sort);

		RoleListResp resp = new RoleListResp();
		resp.setTotal(result.size());

		if (null != result && result.size() > 0) {
			Map<String, Integer> arcm = null;
			if (stats) {
				arcm = this.adminRoleService.countByRole();
			}

			for (Role role : result) {
				RoleResp r = new RoleResp(role);
				if (stats) {
					int arc = arcm.containsKey(role.getOid()) ? arcm.get(role.getOid()) : 0;
					r.setArc(arc);
					r.setRac(0);
				}
				resp.getRows().add(r);
			}
		}

		return resp;
	}

	@Transactional
	public Role get(String oid) {
		Role r = this.roleDao.findOne(oid);
		if (null == r) {
			// 角色不存在
			throw OPException.getException(10602);
		}
		return r;
	}

	@Transactional
	public boolean exists(String name, Sys system) {
		List<Role> list = this.roleDao.findByNameAndSystem(name, system);
		return list.size() > 0;
	}

}
