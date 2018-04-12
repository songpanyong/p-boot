package com.guohuai.payadapter.reconciliation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
/**
 * 对账DAO
 * @author chendonghui
 *
 */
public interface BankReconciliationPassDao extends JpaRepository<BankReconciliationPassEntity, String>, JpaSpecificationExecutor<BankReconciliationPassEntity> {

	@Query("from BankReconciliationPassEntity a where a.channelId = ?1 and a.tradStatus = ?2")
	public List<BankReconciliationPassEntity> getListByName(String channelId, String tradStatus);
	
}
