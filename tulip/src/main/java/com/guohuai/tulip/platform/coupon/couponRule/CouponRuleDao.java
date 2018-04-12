package com.guohuai.tulip.platform.coupon.couponRule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CouponRuleDao
		extends JpaRepository<CouponRuleEntity, String>, JpaSpecificationExecutor<CouponRuleEntity> {

	@Query(value = "SELECT * FROM T_TULIP_COUPON_RULE WHERE ruleId=?1", nativeQuery = true)
	public List<CouponRuleEntity> listCouponRuleEntityByRID(String rid);

	@Query(value = "SELECT a.* FROM T_TULIP_COUPON_RULE a JOIN T_TULIP_RULE b ON a.ruleId=b.oid WHERE a.couponId=?1 AND b.type=?2", nativeQuery = true)
	public List<CouponRuleEntity> listByCid(String cid, String type);

	@Modifying
	@Query(value = "DELETE FROM T_TULIP_COUPON_RULE WHERE ruleId IN ?1", nativeQuery = true)
	public void deleteCouponRule(String[] rid);
}
