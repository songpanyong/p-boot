package com.guohuai.tulip.platform.coupon.couponRange;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.mimosa.api.MimosaSdk;
import com.guohuai.mimosa.api.obj.MimosaSdkPages;
import com.guohuai.mimosa.api.obj.ProductLabelRep;
import com.guohuai.mimosa.api.obj.ProductLabelReq;
import com.guohuai.mimosa.api.obj.ProductSDKRep;
import com.guohuai.mimosa.api.obj.ProductSDKReq;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CouponRangeService {

	@Autowired
	private MimosaSdk mimoSdk;
	@Autowired
	private CouponRangeDao coupnRangeDao;
	/**
	 * 查询产品集合
	 * @param productType
	 * @return
	 */
	public List<CouponRangeRep> getProductList(ProductReq productType) {
		List<CouponRangeRep> list = new ArrayList<CouponRangeRep>();
		try {
			ProductSDKReq req=new ProductSDKReq();
			req.setType(productType.getType());
			MimosaSdkPages<ProductSDKRep> rep=mimoSdk.queryProductList(req);
			List<ProductSDKRep> productList=rep.getRows();
			if(!CollectionUtils.isEmpty(productList)){
				for (ProductSDKRep productSDKRep : productList) {
					CouponRangeRep couponRep=new CouponRangeRep();
					couponRep.setProductId(productSDKRep.getOid());
					couponRep.setProductName(productSDKRep.getName());
					list.add(couponRep);
				}
			}
		} catch (Exception e) {
			log.error("查询商品接口异常:{}",e.getMessage());
		}
		return list;
	}
	/**
	 * 查询产品集合
	 * @param productType
	 * @return
	 */
	public List<CouponRangeRep> getAllProductList(ProductReq reqProduct) {
		List<CouponRangeRep> list = new ArrayList<CouponRangeRep>();
		try {
			ProductSDKReq req=new ProductSDKReq();
			req.setType(reqProduct.getType());
			req.setOid(reqProduct.getOid());
			MimosaSdkPages<ProductSDKRep> rep=mimoSdk.queryProductList(req);
			List<ProductSDKRep> productList=rep.getRows();
			if(!CollectionUtils.isEmpty(productList)){
				for (ProductSDKRep productSDKRep : productList) {
					CouponRangeRep couponRep=new CouponRangeRep();
					couponRep.setProductId(productSDKRep.getOid());
					couponRep.setProductName(productSDKRep.getName());
					couponRep.setDurationPeriodDays(productSDKRep.getDurationPeriodDays());
					couponRep.setType(productSDKRep.getType());
					list.add(couponRep);
				}
			}
		} catch (Exception e) {
			log.error("查询商品接口异常:{}",e.getMessage());
		}
		return list;
	}
	/**
	 * 查询产品标签集合
	 * @param productType
	 * @return
	 */
	public List<ProductLabelRep> getProductLabelNames(ProductLabelReq productType) {
		List<ProductLabelRep> list = new ArrayList<ProductLabelRep>();
		try {
			MimosaSdkPages<ProductLabelRep> rep=mimoSdk.getProductLabelNames(productType);
			List<ProductLabelRep> productList=rep.getRows();
			if(!CollectionUtils.isEmpty(productList)){
				list.addAll(productList);
			};
		} catch (Exception e) {
			log.error("查询商品接口异常:{}",e.getMessage());
		}
		return list;
	}
	/**
	 * 根据卡券Oid查询卡券使用范围
	 * @param couponId
	 * @return
	 */
	public List<CouponRangeEntity> findByCouponId(String couponId) {
		List<CouponRangeEntity> couponRangeList=this.coupnRangeDao.findByCouponId(couponId);
		return couponRangeList;
	}
	/**
	 * 删除卡券使用范围
	 * @param oid
	 */
	public void deleteRange(String oid) {
		 this.coupnRangeDao.deleteRange(oid);
	}
	/**
	 * 创建卡券使用范围
	 * @param couponRangeEntity
	 * @return
	 */
	public CouponRangeEntity createCouponRange(CouponRangeEntity couponRangeEntity) {
		return this.coupnRangeDao.save(couponRangeEntity);
	}
}
