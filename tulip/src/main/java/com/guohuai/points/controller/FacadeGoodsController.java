package com.guohuai.points.controller;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.component.AccountTypeEnum;
import com.guohuai.points.component.Constant;
import com.guohuai.points.entity.*;
import com.guohuai.points.request.*;
import com.guohuai.points.response.*;
import com.guohuai.points.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mr_gu
 */

@Slf4j
@RestController
@RequestMapping(value = "/points/facadeGoods", produces = "application/json")
public class FacadeGoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private ExchangedBillService exchangedBillService;

	@Autowired
	private DeliveryManageService deliveryManageService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private AccountTransService accountTransService;

	/**
	 * 查询个人积分数
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findAcountByUserOid", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<CreateAccountInfoResponse> findAcountByUserOid(CreateAccountRequest req) {
		CreateAccountInfoResponse resp = new CreateAccountInfoResponse();
		if (StringUtil.isEmpty(req.getUserOid()) || StringUtil.isEmpty(req.getAccountType())) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("用户Id为空，或账户类型为空");
			log.info("查询个人积分，用户Id为空，或账户类型为空！");
			return new ResponseEntity<CreateAccountInfoResponse>(resp, HttpStatus.OK);
		}
		if (!AccountTypeEnum.ACCOUNT_TYPE01.getCode().equals(req.getAccountType())) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("查询个人积分，账户类型错误，必须是查主账户");
			log.info("查询个人积分，账户类型错误，必须是查主账户");
			return new ResponseEntity<CreateAccountInfoResponse>(resp, HttpStatus.OK);
		}
		AccountInfoEntity accountInfo = accountInfoService.getAccountByTypeAndUser(req.getAccountType(), req.getUserOid());
		if (null == accountInfo) {
			resp.setBalance(BigDecimal.ZERO);
			return new ResponseEntity<CreateAccountInfoResponse>(resp, HttpStatus.OK);
		}
		resp.setAccountNo(accountInfo.getAccountNo());
		resp.setUserOid(accountInfo.getUserOid());
		resp.setBalance(accountInfo.getBalance());
		return new ResponseEntity<CreateAccountInfoResponse>(resp, HttpStatus.OK);
	}

	/**
	 * 积分商品列表
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pointGoodsList", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<PageResp<GoodsResponse>> pointGoodsList(GoodsRequest req) {
		PageResp<GoodsResponse> listPage = goodsService.findPointGoodsList(req);
		return new ResponseEntity<PageResp<GoodsResponse>>(listPage, HttpStatus.OK);
	}

	/**
	 * 个人使用积分兑换商品时检查：积分数、库存数
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checkGoodsPoint", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GoodsResponse> checkGoodsPoint(GoodsRequest req) {
		GoodsResponse resp = new GoodsResponse();
		if (StringUtil.isEmpty(req.getUserOid())) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("当前用户oid为空");
			return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
		}
		if (StringUtil.isEmpty(req.getOid())) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("要兑换的商品oid为空");
			return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
		}
		if (req.getExchangedCount().compareTo(BigDecimal.ZERO) < 1) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("兑换数量必须大于0");
			return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
		}
		resp = goodsService.checkGoodsPoint(req);
		return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
	}

	/**
	 * 查询积分商品详情
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pointGoodsDetail", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GoodsResponse> pointGoodsDetail(GoodsRequest req) {
		GoodsResponse resp = null;
		if (StringUtil.isEmpty(req.getOid())) {
			resp = new GoodsResponse();
			resp.setErrorCode(-1);
			resp.setErrorMessage("商品Id为空");
			log.info("查询积分商品详情，商品Id为空！");
			return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
		}

		resp = goodsService.getPointGoodsDetail(req);
		return new ResponseEntity<GoodsResponse>(resp, HttpStatus.OK);
	}

	/**
	 * 当前商品的购买记录（使用积分购买商品）
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/goodsExchangedBill", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<PageResp<ExchangedBillResponse>> goodsExchangedBill(ExchangedBillRequest req) {
		if (StringUtil.isEmpty(req.getGoodsOid())) {
			PageResp<ExchangedBillResponse> resp = new PageResp<>();
			resp.setErrorCode(-1);
			resp.setErrorMessage("goodsOid为空！");
			return new ResponseEntity<PageResp<ExchangedBillResponse>>(resp, HttpStatus.OK);
		}
		PageResp<ExchangedBillResponse> listPage = exchangedBillService.page(req, buildSpecification(req));
		return new ResponseEntity<PageResp<ExchangedBillResponse>>(listPage, HttpStatus.OK);
	}


	private Specification<ExchangedBillEntity> buildSpecification(final ExchangedBillRequest req) {
		return new Specification<ExchangedBillEntity>() {
			@Override
			public Predicate toPredicate(Root<ExchangedBillEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getUserOid())) {
					list.add(cb.equal(root.get("userOid").as(String.class), req.getUserOid()));
				}
				if (!StringUtil.isEmpty(req.getGoodsOid())) {
					list.add(cb.equal(root.get("goodsOid").as(String.class), req.getGoodsOid()));
				}
				if (!StringUtil.isEmpty(req.getGoodsName())) {
					list.add(cb.like(root.get("goodsName").as(String.class), "%" + req.getGoodsName() + "%"));
				}
				if (null != req.getStartTime()) {
					list.add(cb.greaterThanOrEqualTo(root.get("exchangedTime").as(Date.class), req.getStartTime()));
				}
				if (null != req.getEndTime()) {
					list.add(cb.lessThan(root.get("exchangedTime").as(Date.class), req.getEndTime()));
				}
				if (null != req.getState()) {
					list.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				}
				if (!StringUtil.isEmpty(req.getType())) {
					list.add(cb.equal(root.get("type").as(String.class), req.getType()));
				}

				query.where(cb.and(list.toArray(new Predicate[list.size()])));
				query.orderBy(cb.desc(root.get("exchangedTime").as(Date.class)));
				return query.getRestriction();
			}
		};
	}

	/**
	 * 积分商品下单
	 *
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePointsOrder", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> savePointsOrder(GoodsOrderRequest req) {
		BaseResp resp = new BaseResp();
		if (StringUtil.isEmpty(req.getUserOid())) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("下单用户为空");
			return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
		}

		if (null != req.getExchangedCount()) {
			int res = req.getExchangedCount().compareTo(BigDecimal.ZERO);
			if (res < 1) {
				resp.setErrorCode(-1);
				resp.setErrorMessage("兑换商品数必须大于0");
				return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
			}
		}
		GoodsRequest request = new GoodsRequest();
		request.setUserOid(req.getUserOid());
		request.setOid(req.getGoodsOid());
		request.setExchangedCount(req.getExchangedCount());
		request.setNeedPoints(req.getNeedPoints());

		//判断余额
		GoodsResponse goodsresp = goodsService.checkGoodsPoint(request);
		if (goodsresp.getErrorCode() != 0) {
			resp.setErrorCode(goodsresp.getErrorCode());
			resp.setErrorMessage(goodsresp.getErrorMessage());
			return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
		}

		try {
			resp = deliveryManageService.savePointsOrder(req);
		} catch (Exception e) {
			log.info("积分商品下单异常： "+e.getMessage());
			resp.setErrorCode(120002);
			resp.setErrorMessage("该商品库存不足，操作失败");
		}
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 个人积分交易记录查询
	 */
	@RequestMapping(value = "/findUserPointTransRecord", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<PageResp<UserPointsRecords>> findUserPointTransRecord(UserPointsRequest req) {
		log.info("个人积分记录查询：{}", req);
		if (StringUtil.isEmpty(req.getUserOid())) {
			PageResp resp = new PageResp<UserPointsRecords>();
			resp.setErrorCode(-1);
			resp.setErrorMessage("用户Id为空");
			log.info("查询个人积分交易记录，用户Id为空！");
			return new ResponseEntity<PageResp<UserPointsRecords>>(resp, HttpStatus.OK);
		}
		AccountTransRequest accountTransRequest = new AccountTransRequest();
		BeanUtils.copyProperties(req, accountTransRequest);
		accountTransRequest.setAccountType("01");

		AccountTransRes userAccountTrans = accountTransService.getUserAccountTrans(accountTransRequest);
		PageResp<UserPointsRecords> pageResp = new PageResp<>();

		for (AccountTransEntity accountTransEntity : userAccountTrans.getRows()) {
			UserPointsRecords userPointsRes = new UserPointsRecords();
			userPointsRes.setDirection(accountTransEntity.getDirection());
			userPointsRes.setPoint(accountTransEntity.getPoint());
			userPointsRes.setPointTime(accountTransEntity.getCreateTime());
			userPointsRes.setPointType(accountTransEntity.getOrderType());
			pageResp.getRows().add(userPointsRes);
		}
		pageResp.setTotal(userAccountTrans.getTotal());
		log.info("个人积分记录查询：返回数据条数：{} ,数据总条数：{}", pageResp.getRows().size(), pageResp.getTotal());

		return new ResponseEntity<PageResp<UserPointsRecords>>(pageResp, HttpStatus.OK);
	}

	/**
	 * 购买积分列表（用现金购买的积分产品）
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/settingList", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<PageResp<PointSettingEntity>> SettingList(SettingRequest req) {
		PageResp<PointSettingEntity> page = settingService.page(req);
		return new ResponseEntity<PageResp<PointSettingEntity>>(page, HttpStatus.OK);
	}


	/**
	 * 获取积分商品列表
	 *
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseEntity<PageResp<PointGoodsEntity>> page(GoodsRequest form) {
		PageResp<PointGoodsEntity> rows = goodsService.page(form);
		return new ResponseEntity<PageResp<PointGoodsEntity>>(rows, HttpStatus.OK);
	}

}
