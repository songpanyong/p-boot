package com.guohuai.points.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.points.entity.AccountTransEntity;

public interface AccountTransDao extends JpaRepository<AccountTransEntity, String>, JpaSpecificationExecutor<AccountTransEntity> {

	@Query(value = "SELECT * FROM T_TULIP_ACCOUNT_TRANS WHERE userOid = ?1 AND orderNo = ?2", nativeQuery = true)
	List<AccountTransEntity> findByUserAndOrderNo(String userOid,
			String oldOrderNo);
	
}
