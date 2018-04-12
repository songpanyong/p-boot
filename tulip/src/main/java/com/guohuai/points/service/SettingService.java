package com.guohuai.points.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.dao.SettingDao;
import com.guohuai.points.entity.PointSettingEntity;
import com.guohuai.points.request.SettingRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 积分产品设置<br>
 * 业务：使用现金购买积分产品
 * @author mr_gu
 *
 */
@Slf4j
@Service
public class SettingService {
	
	@Autowired
	private SettingDao settingDao;
	
	/**
	 * 新增积分商品
	 * @param req
	 */
	public BaseResp savePoints(SettingRequest req){
		log.info("新增积分产品参数, createPointsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointSettingEntity entity = new PointSettingEntity();
		BeanUtils.copyProperties(req, entity);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setOid(StringUtil.uuid());
		entity.setState(0);
		settingDao.save(entity);
		response.setErrorCode(0);
		return response;
	}
	
	/**
	 * 编辑积分商品
	 * @param req
	 */
	public BaseResp updatePoints(SettingRequest req){
		log.info("编辑积分产品参数, createPointsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointSettingEntity entity = settingDao.findOne(req.getOid());
		if(null != entity){
			entity.setName(req.getName());
			entity.setPoints(req.getPoints());
			entity.setAmount(req.getAmount());
			entity.setTotalCount(req.getTotalCount());
			entity.setRemainCount(req.getRemainCount());
			entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			settingDao.save(entity);
			response.setErrorCode(0);
		}else{
			response.setErrorCode(-1);
		}
		
		return response;
	}
	
	/**
	 * 上架、下架、删除积分产品
	 * @param req
	 */
	public BaseResp editPoints(SettingRequest req){
		log.info("上架、下架积分商品参数, createPointsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointSettingEntity entity = settingDao.findOne(req.getOid());
		if(null != entity){
			entity.setState(req.getState());
			entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			settingDao.save(entity);
			response.setErrorCode(0);
		}else{
			response.setErrorCode(-1);
		}
		
		return response;
	}
	
	/**
	 * 分页查询积分产品列表
	 * @param req
	 * @return
	 */
	public PageResp<PointSettingEntity> page(SettingRequest req){
		log.info("积分产品查询参数, createPointsRequest:{}", JSON.toJSONString(req));
		PageResp<PointSettingEntity> pagesRep = new PageResp<PointSettingEntity>();
		Page<PointSettingEntity> listPage = settingDao.findAll(this.buildSpecification(req), new PageRequest(req.getPage() -1 , req.getRows()));
		if(null != listPage && listPage.getSize() > 0){
			pagesRep.setRows(listPage.getContent());
			pagesRep.setTotal(listPage.getTotalElements());
		}
		
		return pagesRep;
	}
	
	/**
	 * 积分产品的查询条件
	 * @param req
	 * @return
	 */
	public Specification<PointSettingEntity> buildSpecification(final SettingRequest req){
		Specification<PointSettingEntity> spec = new Specification<PointSettingEntity>() {
			@Override
			public Predicate toPredicate(Root<PointSettingEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> bigList = new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getName())){
					bigList.add(cb.like(root.get("name").as(String.class), "%" +req.getName() + "%"));					
				}
				if (null != req.getPoints()){
					bigList.add(cb.equal(root.get("points"), req.getPoints()));					
				}
				if (null != req.getState()){
					bigList.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				} else {
					bigList.add(cb.gt(root.get("state").as(Integer.class), -1));
				}
				
				query.where(cb.and(bigList.toArray(new Predicate[bigList.size()])));
				query.orderBy(cb.desc(root.get("createTime")));
				return query.getRestriction();
			}
		};
		return spec;
	}
	
}
