package com.guohuai.operate.admin.role;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guohuai.operate.admin.Admin;
import com.guohuai.operate.component.exception.OPException;
import com.guohuai.operate.role.Role;
import com.guohuai.operate.role.RoleService;

@Service
public class AdminRoleService {

	@Autowired
	private AdminRoleDao adminRoleDao;
	@Autowired
	private RoleService roleService;
	

	@Transactional
	public List<AdminRole> save(Admin admin, String[] roles, String operator) {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		List<AdminRole> ars = this.adminRoleDao.findByAdmin(admin);
		Map<String, AdminRole> arms = new HashMap<String, AdminRole>();
		if (null != ars && ars.size() > 0) {
			for (AdminRole ar : ars) {
				arms.put(ar.getRole().getOid(), ar);
			}
		}

		List<AdminRole> r = new ArrayList<AdminRole>();

		if (null != roles && roles.length > 0) {
			List<Role> rs = this.roleService.list(roles);
			if (null != rs && rs.size() > 0) {
				for (Role role : rs) {
					if (arms.containsKey(role.getOid())) {
						AdminRole ar = arms.get(role.getOid());
						r.add(ar);
						arms.remove(role.getOid());
					} else {
						AdminRole.AdminRoleBuilder arb = AdminRole.builder().admin(admin).role(role).operator(operator).updateTime(now).createTime(now);
						AdminRole ar = arb.build();
						ar = this.adminRoleDao.saveAndFlush(ar);
						r.add(ar);
					}
				}
			}
		}

		if (arms.size() > 0) {
			for (String key : arms.keySet()) {
				AdminRole ar = arms.get(key);
				this.delete(ar);
			}
		}

		return r;
	}

	@Transactional
	public void delete(AdminRole adminRole) {
		this.adminRoleDao.delete(adminRole);
	}

	@Transactional
	public List<AdminRole> list(Specification<AdminRole> spec) {
		Sort sort = new Sort(new Order(Direction.ASC, "role.name"));
		List<AdminRole> list = this.adminRoleDao.findAll(spec, sort);
		return list;
	}

	@Transactional
	public List<AdminRole> findByAdmin(Admin admin) {
		List<AdminRole> list = this.adminRoleDao.findByAdmin(admin);
		return list;
	}

	public List<AdminRole> findByAdmin(List<Admin> admins) {
		List<AdminRole> list = this.adminRoleDao.findByAdminIn(admins);
		return list;
	}

	@Transactional
	public List<AdminRole> findByRole(Role role) {
		List<AdminRole> list = this.adminRoleDao.findByRole(role);
		return list;
	}

	@Transactional
	public AdminRole get(String oid) {
		AdminRole ar = this.adminRoleDao.findOne(oid);
		if (null == ar) {
			// 权限不存在
			throw OPException.getException(10801);
		}
		return ar;
	}

	/**
	 * 以角色分组，统计出用户数放到List<Object[]>中
	 */
	@Transactional
	public Map<String, Integer> countByRole() {
		List<Object[]> list = this.adminRoleDao.countByRole();
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (null != list && list.size() > 0) {
			for (Object[] item : list) {
				map.put(item[0].toString(), Integer.parseInt(item[1].toString()));
			}
		}
		return map;
	}

}
