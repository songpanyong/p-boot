package com.guohuai.tulip.platform.commissionorder;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CommissionOrderDao extends JpaRepository<CommissionOrderEntity, String>, JpaSpecificationExecutor<CommissionOrderEntity>{

	
	@Query(value = "SELECT *  FROM T_TULIP_COMMISSION_ORDER WHERE orderStatus='pass' AND userId=?1", nativeQuery = true)
	List<CommissionOrderEntity> queryByUserId(String userId);

	@Query(value = "SELECT * FROM T_TULIP_COMMISSION_ORDER WHERE orderStatus = ?1 ", nativeQuery = true)
	List<CommissionOrderEntity> commissionListByOrderStatus(String orderStatus);

	@Query("UPDATE CommissionOrderEntity s SET s.orderStatus = 'pass',auditTime=now(),auditor=?2 WHERE s.oid = ?1 AND orderStatus='pending' ")
	@Modifying
	public int commissionOrderPass(String oid,String operateName);

	@Query("UPDATE CommissionOrderEntity s SET s.orderStatus = 'pass', auditTime=now(), s.auditor = ?1 WHERE s.oid in ?2")
	@Modifying
	public void commissionOrderBatchPass(String auditor, String[] oids);
	
	@Query("UPDATE CommissionOrderEntity s SET s.orderStatus = 'refused',auditTime=now(),auditor=?2,rejectAdvice=?3 WHERE s.oid = ?1")
	@Modifying
	public void commissionOrderRefused(String oid,String operateName,String rejectAdvice);
}
