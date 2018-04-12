package com.guohuai.payadapter.redeem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ConcurrentManagerDao extends JpaRepository<ConcurrentManager, String>, JpaSpecificationExecutor<ConcurrentManager> {
	
	public ConcurrentManager findByHostAndTaskTag(String host,String taskTag);
	
	// 每次查询时更新数据
	@Transactional
	@Query(value = "update t_bank_concurrent_manager set updateTime=SYSDATE() where oid=?1 ", nativeQuery = true)
	@Modifying
	public int updateTime(String oid);
	
}
