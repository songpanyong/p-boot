package com.guohuai.tulip.platform.intervalsetting;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 基数等级Dao
 */
public interface IntervalSettingDao extends JpaRepository<IntervalSettingEntity, String>, JpaSpecificationExecutor<IntervalSettingEntity> {

	@Query(value = "SELECT * FROM T_TULIP_INTERVAL_SETTING WHERE isdel = 'no' ", nativeQuery = true)
	public List<IntervalSettingEntity> listIntervalSetting();
	
	@Query(value = "SELECT * FROM T_TULIP_INTERVAL_SETTING "
					+ "WHERE ?1 >= startMoney "
					+ "AND endMoney < ?1 "
					+ "AND isdel = 'no' ", nativeQuery = true)
	public IntervalSettingEntity findIntervalSettingByAmount(BigDecimal amount);
	
	@Query(value = "UPDATE T_TULIP_INTERVAL_SETTING SET isdel=?2 WHERE oid=?1 AND isdel = ?3 ", nativeQuery = true)
	@Modifying
	public int activeIntervalSetting(String oid, String toStatus, String formStatus);
	
	
	@Query(value = "SELECT * FROM T_TULIP_INTERVAL_SETTING "
			+ "WHERE (startMoney < ?2 AND  startMoney > ?1) "
			+ "OR (endMoney > ?1 AND endMoney <= ?2 ) "
			+ "OR (endMoney <= ?2 AND startMoney >= ?1 ) "
			+ "OR (endMoney >= ?2 AND startMoney <= ?1 ) LIMIT 1 ", nativeQuery = true)
	public IntervalSettingEntity checkIntervalSettingEntity(BigDecimal beginMoney,BigDecimal endMoney);

}