package com.guohuai.tulip.platform.coupon.redpacket;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponRedisUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RedpacketDetailService {

	@Autowired
	private RedpacketDetailDao redpacketDetailDao;
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	public RedpacketDetailEntity findByOid(String oid){
		return redpacketDetailDao.findOne(oid);
	}
	
	/**
	 * 查询当前红包批次的所有随机红包
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<RedpacketDetailRep> query(Specification<RedpacketDetailEntity> spec, Pageable pageable) {
		Page<RedpacketDetailEntity> enchs = this.redpacketDetailDao.findAll(spec, pageable);
		PageResp<RedpacketDetailRep> pageResp = new PageResp<RedpacketDetailRep>();

		for (RedpacketDetailEntity entity : enchs) {
			RedpacketDetailRep resp = new RedpacketDetailRep();
			BeanUtils.copyProperties(entity, resp);
			pageResp.getRows().add(resp);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}

	public List<RedpacketDetailEntity> savebitch(List<RedpacketDetailEntity> list){
		return redpacketDetailDao.save(list);
	}
	
	
	public RedpacketDetailEntity saveRedpacket(RedpacketDetailEntity entity){
		return redpacketDetailDao.save(entity);
	}
	
	/**
	 * 将生成的随机红包入库
	 * @param splitRedList
	 * @param couponBatchId
	 * @return
	 */
	public void saveRandomRedpacket(List<BigDecimal> splitRedList, String couponBatchId){
		List<String> resList = new ArrayList<String>();
		List<RedpacketDetailEntity> saveList = new ArrayList<RedpacketDetailEntity>();
		if(!CollectionUtils.isEmpty(splitRedList)){
			try {
				for (BigDecimal amount : splitRedList) {
					RedpacketDetailEntity red = new RedpacketDetailEntity();
					red.setCouponBatchId(couponBatchId);
					red.setRandomAmount(amount);
					red.setStatus(RedpacketDetailEntity.RED_STATUS_NOTUSED);
					red.setCreateTime(new Timestamp(System.currentTimeMillis()));
					red.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					saveList.add(red);
				}
				saveList = this.savebitch(saveList);
				for (RedpacketDetailEntity redpacketDetailEntity : saveList) {
					resList.add(redpacketDetailEntity.getOid());
				}
			} catch (Exception e) {
				throw new GHException("提前生成的随机红包入库异常");
			}
			redis.opsForList().leftPushAll(UserCouponRedisUtil.COUPON_REDPACKET_REDIS_KEY + couponBatchId, resList);
			log.info("生成的随机红包入库之后，加入redis完成");
		}
	}
	
}
