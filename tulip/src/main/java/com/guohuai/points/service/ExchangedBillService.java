package com.guohuai.points.service;

import com.alibaba.fastjson.JSON;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.dao.ExchangedBillDao;
import com.guohuai.points.entity.ExchangedBillEntity;
import com.guohuai.points.request.ExchangedBillRequest;
import com.guohuai.points.response.ExchangedBillResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExchangedBillService {
	@Autowired
	private ExchangedBillDao exchangedBillDao;

	/**
	 * 分页查询
	 *
	 * @param req
	 * @return
	 */
	public PageResp<ExchangedBillResponse> page(ExchangedBillRequest req,Specification<ExchangedBillEntity> specification) {

		Page<ExchangedBillEntity> pages = exchangedBillDao.findAll(specification, new PageRequest(req.getPage() - 1, req.getRows()));
		PageResp<ExchangedBillResponse> resPage = new PageResp<>();

		for (ExchangedBillEntity page : pages) {
			ExchangedBillResponse res = new ExchangedBillResponse();
			BeanUtils.copyProperties(page, res);
			res.setUserPhone(page.getUser().getPhone());
			resPage.getRows().add(res);
		}
		resPage.setTotal(pages.getTotalElements());
		log.info("积分兑换记录查询：返回数据条数：{} ,数据总条数：{}", resPage.getRows().size(), pages.getTotalElements());
		return resPage;
	}



	public ExchangedBillResponse findById(String oid) {

		ExchangedBillEntity entity = exchangedBillDao.findOne(oid);
		ExchangedBillResponse billRes = new ExchangedBillResponse();
		BeanUtils.copyProperties(entity, billRes);

		return billRes;
	}

	/**
	 * 积分商城前台接口：新增积分商品兑换记录
	 *
	 * @param req
	 * @return
	 */
	@Transactional
	public BaseResp saveExchangedBill(ExchangedBillRequest req) {
		log.info("新增积分商品兑换记录, ExchangedBillRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		ExchangedBillEntity exchange = new ExchangedBillEntity();
		BeanUtils.copyProperties(req, exchange);
		exchange.setGoodsName(req.getGoodsName());
		exchange.setState(0);
		exchangedBillDao.save(exchange);
		response.setErrorCode(0);
		response.setErrorMessage("保存商品兑换记录成功");
		return response;
	}
}
