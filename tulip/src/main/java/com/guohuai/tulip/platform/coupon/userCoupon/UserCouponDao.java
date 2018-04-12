package com.guohuai.tulip.platform.coupon.userCoupon;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserCouponDao extends JpaRepository<UserCouponEntity, String>, JpaSpecificationExecutor<UserCouponEntity> {	

	@Query(value="UPDATE T_TULIP_USER_COUPON  SET status = ?1,useTime=now() "
			+ "WHERE oid = ?2 AND status = 'notUsed' AND userId = ?3 ", nativeQuery = true)
	@Modifying
	public int useUserCoupon(String status,String oid, String userId);
	
	@Query(value="UPDATE T_TULIP_USER_COUPON  SET status = ?1,useTime=null "
			+ "WHERE oid = ?2 AND status='used' AND userId = ?3 ", nativeQuery = true)
	@Modifying
	public int resetUserCoupon(String status,String oid, String userId);
	
	@Modifying
	@Query(value = "SELECT * FROM T_TULIP_USER_COUPON WHERE userId=?1", nativeQuery = true)
	public List<UserCouponEntity> getMyCouponList(String userId);
	
	@Query(value = "SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 AND  eventId=?2 AND couponBatch=?3", nativeQuery = true)
	public List<UserCouponEntity> checkUserCoupon(String userId,String eventId,String batch);

	@Query(value="UPDATE T_TULIP_USER_COUPON SET STATUS='expired' "
			+ "WHERE STATUS='notUsed' and NOW() >finish", nativeQuery = true)
	@Modifying
	public void updateCouponForExpired();
	
	@Query(value="SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() between start "
			+ "AND finish AND type<>'redPackets'", nativeQuery = true)
	public List<UserCouponEntity> getCouponList(String userId);
	
	@Query(value="SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE oid=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() between start "
			+ "AND finish AND type='cashCoupon'", nativeQuery = true)
	public UserCouponEntity getCashCoupon(String oid);
	
	@Query(value="UPDATE T_TULIP_USER_COUPON "
			+ "SET status='writeOff', settlement=now() "
			+ "WHERE useTime IS NOT NULL AND status='used' AND oid IN ?1", nativeQuery = true)
	@Modifying
	public int updateCouponForWriteOff(String[] couponIds);
	
	@Query(value="UPDATE T_TULIP_USER_COUPON "
			+ "SET STATUS='invalid', settlement=now() "
			+ "WHERE useTime IS NOT NULL AND status='used' AND oid IN ?1", nativeQuery = true)
	@Modifying
	public int updateCouponForInvalid(String[] couponIds);
	
	@Query(value="SELECT COUNT(CASE WHEN useTime IS NOT NULL THEN 1 END),COUNT(*) useCount FROM T_TULIP_USER_COUPON "
			+ "WHERE couponBatch=?1", nativeQuery = true)
	public List<Object[]> findIssueCount(String oid);
	
	@Query(value="SELECT COUNT(*) FROM T_TULIP_USER_COUPON ", nativeQuery = true)
	public int isseCount();
	
	@Query(value="SELECT COUNT(*) FROM T_TULIP_USER_COUPON "
			+ "WHERE useTime IS NOT NULL ", nativeQuery = true)
	public Integer useCount();

	@Query(value="SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() between start AND finish AND type=?2", nativeQuery = true)
	public List<UserCouponEntity> counponListByUidAndType(String userId, String type);
	
	
	@Query(value="SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() NOT between start "
			+ "AND finish AND type='redPackets'", nativeQuery = true)
	public List<UserCouponEntity> counponListExpire(String userId);

	@Query(value="UPDATE T_TULIP_USER_COUPON "
			+ "SET STATUS='used' "
			+ "WHERE userId=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() between start AND finish AND type=?2", nativeQuery = true)
	@Modifying
	public void updateCouponStatus(String userId,String type);

	@Query(value="UPDATE T_TULIP_USER_COUPON "
			+ "SET STATUS='invalid' "
			+ "WHERE userId=?1 "
			+ "AND status='notUsed' "
			+ "AND NOW() between start AND finish "
			+ "AND type='cashCoupon'", nativeQuery = true)
	@Modifying
	public void updateCoupon(String userId);

	@Query(value="SELECT COUNT(*) FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 AND status='notUsed'", nativeQuery = true)
	public Integer countNotUsedNum(String userId);
	
	@Query(value="SELECT COUNT(*) FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 AND status NOT IN ('notUsed','expired') ", nativeQuery = true)
	public Integer countUsedNum(String userId);
	
	@Query(value="SELECT COUNT(*) FROM T_TULIP_USER_COUPON "
			+ "WHERE userId=?1 AND status='expired'", nativeQuery = true)
	public Integer countExpiredNum(String userId);
	
	/**
	 * 查询即将过期的卡券列表（过期前一天）
	 * 
	 * @return
	 */
	@Query(value="SELECT * FROM T_TULIP_USER_COUPON "
			+ "WHERE status='notUsed' "
			+ "AND datediff(now(), date_sub(finish,interval 1 day)) = 0 ", nativeQuery = true)
	public List<UserCouponEntity> getCouponForExpiredList();
	
	@Query(value = "SELECT oid FROM T_TULIP_USER_COUPON WHERE userId=?1 AND status=\'notUsed\' AND NOW() between start AND finish ORDER BY leadTime DESC ", nativeQuery = true)
	public List<String> queryCounponListByUid(String userId);
	
	
	@Query(value = "SELECT userId FROM t_tulip_user_invest_log WHERE phone = ?1 ", nativeQuery = true)
	public List<String> queryUserIdByPhone(String phone);
	
	@Query(value = "SELECT phone FROM t_tulip_user_invest_log WHERE userId = ?1 ", nativeQuery = true)
	public List<String> queryUserPhoneByUserId(String userId);
	
	/**
	 * 记录已使用的卡券为核销
	 * @param oid
	 * @param status
	 * @param writeOffAmount
	 * @param settlement
	 * @return
	 */
	@Query(value="update t_tulip_user_coupon set status =?2, writeOffAmount = ?3, settlement = ?4 "
			+ "WHERE oid= ?1 and status = 'used' ", nativeQuery=true)
	@Modifying
	public int updateUseCouponWriteOffAmount(String oid, String status, BigDecimal writeOffAmount, Timestamp settlement);
	
	/**
	 * 查询卡券批次号
	 * @param oid
	 * @return
	 */
	@Query(value="select couponBatch from t_tulip_user_coupon where oid = ?1 ",nativeQuery=true)
	public String queryCouponBatchByCouponId(String oid);
	
	/**
	 * 统计所有卡券兑付情况
	 * @return [0]卡券类型，[1]已发放金额，[2]已兑付金额（非代金券），[3]代金券已兑付金额,[4]已过期金额
	 */
	@Query(value = "SELECT type, SUM(amount) allGrantAmount, "
			+ "SUM(CASE WHEN `status` = 'writeOff' THEN writeOffAmount ELSE 0 END) allCashAmount, "
			+ "SUM(CASE WHEN `status` = 'used' AND type = 'coupon' THEN amount ELSE 0 END) allCouponCashAmount, "
			+ "SUM(CASE WHEN `status` = 'expired' THEN amount ELSE 0 END) allDueAmount "
			+ "FROM t_tulip_user_coupon GROUP BY type", nativeQuery = true)
	public List<Object[]> querySumCashInfo();
}
