package com.guohuai.tulip.platform.statistic;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 统计分析Dao
 * 
 * @author suzhicheng
 *
 */
public interface StatisticDao
		extends JpaRepository<StatisticEntity, String>, JpaSpecificationExecutor<StatisticEntity> {

	@Query(value = "SELECT COUNT(CASE WHEN NOW()>finish AND useTime IS NULL THEN 1 END),COUNT(CASE WHEN STATUS='used' OR STATUS='writeOff' OR STATUS='invalid' THEN 1 END),"
			+ "COUNT(CASE WHEN STATUS='notUsed' THEN 1 END),COUNT(*) useCount,NAME,TYPE FROM T_TULIP_USER_COUPON GROUP BY couponBatch", nativeQuery = true)
	public List<Object[]> listByCouponType();

	@Query(value = "SELECT COUNT(CASE WHEN NOW()>a.finish AND a.useTime IS NULL THEN 1 END),COUNT(CASE WHEN a.STATUS='used' OR a.STATUS='writeOff' OR a.STATUS='invalid' THEN 1 END),"
			+ "COUNT(CASE WHEN a.STATUS='notUsed' THEN 1 END),COUNT(*) useCount,b.title FROM T_TULIP_EVENT b LEFT JOIN T_TULIP_USER_COUPON a ON a.eventId = b.oid  "
			+ "WHERE ?1<b.finish AND ?2>b.start GROUP BY b.oid ", nativeQuery = true)
	public List<Object[]> listByEvent(Date start, Date finish);
}
