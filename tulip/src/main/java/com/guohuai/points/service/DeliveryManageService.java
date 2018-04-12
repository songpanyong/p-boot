package com.guohuai.points.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.component.Constant;
import com.guohuai.points.component.TradeType;
import com.guohuai.points.dao.DeliveryManageDao;
import com.guohuai.points.entity.DeliveryEntity;
import com.guohuai.points.entity.PointGoodsEntity;
import com.guohuai.points.request.AccountTradeRequest;
import com.guohuai.points.request.DeliveryRequest;
import com.guohuai.points.request.ExchangedBillRequest;
import com.guohuai.points.request.GoodsOrderRequest;
import com.guohuai.points.response.AccountTradeResponse;
import com.guohuai.points.response.AddressResponse;
import com.guohuai.points.response.DeliveryResponse;
import com.guohuai.points.response.GoodsResponse;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.facade.FacadeNewService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryManageService {

	@Autowired
	private DeliveryManageDao deliveryManageDao;

	@Autowired
	private ExchangedBillService exchangedBillService;

	@Autowired
	private AccountTradeService accountTradeService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private FacadeNewService facadeNewService;
	
	@Autowired
	private UserInvestService userInvestService;
	
	@Autowired
	private CouponService couponService;

	/**
	 * 分页查询
	 */
	public PageResp<DeliveryResponse> page(DeliveryRequest req) {

		Page<DeliveryEntity> pages = deliveryManageDao.findAll(buildSpecification(req), new PageRequest(req.getPage() - 1, req.getRows()));
		PageResp<DeliveryResponse> resPage = new PageResp<>();

		for (DeliveryEntity page : pages) {
			DeliveryResponse res = new DeliveryResponse();
			BeanUtils.copyProperties(page, res);
			resPage.getRows().add(res);
		}
		resPage.setTotal(pages.getTotalElements());
		log.info("发货管理查询：返回数据条数：{} ,数据总条数：{}", resPage.getRows().size(), pages.getTotalElements());
		return resPage;
	}

	private Specification<DeliveryEntity> buildSpecification(final DeliveryRequest req) {
		return new Specification<DeliveryEntity>() {
			@Override
			public Predicate toPredicate(Root<DeliveryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != req.getStartTime()) {
					list.add(cb.greaterThanOrEqualTo(root.get("orderedTime").as(Date.class), req.getStartTime()));
				}
				if (null != req.getEndTime()) {
					list.add(cb.lessThan(root.get("orderedTime").as(Date.class), req.getEndTime()));
				}
				if (null != req.getState()) {
					list.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				}
				if (!StringUtil.isEmpty(req.getUserOid())) {
					list.add(cb.like(root.get("userOid").as(String.class), req.getUserOid()));
				}
				if (!StringUtil.isEmpty(req.getUserName())) {
					list.add(cb.like(root.get("userName").as(String.class), req.getUserName()));
				}
				if (!StringUtil.isEmpty(req.getUserPhone())) {
					list.add(cb.equal(root.get("userPhone").as(String.class), req.getUserPhone()));
				}
				if (!StringUtil.isEmpty(req.getOrderPhone())) {
					list.add(cb.equal(root.get("orderPhone").as(String.class), req.getOrderPhone()));
				}
				if (!StringUtil.isEmpty(req.getOrderNumber())) {
					list.add(cb.like(root.get("orderNumber").as(String.class), req.getOrderNumber()));
				}
				query.where(cb.and(list.toArray(new Predicate[list.size()])));
				query.orderBy(cb.asc(root.get("state").as(Integer.class)), cb.desc(root.get("orderedTime").as(Date.class)));

				return query.getRestriction();
			}
		};
	}

	private Specification<DeliveryEntity> buildQueryOrderSpecification(final DeliveryRequest req) {
		return new Specification<DeliveryEntity>() {
			@Override
			public Predicate toPredicate(Root<DeliveryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != req.getOrderedTime()) {
					list.add(cb.greaterThan(root.get("orderedTime").as(Date.class), req.getOrderedTime()));
					list.add(cb.lessThan(root.get("orderedTime").as(Date.class), DateUtil.addDays(req.getOrderedTime(), 1)));
				}
				if (null != req.getState()) {
					list.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				}
				if (!StringUtil.isEmpty(req.getUserOid())) {
					list.add(cb.equal(root.get("userOid").as(String.class), req.getUserOid()));
				}
				if (!StringUtil.isEmpty(req.getGoodsName())) {
					list.add(cb.like(root.get("goodsName").as(String.class), "%" + req.getGoodsName() + "%"));
				}
				list.add(cb.equal(root.get("isdel").as(String.class), "no"));
				query.where(cb.and(list.toArray(new Predicate[list.size()])));
				query.orderBy(cb.asc(root.get("state").as(Integer.class)), cb.desc(root.get("orderedTime").as(Date.class)));

				return query.getRestriction();
			}
		};
	}

	public DeliveryResponse findById(String oid) {

		DeliveryEntity entity = deliveryManageDao.findOne(oid);
		DeliveryResponse billRes = new DeliveryResponse();
		BeanUtils.copyProperties(entity, billRes);

		return billRes;
	}

	@Transactional
	public DeliveryResponse save(DeliveryRequest req) {

		DeliveryEntity entity = deliveryManageDao.forUpdateByOid(req.getOid());
		entity.setLogisticsCompany(req.getLogisticsCompany());
		entity.setLogisticsNumber(req.getLogisticsNumber());
		entity.setSendOperater(req.getSendOperater());
		entity.setSendTime(new Date());
		entity.setState(1);
		log.info("新增或修改发货记录持久化数据：id={} {}", entity.getOid(), entity);

		deliveryManageDao.save(entity);
		return new DeliveryResponse();
	}

	@Transactional
	public BaseResp cancel(DeliveryRequest req) {

		BaseResp baseResp = new BaseResp();

		DeliveryEntity entity = deliveryManageDao.forUpdateByOid(req.getOid());
		entity.setCancelOperater(req.getCancelOperater());
		entity.setCancelReason(req.getCancelReason());
		entity.setCancelTime(new Date());
		entity.setState(2);
		log.info("取消发货记录持久化数据：id={} {}", entity.getOid(), entity);

		deliveryManageDao.save(entity);

		//  调用积分接口退积分
		AccountTradeRequest accountTradeRequest = new AccountTradeRequest();
		accountTradeRequest.setOldOrderNo(entity.getOrderNumber());  // 原订单号
		accountTradeRequest.setUserOid(entity.getUserOid()); //用户id
		accountTradeRequest.setRequestNo(StringUtil.uuid());
		accountTradeRequest.setSystemSource("points");
		accountTradeRequest.setOrderType(TradeType.KILLORDER.getValue()); //撤单
		log.info("调用积分接口req：{}", JSONObject.toJSON(accountTradeRequest));

		AccountTradeResponse trade = accountTradeService.trade(accountTradeRequest);

		log.info("调用积分接口resp：returnCode：{}  ErrorMessage：{}", trade.getReturnCode(), trade.getErrorMessage());

		if (!Constant.SUCCESSED.equalsIgnoreCase(trade.getReturnCode())) {
			log.info("原订单号：{} 调用积分接口退积分失败！", entity.getOrderNumber());
			throw new RuntimeException("调用积分接口退积分失败：" + trade.getErrorMessage());
		}
		log.info("原订单号：{} 调用积分接口退积分成功！", entity.getOrderNumber());
		return baseResp;
	}

	/**
	 * 积分商城前台接口：保存商品兑换订单
	 * @throws Exception
	 */
	@Transactional
	public BaseResp savePointsOrder(GoodsOrderRequest req) throws Exception {
		//无发货方，只是生成订单，积分商城后台负责发货
		log.info("新增积分商品兑换订单记录，用于发货, DeliveryRequest:{}", JSON.toJSONString(req));
		AddressResponse addressResp = addressService.findById(req.getAddressOid());
		GoodsResponse goodsResp = goodsService.findGoodsForupdateByOid(req.getGoodsOid());
		GoodsResponse changeResp = goodsService.changeStockGoods(req.getGoodsOid(), "-", req.getExchangedCount());
		log.info("新增积分商品兑换订单记录，1.修改商品库存，返回结果:{}", JSON.toJSONString(changeResp));
		if(changeResp.getErrorCode() != 0){
			throw new RuntimeException("新增积分商品兑换订单记录, 修改商品库存异常");
		}
		DeliveryEntity entity = new DeliveryEntity();
		entity.setOrderNumber(StringUtil.uuid());
		entity.setUserOid(req.getUserOid());
		entity.setUserName(req.getUserName());
		entity.setOrderedTime(new Date());
		entity.setOrderAddress(addressResp.getTakeAddress());
		entity.setOrderPhone(addressResp.getPhone());
		entity.setOrderName(addressResp.getName());
		entity.setOrderZipCode(addressResp.getZipCode());
		entity.setGoodsName(goodsResp.getName());
		entity.setGoodsCount(req.getExchangedCount());
		entity.setPoint(req.getNeedPoints());
		entity.setRemark(req.getRemark()); //订单备注
		if(PointGoodsEntity.GOODSTYPE_VIRTUAL.equals(goodsResp.getVirtualCouponType())){
			//虚拟券，直接发货
			entity.setState(1);
		}else{
			entity.setState(0);
		}
		DeliveryEntity resentity = deliveryManageDao.save(entity);
		log.info("新增积分商品兑换订单记录，2.保存发货记录, 返回结果:{}", JSON.toJSONString(resentity));
		//添加积分兑换记录
		ExchangedBillRequest reqq = new ExchangedBillRequest();
		reqq.setUserOid(req.getUserOid());
		reqq.setGoodsOid(goodsResp.getOid());
		reqq.setGoodsName(goodsResp.getName());
		reqq.setType(goodsResp.getType());
		reqq.setExchangedCount(req.getExchangedCount());
		reqq.setExpendPoints(req.getNeedPoints());
		reqq.setExchangedTime(new Timestamp(System.currentTimeMillis()));
		BaseResp resp = exchangedBillService.saveExchangedBill(reqq);
		log.info("新增积分商品兑换订单记录，3.保存商品兑换记录, 返回结果:{}", JSON.toJSONString(resp));		
		//积分兑换，扣减积分
		AccountTradeResponse tradeResponse = this.consumePoints(req, goodsResp);
		log.info("积分兑换商品，扣减积分余额，最终返回结果：{}", JSONObject.toJSONString(tradeResponse));
		if(Constant.FAIL.equals(tradeResponse.getReturnCode())){
			throw new RuntimeException("积分兑换商品，扣减积分余额异常！");
		}
		if(!StringUtil.isEmpty(goodsResp.getIssueVirtualCouponId())){
			//下发虚拟券给用户
			UserInvestEntity userInvestEntity = userInvestService.findUserInvestByUserId(req.getUserOid());
			CouponEntity couponEntity = couponService.findCouponByOid(goodsResp.getIssueVirtualCouponId());
			int exCount = req.getExchangedCount().intValue();
			log.info("****************为用户Id {} 一共需要下发  {} 张 卡券 ", req.getUserOid(), exCount);
			for (int i = 0; i < exCount; i++) {
				facadeNewService.generateCoupon(userInvestEntity, Constant.POINTS_EVENTTYPE, couponEntity);
				log.info("****************为用户Id {} 下发 第  {} 张 卡券 ", req.getUserOid(), i+1);
			}
			log.info("****************为用户Id {} 下发的卡券 {} 结束 ", req.getUserOid(), JSON.toJSONString(couponEntity));
		}
		
		return new BaseResp(0, "兑换积分商品，下单成功 ");
	}
	
	/**
	 * 消费积分：兑换商品（实物、虚拟卡券）
	 * @param req
	 * @param goodsResp
	 * @return
	 */
	private AccountTradeResponse consumePoints(GoodsOrderRequest req, GoodsResponse goodsResp){
		log.info("积分兑换商品操作，扣减积分余额，GoodsOrderRequest参数：{}, GoodsResponse参数：{} ", JSONObject.toJSONString(req), JSONObject.toJSONString(goodsResp));
		AccountTradeRequest tradeRequest = new AccountTradeRequest();
		tradeRequest.setSystemSource("tulip");
		tradeRequest.setRequestNo(StringUtil.uuid());
		tradeRequest.setOldOrderNo(StringUtil.uuid());
		tradeRequest.setUserOid(req.getUserOid());
		tradeRequest.setRelationProductNo(goodsResp.getOid());
		tradeRequest.setRelationProductName(goodsResp.getName());
		tradeRequest.setBalance(req.getNeedPoints());
		tradeRequest.setOrderType(TradeType.CONSUME.getValue()); //消费（兑换商品）
		tradeRequest.setOrderDesc(req.getRemark());
		AccountTradeResponse tradeResponse = accountTradeService.trade(tradeRequest);	
		return tradeResponse;
	}

	@Transactional
	public BaseResp confirmReceipt(DeliveryRequest req) {
		BaseResp baseResp = new BaseResp();

		DeliveryEntity entity = deliveryManageDao.forUpdateByOid(req.getOid());
		entity.setState(3);
		entity = deliveryManageDao.save(entity);
		log.info("确认收货持久化数据：id={} {}", entity.getOid(), entity);
		return baseResp;
	}

	@Transactional
	public BaseResp deleteOrder(DeliveryRequest req) {
		int num = deliveryManageDao.deleteOrder(req.getOid());
		log.info("删除订单：oid={} num={}", req.getOid(), num);
		return new BaseResp();
	}

	public PageResp<DeliveryResponse> query(DeliveryRequest req) {
		Page<DeliveryEntity> pages = deliveryManageDao.findAll(buildQueryOrderSpecification(req), new PageRequest(req.getPage() - 1, req.getRows()));
		PageResp<DeliveryResponse> resPage = new PageResp<>();

		for (DeliveryEntity page : pages) {
			DeliveryResponse res = new DeliveryResponse();
			BeanUtils.copyProperties(page, res);
			resPage.getRows().add(res);
		}
		resPage.setTotal(pages.getTotalElements());
		log.info("我的订单查询：返回数据条数：{} ,数据总条数：{}", resPage.getRows().size(), pages.getTotalElements());
		return resPage;
	}
}
