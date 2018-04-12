package com.guohuai.operate.admin.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.operate.admin.Admin;
import com.guohuai.operate.role.Role;

public interface AdminRoleDao extends JpaRepository<AdminRole, String>, JpaSpecificationExecutor<AdminRole> {

	public List<AdminRole> findByAdmin(Admin admin);

	public List<AdminRole> findByRole(Role role);

	public List<AdminRole> findByAdminIn(List<Admin> admins);

	@Query(value = "SELECT roleOid AS oid, COUNT(*) AS 'count' FROM T_OP_ADMIN_ROLE GROUP BY roleOid", nativeQuery = true)
	public List<Object[]> countByRole();
}
