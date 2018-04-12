package com.guohuai.tulip.platform.coupon.redpacket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RedpacketDetailDao extends JpaRepository<RedpacketDetailEntity, String>, JpaSpecificationExecutor<RedpacketDetailEntity> {
	
	/**
	 * 获取某个场景下需要发放的活动，满足1，在有效期内的活动；2，活动需要下发的卡券的剩余数量>0; 去重
	 * @param eventType
	 * @return
	 */
//	@Query(value="SELECT DISTINCT e.*  FROM t_tulip_event e "
//			+ "LEFT JOIN t_tulip_event_rule er ON e.oid = er.eventId "
//			+ "LEFT JOIN t_tulip_coupon_rule cr ON er.ruleId = cr.ruleId "
//			+ "LEFT JOIN t_tulip_coupon c ON cr.couponId = c.oid "
//			+ "WHERE e.type = ?1 AND e.active = 'on' AND e.isdel = 'no' "
//			+ "AND c.remainCount > 0 "
//			+ "AND NOW() BETWEEN e.start AND e.finish ",nativeQuery=true)
//	public List<EventEntity> queryEventListByType(String eventType);
	
	/**
	 * 查询当前随机红包批次的生成的所有随机红包
	 * @param ruleIds
	 * @return
	 */
	@Query(value="SELECT * FROM t_tulip_redpacket_random_detail WHERE couponBatchId = ?1 ",nativeQuery=true)
	public List<RedpacketDetailEntity> queryRandomRedListByCouponBitchId(String couponBitchId);
	
}
