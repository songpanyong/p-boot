package com.guohuai.tulip.platform.coupon.userCoupon;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.util.Collections3;
import com.guohuai.tulip.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserCouponService {

	@Autowired
	private UserCouponDao userCouponDao;
	/**
	 * 查询用户卡券
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PageResp<UserCouponRep> query(UserCouponDetailResp entity, Pageable pageable) {
		log.debug("===================查询卡券明细================{}", JSONObject.toJSONString(entity));
		PageResp<UserCouponRep> pageResp = new PageResp<UserCouponRep>();
		
		Specification<UserCouponEntity> spec = this.getWhere(entity);
		
		Page<UserCouponEntity> enchs = this.userCouponDao.findAll(spec, pageable);
		Iterator<UserCouponEntity> arg4 = enchs.iterator();
		while (arg4.hasNext()) {
			UserCouponEntity ench = (UserCouponEntity) arg4.next();
			UserCouponRep rep = new UserCouponRep();
			BeanUtils.copyProperties(ench, rep);
			//数据量大时影响性能
			List<String> phones = userCouponDao.queryUserPhoneByUserId(ench.getUserId());
			String phone = "";
			if(!Collections3.isEmpty(phones)){
				phone = phones.get(0);
			}
			rep.setPhone(phone);
			pageResp.getRows().add(rep);
		}

		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}
	
	private Specification<UserCouponEntity> getWhere(final UserCouponDetailResp reqEntity){
		
		return new Specification<UserCouponEntity>() {
			@Override
			public Predicate toPredicate(Root<UserCouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
                if(!StringUtil.isEmpty(reqEntity.getPhone())){
                	//手机号变成userId去查询
                	List<String> userIds = userCouponDao.queryUserIdByPhone(reqEntity.getPhone());
					String userId = "";
					if(!Collections3.isEmpty(userIds)){
						userId = userIds.get(0);
					}
                    predicate.add(cb.equal(root.get("userId").as(String.class), userId));
                }
                if(!StringUtil.isEmpty(reqEntity.getName())){
                    predicate.add(cb.equal(root.get("name").as(String.class), reqEntity.getName()));
                }
                if(!StringUtil.isEmpty(reqEntity.getEventType())){
                    predicate.add(cb.equal(root.get("eventType").as(String.class), reqEntity.getEventType()));
                }
                if(null !=reqEntity.getUseTime_start()){
                	Timestamp newt = new Timestamp(reqEntity.getUseTime_start().getTime());
                	predicate.add(cb.greaterThanOrEqualTo(root.get("useTime").as(Timestamp.class), newt));
                }
                if(null !=reqEntity.getUseTime_finish()){
                	Timestamp newf = new Timestamp(reqEntity.getUseTime_finish().getTime());
                	predicate.add(cb.lessThanOrEqualTo(root.get("useTime").as(Timestamp.class), newf));
                }
                if(!StringUtil.isEmpty(reqEntity.getStatus())){
                    predicate.add(cb.equal(root.get("status").as(String.class), reqEntity.getStatus()));
                }
                if(!StringUtil.isEmpty(reqEntity.getType())){
                    predicate.add(cb.equal(root.get("type").as(String.class), reqEntity.getType()));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
			}
			
		};
	}
	
	/**查询用户指定卡券类型
	 * 说明：
	 * @param userId
	 * @param type
	 * @return
	 * @author ddyin
	 * @time：2017年3月27日 下午2:06:28
	 */
	public List<UserCouponRep> counponListByUidAndType(String userId, String type) {
		List<UserCouponEntity> userCouponList = userCouponDao.counponListByUidAndType(userId,type);
		List<UserCouponRep> rep  =new ArrayList<UserCouponRep>();
		for (UserCouponEntity en : userCouponList) {
			UserCouponRep userCouponQueryRep = new UserCouponRep();
			BeanUtils.copyProperties(en, userCouponQueryRep);
			rep.add(userCouponQueryRep);
		}
		return rep;
	}
	
	
	/**
	 * 说明：红包过期
	 * @return
	 * @author ddyin
	 * @time：2017年3月27日 下午3:37:34
	 */
	public List<UserCouponRep> counponListExpire(String userId) {
		List<UserCouponEntity> userCouponList = userCouponDao.counponListExpire(userId);
		List<UserCouponRep> rep  =new ArrayList<UserCouponRep>();
		for (UserCouponEntity en : userCouponList) {
			UserCouponRep couponQueryRep = new UserCouponRep();
			BeanUtils.copyProperties(en, couponQueryRep);
			rep.add(couponQueryRep);
		}
		return rep;
	}
	
	/**
	 * 使用户卡券过期
	 */
	public void updateCouponForExpired(){
		this.userCouponDao.updateCouponForExpired();
	}

	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateCouponForInvalid(String[] oids){
		this.userCouponDao.updateCouponForInvalid(oids);
	}

	/**
	 * 卡券领用数量
	 * @return
	 */
	public Integer isseCount() {
		return this.userCouponDao.isseCount();
	}

	/**
	 * 卡券使用数量
	 * @return
	 */
	public Integer useCount() {
		return this.userCouponDao.useCount();
	}

	/**
	 * 保存用户卡券
	 * @param userCoupon
	 * @return
	 */
	public UserCouponEntity createUserCoupon(UserCouponEntity userCoupon) {
		return this.userCouponDao.save(userCoupon);
	}

	/**
	 * 查询用户卡券
	 * @param oid
	 * @return
	 */
	public UserCouponEntity findUserCouponByOid(String oid) {
		UserCouponEntity entity =this.userCouponDao.findOne(oid);
		return entity;
	}

	/**
	 * 卡券过期
	 * @param arr
	 */
	public void updateCouponForWriteOff(String[] couponIds) {
		int num = this.userCouponDao.updateCouponForWriteOff(couponIds);
		if(num < 1){
			throw new GHException("使卡券过期异常!");
		}
	}

	/**
	 * 充值卡券
	 * @param couponStatus
	 * @param couponId
	 * @return
	 */
	public int resetUserCoupon(String couponStatus, String oid, String userId) {
		return this.userCouponDao.resetUserCoupon(couponStatus, oid, userId);
	}

	/**
	 * 使用卡券
	 * @param couponStatus
	 * @param couponId
	 * @return
	 */
	public int useUserCoupon(String couponStatus, String oid, String userId) {
		return this.userCouponDao.useUserCoupon(couponStatus, oid, userId);
	}

	/**
	 * 获取用户卡券列表
	 * @param userId
	 * @return
	 */
	public List<UserCouponEntity> getCouponList(String userId) {
		return this.userCouponDao.getCouponList(userId);
	}

	/**
	 * 统计用户未使用的卡券
	 * @param userId
	 * @return
	 */
	public Integer countNotUsedNum(String userId) {
		return this.userCouponDao.countNotUsedNum(userId);
	}

	/**
	 * 统计用户已使用的卡券
	 * @param userId
	 * @return
	 */
	public Integer countUsedNum(String userId) {
		return this.userCouponDao.countUsedNum(userId);
	}

	/**
	 * 统计用户已过期的卡券
	 * @param userId
	 * @return
	 */
	public Integer countExpiredNum(String userId) {
		return this.userCouponDao.countExpiredNum(userId);
	}

	/**
	 * 分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<UserCouponEntity> findAll(Specification<UserCouponEntity> spec, Pageable pageable) {
		return  this.userCouponDao.findAll(spec, pageable);
	}
	
	/**
	 * 查询过期的卡券列表
	 * 
	 * @return
	 */
	public List<UserCouponRep> findCouponForExpiredList(){
		List<UserCouponEntity> userCouponList = userCouponDao.getCouponForExpiredList();
		List<UserCouponRep> rep  =new ArrayList<UserCouponRep>();
		for (UserCouponEntity en : userCouponList) {
			UserCouponRep couponQueryRep = new UserCouponRep();
			BeanUtils.copyProperties(en, couponQueryRep);
			rep.add(couponQueryRep);
		}
		return rep;
	}
	
	public List<String> findMyCouponList(String userId){
		return userCouponDao.queryCounponListByUid(userId);
	}
	
	/**
	 * 卡券明细核销
	 * 
	 * @param oid
	 * @param writeOffAmount 核销金额
	 * @param settlement 核销时间
	 * @return
	 */
	public int updateWriteOffAmount(String oid, BigDecimal writeOffAmount, Timestamp settlement){
		log.debug("卡券核销第一步：卡券明细的核销，请求参数[oid={}, amount={}, time={}]", oid, writeOffAmount, settlement);
		return userCouponDao.updateUseCouponWriteOffAmount(oid, UserCouponEntity.COUPON_STATUS_WRITEOFF, writeOffAmount, settlement);
	}
	
	/**
	 * 卡券号反查卡券批次号
	 * @param oid
	 * @return
	 */
	public String findCouponBatch(String oid){
		return userCouponDao.queryCouponBatchByCouponId(oid);
	}
	
	/**
	 * 统计所有卡券兑付信息
	 * @return [0]卡券类型，[1]已发放金额，[2]已兑付金额（非代金券），[3]代金券已兑付金额,[4]已过期金额
	 */
	public List<Object[]> findSumCashInfo(){
		return userCouponDao.querySumCashInfo();
	}
}
