package com.guohuai.points.service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.dao.AddressDao;
import com.guohuai.points.entity.AddressEntity;
import com.guohuai.points.request.AddressRequest;
import com.guohuai.points.response.AddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AddressService {
	@Autowired
	private AddressDao addressDao;


	/**
	 * 分页查询
	 */
	public PageResp<AddressResponse> page(AddressRequest req) {

		Page<AddressEntity> pages = addressDao.findAll(buildSpecification(req), new PageRequest(req.getPage() - 1, req.getRows()));
		PageResp<AddressResponse> resPage = new PageResp<>();

		for (AddressEntity page : pages) {
			AddressResponse res = new AddressResponse();
			BeanUtils.copyProperties(page, res);
			resPage.getRows().add(res);
		}
		resPage.setTotal(pages.getTotalElements());
		log.info("收货地址查询：返回数据条数：{} ,数据总条数：{}", resPage.getRows().size(), pages.getTotalElements());
		return resPage;
	}

	private Specification<AddressEntity> buildSpecification(final AddressRequest req) {
		return new Specification<AddressEntity>() {
			@Override
			public Predicate toPredicate(Root<AddressEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != req.getStartTime()) {
					list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), req.getStartTime()));
				}
				if (null != req.getEndTime()) {
					list.add(cb.lessThan(root.get("createTime").as(Date.class), req.getEndTime()));
				}
				if (null != req.getIsdel()) {
					list.add(cb.equal(root.get("isdel").as(String.class), req.getIsdel()));
				}
				if (!StringUtil.isEmpty(req.getUserOid())) {
					list.add(cb.equal(root.get("userOid").as(String.class), req.getUserOid()));
				}
				query.where(cb.and(list.toArray(new Predicate[list.size()])));
				query.orderBy(cb.desc(root.get("createTime").as(Date.class)));

				return query.getRestriction();
			}
		};
	}

	public AddressResponse findById(String oid) {

		AddressEntity entity = addressDao.findOne(oid);
		AddressResponse billRes = new AddressResponse();
		BeanUtils.copyProperties(entity, billRes);

		return billRes;
	}

	@Transactional
	public BaseResp save(AddressRequest req) {

		if (StringUtil.isEmpty(req.getOid())) {
			int num = addressDao.countByUserOid(req.getUserOid());
			if (num >= 5) {
				return new BaseResp(-2, "地址数量超过限制！");
			}
			AddressEntity entity = new AddressEntity();
			BeanUtils.copyProperties(req, entity);

			entity.setUpdateTime(new Date());
			entity.setCreateTime(new Date());
			entity.setIsdel("no");
			entity.setIsDefault(0);
			entity = addressDao.save(entity);
			log.info("收货地址持久化数据：id={} {}", entity.getOid(), entity);
		} else {
			int num = addressDao.updateAddress(req.getName(), req.getTakeAddress(), req.getPhone(), req.getZipCode(), req.getOid());
			log.info("收货地址更新数据：num={} ", num);
		}
		return new BaseResp();
	}

	@Transactional
	public BaseResp delete(AddressRequest req) {

		BaseResp baseResp = new BaseResp();
		addressDao.isDelete(req.getOid(), req.getIsdel());
		log.info("收货地址删除成功，oid={}", req.getOid());
		return baseResp;
	}


	@Transactional
	public BaseResp setDefault(AddressRequest req) {
		addressDao.setAllAddressNotDefault(req.getUserOid());
		int num = addressDao.setAddressDefault(req.getOid());
		if (num == 0) {
			return new BaseResp(-1, "收货地址已被删除！");
		}
		return new BaseResp(0, "成功");
	}
}
