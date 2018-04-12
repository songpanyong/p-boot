package com.guohuai.points.dao;

import com.guohuai.points.entity.AccountOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AccountOrderDao extends JpaRepository<AccountOrderEntity, String>, JpaSpecificationExecutor<AccountOrderEntity> {
	
	@Query(value = "SELECT * FROM T_TULIP_ACCOUNT_ORDER WHERE orderNo = ?1", nativeQuery = true)
	public AccountOrderEntity findByOrderNo(String orderNo);
	
	@Query(value = "SELECT * FROM T_TULIP_ACCOUNT_ORDER WHERE orderStatus = '1' AND userOid = ?1 ORDER BY createTime DESC;", nativeQuery = true)
	public AccountOrderEntity findRecordByUserOid(String userOid);
	
}
