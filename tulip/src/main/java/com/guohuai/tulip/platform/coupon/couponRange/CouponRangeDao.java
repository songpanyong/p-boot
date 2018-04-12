package com.guohuai.tulip.platform.coupon.couponRange;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CouponRangeDao
		extends JpaRepository<CouponRangeEntity, String>, JpaSpecificationExecutor<CouponRangeEntity> {
	@Modifying
	@Query(value = "DELETE FROM T_TULIP_COUPON_RANGE WHERE couponBatch = ?1", nativeQuery = true)
	public int deleteRange(String batch);

	@Modifying
	@Query(value = "SELECT * FROM T_TULIP_COUPON_RANGE  WHERE couponBatch = ?1 ", nativeQuery = true)
	public List<CouponRangeEntity> findByCouponId(String batch);
	
	@Modifying
	@Query(value = "SELECT * FROM T_TULIP_COUPON_RANGE  WHERE couponBatch = ?1 AND productId=?2", nativeQuery = true)
	public List<CouponRangeEntity> findByBatch(String batch,String productId);
	
	@Query(value = "SELECT DISTINCT b.* "
			+ "FROM T_TULIP_USER_COUPON a "
			+ "JOIN T_TULIP_COUPON_RANGE b ON a.couponBatch=b.couponBatch "
			+ "WHERE a.userId=?1" , nativeQuery = true)
	public List<CouponRangeEntity> findByUserId(String userId);
	
}
