package com.guohuai.tulip.platform.event;

import java.sql.Date;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EventDao extends JpaRepository<EventEntity, String>, JpaSpecificationExecutor<EventEntity> {
	@Query(value="UPDATE T_TULIP_EVENT s SET s.description = ?2, s.title = ?3,s.updateUser=?4,s.updateTime=SYSDATE(),s.start=?5,s.finish=?6 WHERE s.oid = ?1  AND isdel='no'",nativeQuery=true)
	@Modifying
	@ConfigurationProperties()
	public void update(String oid, String description, String title, String updateUser, Date start, Date finish);

	@Query(value="UPDATE T_TULIP_EVENT SET active = 'on',updateTime = SYSDATE(), updateUser = ?2 WHERE oid = ?1  AND isdel='no'",nativeQuery=true)
	@Modifying
	public int activiting(String oid, String updateUser);

	@Query(value="UPDATE T_TULIP_EVENT SET active = 'off', updateTime = SYSDATE(), updateUser = ?2 WHERE oid NOT IN ?1 AND status ='pass'  AND isdel='no' ",nativeQuery=true)
	@Modifying
	public int delactiviting(String[] oids, String releaseOpe);

	@Query(value="UPDATE T_TULIP_EVENT SET active = 'off', updateTime = SYSDATE(), updateUser = ?1 WHERE status IN ('pass')  AND isdel='no'",nativeQuery=true)
	@Modifying
	public int delactiviting(String releaseOpe);

	@Query(value = "SELECT * FROM T_TULIP_EVENT WHERE status = 'pass' AND isdel='no'",nativeQuery=true)
	public List<EventEntity> getPassDataList();

	@Query(value = "SELECT * FROM T_TULIP_EVENT WHERE status = 'pass' AND active = 'on' AND NOW() BETWEEN START AND finish AND isdel='no' AND type='custom'",nativeQuery=true)
	public List<EventEntity> getPassCustomList();
	
	@Query(value="UPDATE T_TULIP_EVENT SET active = 'on' WHERE  oid IN ?1 AND status ='pass'  AND isdel='no' ",nativeQuery=true)
	@Modifying
	public void activeOtherOn(String[] oids);
	
	@Query(value="UPDATE T_TULIP_EVENT SET active = 'off' WHERE  oid NOT IN ?1 AND status ='pass'  AND isdel='no' ",nativeQuery=true)
	@Modifying
	public void activeOtherOff(String[] oids);
	
	@Query(value="UPDATE T_TULIP_EVENT SET active = 'off' WHERE status ='pass' AND isdel='no' ",nativeQuery=true)
	@Modifying
	public void activeOffOtherAll();
	
	@Query(value="UPDATE T_TULIP_EVENT SET isdel = 'yes' WHERE oid = ?1 AND isdel='no' ",nativeQuery=true)
	@Modifying
	public void deleteEvent(String oids);
	
	@Query(value = "SELECT * FROM T_TULIP_EVENT "
			+ "WHERE type=?1 AND active='on' AND isdel='no' AND NOW() BETWEEN START AND finish",nativeQuery=true)
	public List<EventEntity> eventListByType(String type);
	
	@Query(value="UPDATE T_TULIP_EVENT "
			+ "SET active=IF(NOW() BETWEEN START AND finish,'on','off') "
			+ "WHERE STATUS='pass' AND active='wait'",nativeQuery=true)
	@Modifying
	public int autoOnEvent();
	
	@Query(value="SELECT COUNT(*) FROM T_TULIP_EVENT WHERE finish>now() AND status = 'pass' AND isdel='no'",nativeQuery=true)
	public int eventCount();
	
	@Query(value="SELECT d.couponAmount,d.validPeriod "
			+ "FROM T_TULIP_EVENT a "
			+ "LEFT JOIN T_TULIP_EVENT_RULE b ON a.`oid`=b.`eventId` "
			+ "LEFT JOIN T_TULIP_COUPON_RULE c ON b.`ruleId`=c.`ruleId` "
			+ "LEFT JOIN T_TULIP_COUPON d ON c.`couponId`=d.`oid` AND d.type=?2 AND d.isdel='no' "
			+ "WHERE a.`type`=?1 AND a.isdel='no' AND a.`active`='on' AND a.`status`='pass' AND d.remainCount>0",nativeQuery=true)
	public List<Object[]> getEventCouponInfo(String eventType,String couponType);
	
	@Query(value="SELECT a.* "
			+ "FROM T_TULIP_EVENT a "
			+ "INNER JOIN T_TULIP_EVENT_RULE b ON a.oid=b.eventId "
			+ "INNER JOIN T_TULIP_RULE c ON c.oid=b.ruleId "
			+ "INNER JOIN T_TULIP_COUPON_RULE d ON c.oid=d.ruleId "
			+ "INNER JOIN T_TULIP_COUPON e ON e.oid=d.couponId  AND d.isdel='no' "
			+ "WHERE e.couponAmount=?1 AND e.type=?2 AND a.type='custom' AND a.isdel='no' ",nativeQuery=true)
	public EventEntity getCustomEventId(String money, String couponType);
	
	@Query(value="UPDATE T_TULIP_EVENT SET active = ?2 "
			+ "WHERE oid = ?3 "
			+ "AND (active =?1 OR active='wait')"
			+ "AND status ='pass' "
			+ "AND isdel='no' ",nativeQuery=true)
	@Modifying
	public int activeEvent(String formActive, String toActive, String oid);
	
	@Query(value="SELECT * "
			+ "FROM T_TULIP_EVENT "
			+ "WHERE oid=?1 "
			+ "AND type='custom' "
			+ "AND status='pass' "
			+ "AND active='on' "
			+ "AND isdel='no' ",nativeQuery=true)
	public EventEntity findCustomEventByOid(String eventId);
	
	/**
	 * 获取某个场景下需要发放的活动，满足1，在有效期内的活动；2，活动需要下发的卡券的剩余数量>0; 去重
	 * @param eventType
	 * @return
	 */
	@Query(value="SELECT DISTINCT e.*  FROM t_tulip_event e "
			+ "LEFT JOIN t_tulip_event_rule er ON e.oid = er.eventId "
			+ "LEFT JOIN t_tulip_coupon_rule cr ON er.ruleId = cr.ruleId "
			+ "LEFT JOIN t_tulip_coupon c ON cr.couponId = c.oid "
			+ "WHERE e.type = ?1 AND e.active = 'on' AND e.isdel = 'no' "
			+ "AND c.remainCount > 0 "
			+ "AND NOW() BETWEEN e.start AND e.finish ",nativeQuery=true)
	public List<EventEntity> queryEventCouponInfo(String eventType);
	
	/**
	 * 获取某个场景下需要发放的活动，满足1，在有效期内的活动；2，活动需要下发的卡券的剩余数量>0; 去重
	 * @param eventType
	 * @return
	 */
	@Query(value="SELECT DISTINCT e.*  FROM t_tulip_event e "
			+ "LEFT JOIN t_tulip_event_rule er ON e.oid = er.eventId "
			+ "LEFT JOIN t_tulip_coupon_rule cr ON er.ruleId = cr.ruleId "
			+ "LEFT JOIN t_tulip_coupon c ON cr.couponId = c.oid "
			+ "WHERE e.type = ?1 AND e.active = 'on' AND e.isdel = 'no' "
			+ "AND c.remainCount > 0 "
			+ "AND NOW() BETWEEN e.start AND e.finish ",nativeQuery=true)
	public List<EventEntity> queryEventListByType(String eventType);
	
	/**
	 * 统计需要下发的卡券类型、个数
	 * @param ruleIds
	 * @return
	 */
	@Query(value="SELECT type, count(1) couponCount FROM t_tulip_coupon c "
			+ "LEFT JOIN t_tulip_coupon_rule cr ON c.oid = cr.couponId "
			+ "WHERE cr.ruleId IN ?1 GROUP BY type ",nativeQuery=true)
	public List<Object[]> queryEventCouponCount(Object[] ruleIds);
	
	/**
	 * 查询在时间范围内的、上架的可用的活动列表
	 * 
	 * @return
	 */
	@Query(value = "SELECT * FROM T_TULIP_EVENT "
			+ "WHERE active='on' AND isdel='no' AND NOW() BETWEEN START AND finish",nativeQuery=true)
	public List<EventEntity> initEventList();
	
	/**
	 * 查询活动关联的下发卡券信息
	 * @param eventId
	 * @param eventType
	 * @return [0]couponId、[1]卡券名称、[2]卡券类型、[3]卡券剩余数量、[4]卡券状态
	 */
	@Query(value="SELECT c.oid, c.`name`, c.type, c.remainCount, c.isdel FROM t_tulip_event e "
			+ "LEFT JOIN t_tulip_event_rule er ON e.oid = er.eventId "
			+ "LEFT JOIN t_tulip_coupon_rule cr ON er.ruleId = cr.ruleId "
			+ "LEFT JOIN t_tulip_coupon c ON cr.couponId = c.oid "
			+ "WHERE e.oid = ?1 AND e.type = ?2 ",nativeQuery=true)
	public List<Object[]> queryCouponBatchByEventId(String eventId, String eventType);
	
	/**
	 * 获取当前活动需要下发的卡券的剩余可用数量（[0]=卡券id、[1]=卡券名称、[2]卡券类型、[3]卡券剩余数量）
	 * @param eventId 当前活动Id
	 * @return
	 */
	@Query(value="SELECT c.oid, c.`name`, c.type, c.remainCount FROM t_tulip_event e "
			+ "LEFT JOIN t_tulip_event_rule er ON e.oid = er.eventId "
			+ "LEFT JOIN t_tulip_coupon_rule cr ON er.ruleId = cr.ruleId "
			+ "LEFT JOIN t_tulip_coupon c ON cr.couponId = c.oid "
			+ "WHERE e.oid = ?1 ",nativeQuery=true)
	public List<Object[]> queryCouponInfoByEventId(String eventId);
	
	@Query(value = "SELECT count(*) FROM T_TULIP_EVENT WHERE title = ?1 ", nativeQuery=true)
	public int findEventByTitle(String title);
	
	@Query(value = "SELECT count(*) FROM T_TULIP_EVENT WHERE title = ?1 AND oid <> ?2", nativeQuery=true)
	public int findEventByTitleOid(String title, String oid);
	
	/**
	 * 判断活动是否有效
	 * <pre>
	 * 判断条件包括：审批通过、已上架、活动在有效期内、未删除、自定义类型
	 * </pre>
	 * @return
	 */
	@Query(value = "SELECT count(*) FROM T_TULIP_EVENT WHERE oid = ?1 AND status = 'pass' "
			+ "AND active = 'on' AND NOW() BETWEEN START AND finish AND isdel='no' AND type='custom'",nativeQuery=true)
	public int getPassCustomEventById(String eventId);
}
