package com.guohuai.tulip.platform.coupon.couponOrder;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CouponOrderDao
		extends JpaRepository<CouponOrderEntity, String>, JpaSpecificationExecutor<CouponOrderEntity> {

	@Query(value = "SELECT SUM(userAmount),COUNT(*),MIN(createTime) FROM T_TULIP_COUPON_ORDER WHERE  userId=?1", nativeQuery = true)
	public List<Object[]> findUserAmount(String userId);

	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE couponId = ?1 ",nativeQuery = true)
	public List<CouponOrderEntity> listOrderByCouponId(String couponId);
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE couponId = ?1 AND orderStatus ='success' ",nativeQuery = true)
	public CouponOrderEntity findOrderByCouponId(String couponId);
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER "
			+ "WHERE couponId = ?1 "
			+ "AND userId=?2 "
			+ "AND orderStatus ='success' ",nativeQuery = true)
	public CouponOrderEntity findOrderByCouponIdAndUserId(String couponId,String userId);
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE orderCode = ?1 AND orderType = ?2 ",nativeQuery = true)
	public CouponOrderEntity findOrderByOrderCode(String orderCode,String orderType);
	
	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE couponId=?1 ORDER BY createTime DESC LIMIT 0,1  ", nativeQuery = true)
	public CouponOrderEntity findCouponOrder(String couponId);

	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE productId=?1 AND orderStatus='success' AND couponId IS NOT NULL ", nativeQuery = true)
	public List<CouponOrderEntity> findUsedCouponByProductId(String productId);
	
	@Query(value = "SELECT COUNT(*) FROM T_TULIP_COUPON_ORDER "
            + "WHERE userId=?1 AND orderStatus='success' AND orderType='invest' ", nativeQuery = true)
    public Long countByUserId(String checkOid);
	
	/**
	 * 查询用户 未生成佣金订单
	 * @param userId
	 * @return
	 */
	@Query(value = "SELECT * FROM T_TULIP_COUPON_ORDER WHERE userId=?1 AND orderStatus='success' AND orderType='invest' AND isMakedCommisOrder = 'no' ", nativeQuery = true)
	public List<CouponOrderEntity> findIsMakedCommisOrderByUserId(String userId);
	
}
