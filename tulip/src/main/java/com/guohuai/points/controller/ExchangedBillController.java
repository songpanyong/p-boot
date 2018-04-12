package com.guohuai.points.controller;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.entity.ExchangedBillEntity;
import com.guohuai.points.request.ExchangedBillRequest;
import com.guohuai.points.response.ExchangedBillResponse;
import com.guohuai.points.service.ExchangedBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 积分记录
 */
@RestController
@RequestMapping(value = "/points/exchangedBill/")
@Slf4j
public class ExchangedBillController {

	@Autowired
	private ExchangedBillService exchangedBillService;

	@RequestMapping(value = "page")
	@ResponseBody
	public ResponseEntity<PageResp<ExchangedBillResponse>> page(ExchangedBillRequest req) {
		log.info("积分兑换记录查询：{}", JSONObject.toJSON(req));
		PageResp<ExchangedBillResponse> pageResp = exchangedBillService.page(req, buildSpecification(req));
		return new ResponseEntity<PageResp<ExchangedBillResponse>>(pageResp, HttpStatus.OK);
	}

	private Specification<ExchangedBillEntity> buildSpecification(final ExchangedBillRequest req) {
		return new Specification<ExchangedBillEntity>() {
			@Override
			public Predicate toPredicate(Root<ExchangedBillEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getUserOid())) {
					list.add(cb.like(root.get("userOid").as(String.class), "%" + req.getUserOid() + "%"));
				}
				if (!StringUtil.isEmpty(req.getGoodsOid())) {
					list.add(cb.like(root.get("goodsOid").as(String.class), "%" + req.getGoodsOid() + "%"));
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

	@RequestMapping(value = "findById")
	@ResponseBody
	public ResponseEntity<BaseResp> findById(String oid) {

		if (StringUtil.isEmpty(oid)) {
			BaseResp baseResp = new BaseResp(-1, "id为空！");
			return new ResponseEntity<BaseResp>(baseResp, HttpStatus.OK);
		}
		log.info("查询单条记录ID：{}", oid);
		ExchangedBillResponse billRes = exchangedBillService.findById(oid);

		return new ResponseEntity<BaseResp>(billRes, HttpStatus.OK);
	}
}

