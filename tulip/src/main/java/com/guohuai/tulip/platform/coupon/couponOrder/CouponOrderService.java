package com.guohuai.tulip.platform.coupon.couponOrder;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.util.DateUtil;

@Service
@Transactional
public class CouponOrderService {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private CouponOrderDao couponOrderDao;
	/**
	 * 查询用户投资订单
	 * @param req
	 * @return
	 */
	public PageResp<CouponOrderRep> query(CouponOrderReq req) {
		PageResp<CouponOrderRep> pageResp = new PageResp<CouponOrderRep>();
		long total= Long.parseLong(this.countNum(req)+"");
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT a.oid,a.type,a.status,a.leadTime,a.useTime,a.settlement,a.userId  FROM T_TULIP_USER_COUPON a LEFT JOIN T_TULIP_COUPON_ORDER b ");
		sb.append("ON a.oid=b.couponId ");
		sb.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(req.getCouponId())){
			sb.append("AND a.oid like '%").append(req.getCouponId()).append("%' ");
		}
		if(StringUtils.isNotBlank(req.getUserId())){
			sb.append("AND a.userId like '%").append(req.getUserId()).append("%' ");
		}
		if(StringUtils.isNotBlank(req.getStatus())){
			sb.append("AND a.status='").append(req.getStatus()).append("' ");
		}
		if(StringUtils.isNotBlank(req.getType())){
			sb.append("AND a.type='").append(req.getType()).append("' ");
		}
		if(null != req.getStart()){
			sb.append("AND DATE_FORMAT(a.useTime,'%Y-%m-%d')>='").append(req.getStart()).append("' ");
		}
		if(null != req.getFinish()){
			sb.append("AND DATE_FORMAT(a.useTime,'%Y-%m-%d')<='").append(req.getFinish()).append("' ");
		}
		if(StringUtils.isNotBlank(req.getOrderCode())){
			sb.append("AND b.orderCode like '%").append(req.getOrderCode()).append("%' ");
		}
		sb.append("ORDER BY a.leadTime DESC LIMIT ").append((req.getPage() - 1) * req.getRows()).append(",").append(req.getRows());
		@SuppressWarnings("unchecked")
		List<Object[]> list = em.createNativeQuery(sb.toString()).getResultList();
		CouponOrderRep orderRep=null;
		for (Object[] userCoupon : list) {
			orderRep = new CouponOrderRep();
			orderRep.setOid(userCoupon[0]+"");
			orderRep.setStatus(userCoupon[2]+"");
			orderRep.setType(userCoupon[1]+"");
			try {
				orderRep.setLeadTime(DateUtil.fetchTimestamp(userCoupon[3]+""));
				orderRep.setUseTime((Timestamp)userCoupon[4]);
				orderRep.setSettlement((Timestamp)userCoupon[5]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			orderRep.setUserId(userCoupon[6]+"");
			pageResp.getRows().add(orderRep);
		}
		pageResp.setTotal(total);
		return pageResp;
	}
	private BigInteger countNum(CouponOrderReq req){
		BigInteger num=BigInteger.ZERO;
		StringBuffer sb= new StringBuffer();
		sb.append("SELECT COUNT(a.oid) FROM  T_TULIP_USER_COUPON a LEFT JOIN T_TULIP_COUPON_ORDER b ");
		sb.append("ON a.oid=b.couponId ");
		sb.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(req.getCouponId())){
			sb.append("AND a.oid like '%").append(req.getCouponId()).append("%' ");
		}
		if(StringUtils.isNotBlank(req.getUserId())){
			sb.append("AND a.userId like '%").append(req.getUserId()).append("%' ");
		}
		if(StringUtils.isNotBlank(req.getStatus())){
			sb.append("AND a.status='").append(req.getStatus()).append("' ");
		}
		if(StringUtils.isNotBlank(req.getType())){
			sb.append("AND a.type='").append(req.getType()).append("' ");
		}
		if(null != req.getStart()){
			sb.append("AND DATE_FORMAT(a.useTime,'%Y-%m-%d')>='").append(req.getStart()).append("' ");
		}
		if(null != req.getFinish()){
			sb.append("AND DATE_FORMAT(a.useTime,'%Y-%m-%d')<='").append(req.getFinish()).append("' ");
		}
		if(StringUtils.isNotBlank(req.getOrderCode())){
			sb.append("AND b.orderCode like '%").append(req.getOrderCode()).append("%' ");
		}
		@SuppressWarnings("unchecked")
		List<BigInteger> list = em.createNativeQuery(sb.toString()).getResultList();
		if(!CollectionUtils.isEmpty(list)){
			num=list.get(0);
		}
		return num;
	}
	/**
	 * 创建卡券订单
	 * @param entity
	 * @return
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public CouponOrderEntity createCouponOrder(CouponOrderEntity entity){
		return couponOrderDao.save(entity);
	}
	/**
	 * 根据用户查询是未生成佣金订单的订单
	 * @param userId
	 * @return
	 */
	public List<CouponOrderEntity> findIsMakedCommisOrderByUserId(String userId) {
		List<CouponOrderEntity> couponOrderList=this.couponOrderDao.findIsMakedCommisOrderByUserId(userId);
		return couponOrderList;
	}
	/**
	 * 根据卡券ID查询订单
	 * @param couponId
	 * @return
	 */
	public CouponOrderEntity findOrderByCouponId(String couponId) {
		CouponOrderEntity entity =this.couponOrderDao.findOrderByCouponId(couponId);
		return entity;
	}
	/**
	 * 根据用户ID统计订单数
	 * @param userId
	 * @return
	 */
	public Long countByUserId(String userId) {
		return this.couponOrderDao.countByUserId(userId);
	}
	/**
	 * 根据订单Code和订单类型查询订单
	 * @param orderCode
	 * @param orderType
	 * @return
	 */
	public CouponOrderEntity findOrderByOrderCode(String orderCode, String orderType) {
		CouponOrderEntity entity =this.couponOrderDao.findOrderByOrderCode(orderCode, orderType);
		return entity;
	}
	/**
	 * 根据卡券ID和用户ID查询订单
	 * @param couponId
	 * @param userId
	 * @return
	 */
	public CouponOrderEntity findOrderByCouponIdAndUserId(String couponId, String userId) {
		CouponOrderEntity entity =this.couponOrderDao.findOrderByCouponIdAndUserId(couponId, userId);
		return entity;
	}
	/**
	 * 根据产品ID查询订单
	 * @param productId
	 * @return
	 */
	public List<CouponOrderEntity> findUsedCouponByProductId(String productId) {
		List<CouponOrderEntity> couponOrderList=this.couponOrderDao.findUsedCouponByProductId(productId);
		return couponOrderList;
	}
}
