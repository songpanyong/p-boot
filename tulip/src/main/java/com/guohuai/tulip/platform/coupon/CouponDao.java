package com.guohuai.tulip.platform.coupon;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface CouponDao extends JpaRepository<CouponEntity, String>, JpaSpecificationExecutor<CouponEntity> {
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON "
			+ "WHERE isdel='no' "
			+ "AND remainCount>0 "
			+ "AND oid in ?1", nativeQuery = true)
	public List<CouponEntity> findCouponsByCouponIds(String[] couponIds);
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON "
			+ "WHERE type = ?1 AND isdel='no'  "
			+ "AND remainCount>0 "
			+ "AND IF(totalAmount IS NULL, remainAmount IS NULL, remainAmount > 0)", nativeQuery = true)
	public List<CouponEntity> counponListByType(String type);

	@Query(value="UPDATE T_TULIP_COUPON  "
			+ "SET isdel = 'yes',updateTime=now() "
			+ "WHERE oid = ?1",nativeQuery = true)
	@Modifying
	public int deleteCoupon(String oid);
	
	@Modifying
	@Query(value = "SELECT c.* "
			+ "FROM T_TULIP_EVENT a JOIN "
			+ "T_TULIP_EVENT_ITEM b ON a.oid=b.eventId "
			+ "JOIN T_TULIP_COUPON c ON b.couponId=c.oid "
			+ "WHERE a.code= ?1", nativeQuery = true)
	public List<CouponEntity> findByCode(String code);
	
	@Query(value = "SELECT c.* "
			+ "FROM T_TULIP_COUPON_RULE a "
			+ "JOIN T_TULIP_RULE b ON a.ruleId=b.oid  "
			+ "JOIN T_TULIP_COUPON c ON a.couponId=c.oid "
			+ "WHERE c.remainCount>0 AND c.isdel='no' AND b.oid=?1 AND b.type=?2", nativeQuery = true)
	public List<CouponEntity> listByRid(String rid, String type);
	
	@Query(value = "SELECT d.* "
			+ "FROM T_TULIP_EVENT_RULE a "
			+ "JOIN T_TULIP_EVENT b ON a.eventId=b.oid  "
			+ "JOIN T_TULIP_COUPON_RULE c ON a.ruleId=c.ruleId  "
			+ "JOIN T_TULIP_COUPON d ON c.couponId=d.oid "
			+ "WHERE b.type=?1 AND b.active='on' AND b.isdel='yes'", nativeQuery = true)
	public List<CouponEntity> listByEventType(String type);
	@Query(value = "SELECT d.* "
			+ "FROM T_TULIP_EVENT_RULE a "
			+ "JOIN T_TULIP_EVENT b ON a.eventId=b.oid  "
			+ "JOIN T_TULIP_COUPON_RULE c ON a.ruleId=c.ruleId  "
			+ "JOIN T_TULIP_COUPON d ON c.couponId=d.oid "
			+ "WHERE b.oid=?1 AND b.active='on' AND b.isdel='yes'", nativeQuery = true)
	public List<CouponEntity> listByEventId(String eventId);
	
	@Query(value="UPDATE T_TULIP_COUPON "
			+ "SET remainCount =remainCount-1,remainAmount=IF(remainAmount IS NULL,NULL,remainAmount-?2)  "
			+ "WHERE oid = ?1 and remainCount-1 >= 0",nativeQuery=true)
	@Modifying
	public int updateRemainCount(String oid,BigDecimal remainAmount);
	
	@Query(value="UPDATE T_TULIP_COUPON "
			+ "SET remainCount =remainCount-1,totalAmount=IF(totalAmount IS NULL,NULL,totalAmount+?2)  "
			+ "WHERE oid = ?1 and remainCount-1 >= 0",nativeQuery=true)
	@Modifying
	public int updateRemainAndTotalAmountCount(String oid,BigDecimal remainAmount);
	
	@Query("UPDATE CouponEntity s "
			+ "SET s.description = ?2,s.products=?3,s.rules=?4  "
			+ "WHERE s.oid = ?1")
	@Modifying
	public void updateDescription(String oid,String desc,String products,String rules);
	
	@Query(value="UPDATE T_TULIP_COUPON "
			+ "SET useCount=IFNULL(useCount,0)+?2 "
			+ "WHERE oid=(SELECT couponBatch FROM T_TULIP_USER_COUPON WHERE oid = ?1)",nativeQuery=true)
	@Modifying
	public int updateUseCount(String oid,int num);
	
	/**
	 * 查询指定活动下发的卡券剩余个数，List表示在多层奖励的模式下，会发放多张卡券
	 * @param eventId
	 * @return
	 */
	@Query(value="SELECT c.remainCount FROM t_tulip_coupon c "
			+ "JOIN t_tulip_coupon_rule cr ON c.oid = cr.couponId "
			+ "JOIN t_tulip_event_rule er ON cr.ruleId = er.ruleId "
			+ "JOIN T_TULIP_EVENT e ON er.eventId = e.oid "
			+ "WHERE e.oid = ?1 ",nativeQuery=true)
	public List<Integer> checkCouponRemainCountByEventId(String eventId);
	
	/**
	 * 查找卡券批次名字是否唯一
	 * 
	 * @param couponName
	 * @return
	 */
	@Query(value="SELECT count(*) FROM t_tulip_coupon WHERE `name` = ?1 ",nativeQuery=true)
	public int findOnlyCouponName(String couponName);
	
	/**
	 * 
	 * @param couponName
	 * @param oid
	 * @return
	 */
	@Query(value="SELECT count(*) FROM t_tulip_coupon WHERE `name` = ?1 AND oid <> ?2",nativeQuery=true)
	public int findOnlyCouponName(String couponName, String oid);

	/**
	 * 修改 卡券批次的核销总金额
	 * @param oid
	 * @param writeOffAmount
	 * @return
	 */
	@Query(value="UPDATE T_TULIP_COUPON "
			+ "SET writeOffTotalAmount=IFNULL(writeOffTotalAmount,0)+?2 "
			+ "WHERE oid= ?1", nativeQuery=true)
	@Modifying
	public int updateUseCouponWriteOffTotalAmount(String oid, BigDecimal writeOffAmount);
	
	/**
	 * 查询已创建总额
	 * <pre>
	 * 匹配统计到已上架活动中关联的卡券总额
	 * </pre>
	 * @param types 卡券类型
	 * @return [0]type, [1]卡券总额
	 */
	@Query(value="SELECT c.type, SUM(c.totalAmount) allCreateAmount "
			+ "FROM t_tulip_coupon c, t_tulip_event_rule r, t_tulip_event e, t_tulip_coupon_rule cr "
			+ "where e.oid = r.eventId AND r.ruleId = cr.ruleId AND cr.couponId = c.oid "
			+ "AND e.`status` = 'pass' AND e.active = 'on' "
			+ "AND c.type IN (?1) AND c.isdel = 'no' "
			+ "GROUP BY c.type", nativeQuery = true)
	public List<Object[]> queryAllCreateAmount(List<String> types);
}
