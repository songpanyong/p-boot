package com.guohuai.tulip.platform.coupon.couponRule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CouponRuleService {

	@Autowired
	private CouponRuleDao couponRuleDao;

	public CouponRuleEntity save(CouponRuleEntity entity) {
		log.info("卡券规则====CouponRuleService.save :"+entity);
		return this.couponRuleDao.save(entity);
	}

	public List<CouponRuleEntity> listCouponRuleEntityByRID(String oid) {
		log.info("卡券规则====CouponRuleService.listCouponRuleEntityByRID :"+oid);
		return this.couponRuleDao.listCouponRuleEntityByRID(oid);
	}

	public List<CouponRuleEntity> listByCid(String oid, String type) {
		log.info("卡券规则====CouponRuleService.listByCid :oid={},type={}",oid,type);
		return this.couponRuleDao.listByCid(oid,type);
	}

	public void deleteCouponRule(String[] ruleIds) {
		log.info("卡券规则====CouponRuleService.deleteCouponRule :ruleIds={}",ruleIds.toString());
		this.couponRuleDao.deleteCouponRule(ruleIds);
	}
}
