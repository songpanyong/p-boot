package com.guohuai.points.service;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.dao.GoodsDao;
import com.guohuai.points.dao.PointFileDao;
import com.guohuai.points.entity.AccountInfoEntity;
import com.guohuai.points.entity.PointFileEntity;
import com.guohuai.points.entity.PointGoodsEntity;
import com.guohuai.points.request.GoodsRequest;
import com.guohuai.points.response.GoodsResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private PointFileDao pointFileDao;
	
	@Autowired
	private AccountInfoService accountInfoService;
	
	private final static String CATE = "POINT";
	
	/**
	 * 新增积分商品
	 * @param req
	 */
	@Transactional
	public BaseResp saveGoods(GoodsRequest req){
		log.info("新增积分商品参数, createGoodsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointGoodsEntity entity = new PointGoodsEntity();
		BeanUtils.copyProperties(req, entity);
		//默认已兑换数为0,库存和总数相同
		entity.setRemainCount(req.getTotalCount());
		entity.setExchangedCount(BigDecimal.ZERO);
		entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
		entity.setState(0);
		entity = goodsDao.save(entity);
		
		this.savFilesWithGoods(entity.getOid(), req.getFiles());
		
		response.setErrorCode(0);
		return response;
	}
	
	/**
	 * 在保存图片时，先删除历史图片信息，再加入最新图片信息
	 * @param goodsOid
	 * @param Files
	 */
	private void savFilesWithGoods(String goodsOid, String Files){
		if(!StringUtil.isEmpty(Files) && !StringUtil.isEmpty(goodsOid)){
			log.info("待上传的积分商品图片： {} ", Files);
			String[] urls = Files.split(",");
			List<PointFileEntity> deleEntitys = pointFileDao.findByGoodsOid(goodsOid);
			if(null != deleEntitys && deleEntitys.size() > 0){
				pointFileDao.delete(deleEntitys);
			}
			List<PointFileEntity> list = new ArrayList<PointFileEntity>();
			for (String url : urls) {
				PointFileEntity file = new PointFileEntity();
				file.setCate(CATE);
				file.setGoodsOid(goodsOid);
				file.setFkey(url);
				file.setCreateTime(new Timestamp(System.currentTimeMillis()));
				list.add(file);
			}
			//批量保存图片url数据
			pointFileDao.save(list);
		}
	}
	
	/**
	 * 编辑积分商品
	 * @param req
	 */
	@Transactional
	public BaseResp updateGoods(GoodsRequest req){
		log.info("编辑积分商品参数, createGoodsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointGoodsEntity entity = goodsDao.findOne(req.getOid());
		if(null != entity){
			entity.setName(req.getName());
			entity.setType(req.getType());
			entity.setVirtualCouponType(req.getVirtualCouponType());
			entity.setIssueVirtualCouponId(req.getIssueVirtualCouponId());
			entity.setNeedPoints(req.getNeedPoints());
			entity.setTotalCount(req.getTotalCount());
			entity.setRemainCount(req.getTotalCount());
			entity.setExchangedCount(BigDecimal.ZERO);
			entity.setRemark(req.getRemark());
			entity.setFileOid(req.getFileOid());
			entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			goodsDao.save(entity);
			
			this.savFilesWithGoods(entity.getOid(), req.getFiles());
			
			response.setErrorCode(0);
		}else{
			response.setErrorCode(-1);
		}
		return response;
	}
	
	/**
	 * 上架、下架、删除积分商品
	 * @param req
	 */
	@Transactional
	public BaseResp editGoods(GoodsRequest req){
		log.info("上架、下架积分商品参数, createGoodsRequest:{}", JSON.toJSONString(req));
		BaseResp response = new BaseResp();
		PointGoodsEntity entity = goodsDao.findOne(req.getOid());
		if(null != entity){
			entity.setState(req.getState());
			entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			goodsDao.save(entity);
			response.setErrorCode(0);
		}else{
			response.setErrorCode(-1);
		}
		
		return response;
	}
	
	/**
	 * 分页查询积分商品列表
	 * @param req
	 * @return
	 */
	public PageResp<PointGoodsEntity> page(GoodsRequest req){
		Page<PointGoodsEntity> listPage = goodsDao.findAll(this.buildSpecification(req), new PageRequest(req.getPage() -1 , req.getRows()));
		PageResp<PointGoodsEntity> pagesRep = new PageResp<PointGoodsEntity>();
		if(null != listPage && listPage.getSize() > 0){
			pagesRep.setRows(listPage.getContent());
			pagesRep.setTotal(listPage.getTotalElements());
		}
		return pagesRep;
	}
	
	/**
	 * 商品的查询条件
	 * @param req
	 * @return
	 */
	public Specification<PointGoodsEntity> buildSpecification(final GoodsRequest req){
		Specification<PointGoodsEntity> spec = new Specification<PointGoodsEntity>() {
			@Override
			public Predicate toPredicate(Root<PointGoodsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> bigList = new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getName())){
					bigList.add(cb.like(root.get("name").as(String.class), "%" + req.getName() + "%"));					
				}
				if (!StringUtil.isEmpty(req.getType())){
					bigList.add(cb.equal(root.get("type").as(String.class), req.getType()));					
				}
				if (null != req.getState()){
					bigList.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				} else {
					//默认查询非删除的商品
					bigList.add(cb.gt(root.get("state").as(Integer.class), -1));
				}
				
				query.where(cb.and(bigList.toArray(new Predicate[bigList.size()])));
				query.orderBy(cb.desc(root.get("createTime")));
				return query.getRestriction();
			}
		};
		return spec;
	}
	
	/**
	 * 积分商品前台接口：首页积分商品列表
	 * 
	 * @param req
	 * @return
	 */
	public PageResp<GoodsResponse> findPointGoodsList(GoodsRequest req){
		log.info("积分商城前台首页积分列表,查询参数:{}!", JSONObject.toJSONString(req));
		Page<PointGoodsEntity> listPage = goodsDao.findAll(this.buildWebSpecification(req), new PageRequest(req.getPage() -1 , req.getRows()));
		PageResp<GoodsResponse> pagesRep = new PageResp<GoodsResponse>();
		if(null != listPage && listPage.getSize() > 0){
			for (PointGoodsEntity page : listPage) {
				GoodsResponse res = new GoodsResponse();
				BeanUtils.copyProperties(page, res);
				List<PointFileEntity> files = pointFileDao.findByGoodsOid(page.getOid());
				if(null != files && files.size() > 0){
					res.setFileUrl(files.get(0).getFkey());
				}
				pagesRep.getRows().add(res);
			}
		}
		pagesRep.setTotal(listPage.getTotalElements());
		log.info("积分商品列表查询：返回数据条数：{} ,数据总条数：{}", pagesRep.getRows().size(), listPage.getTotalElements());
		return pagesRep;
	}
	
	public Specification<PointGoodsEntity> buildWebSpecification(final GoodsRequest req){
		Specification<PointGoodsEntity> spec = new Specification<PointGoodsEntity>() {
			@Override
			public Predicate toPredicate(Root<PointGoodsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> bigList = new ArrayList<Predicate>();
				if (req.getMinPoints() != null && req.getMinPoints().compareTo(BigDecimal.ZERO)>0){
					bigList.add(cb.greaterThanOrEqualTo(root.get("needPoints").as(BigDecimal.class), req.getMinPoints()));
				}
				if (req.getMaxPoints() != null && req.getMaxPoints().compareTo(BigDecimal.ZERO)>0){
					bigList.add(cb.lessThanOrEqualTo(root.get("needPoints").as(BigDecimal.class), req.getMaxPoints()));					
				}
				if (null != req.getState()){
					bigList.add(cb.equal(root.get("state").as(Integer.class), req.getState()));
				} else {
					//默认查询已上架的商品
					bigList.add(cb.equal(root.get("state").as(Integer.class), 1));
				}
				
				query.where(cb.and(bigList.toArray(new Predicate[bigList.size()])));
				query.orderBy(cb.desc(root.get("createTime")));
				return query.getRestriction();
			}
		};
		return spec;
	}
	
	/**
	 * 兑换积分之前检查：1.兑换数量对比库存；2.个人积分余额是否充足
	 * @param req
	 * @return
	 */
	public GoodsResponse checkGoodsPoint(GoodsRequest req){
		GoodsResponse resp = new GoodsResponse();
		AccountInfoEntity accountInfo = accountInfoService.getAccountByTypeAndUser("01", req.getUserOid());
		if(null == accountInfo || accountInfo.getBalance().compareTo(BigDecimal.ZERO) < 1){
			resp.setErrorCode(120001);
			resp.setErrorMessage("当前用户的积分余额小于0，请充值");
			return resp;
		}
		if(req.getNeedPoints().compareTo(accountInfo.getBalance()) == 1){
			resp.setErrorCode(120001);
			resp.setErrorMessage("当前用户的积分余额不足，请充值");
			return resp;
		}
		
		resp.setErrorCode(0);
		return resp;
	}
	
	/**
	 * 根据Id查询积分商品信息
	 * @param oid
	 * @return
	 */
	public GoodsResponse findGoodsByOid(String oid){
		GoodsResponse resp = new GoodsResponse();
		PointGoodsEntity entity = goodsDao.findOne(oid);
		BeanUtils.copyProperties(entity, resp);
		return resp;
	}
	
	/**
	 * 查询积分商品，并锁定该条记录for update
	 * @param oid
	 * @return
	 */
	public GoodsResponse findGoodsForupdateByOid(String oid){
		GoodsResponse resp = new GoodsResponse();
		PointGoodsEntity entity = goodsDao.forUpdateByOid(oid);
		BeanUtils.copyProperties(entity, resp);
		return resp;
	}
	
	/**
	 * 修改积分商品库存<br>
	 * -: 库存减、同时已兑换数加<br>
	 * +: 库存加、同时已兑换数减
	 * @param oid 商品Id
	 * @param direction 库存操作（只能是 +或者-）
	 * @param value 需要修改的库存量
	 * @return
	 */
	public GoodsResponse changeStockGoods(String oid, String direction, BigDecimal value){
		log.info("修改积分商品库存， 商品号：{}, 库存操作是：{}; 需要变更的库存数量为：{} ", oid, direction, value);
		GoodsResponse resp = new GoodsResponse();
		if(!direction.equals("+") && !direction.equals("-")){
			resp.setErrorCode(-1);
			resp.setErrorMessage("积分的只能+或者-");
			return resp;
		}
		int rowNumb = 0;
		if(direction.equals("+")){
			//+加库存，即积分退回操作，用于在订单被拒绝的时候使用
			rowNumb = goodsDao.addStockGoods(oid, value);
		}
		if(direction.equals("-")){
			//库存-、已兑换数+
			rowNumb = goodsDao.subtractStockGoods(oid, value);
		}
		if(rowNumb > 0){
			resp.setErrorCode(0);
			log.info("积分商品 id：{} 的库存操作成功 ", oid);
		}else{
			resp.setErrorCode(120002);
			resp.setErrorMessage("该商品库存不足，操作失败");
		}

		log.info("库存操作完成  GoodsResponse:{}",JSONObject.toJSON(resp));
		return resp;
	}
	
	/**
	 * 积分商品前台接口：查询积分商品详情
	 * 
	 * @param req
	 * @return
	 */
	public GoodsResponse getPointGoodsDetail(GoodsRequest req){
		GoodsResponse resp = new GoodsResponse();
		PointGoodsEntity entity = goodsDao.findOne(req.getOid());
		if(null == entity){
			resp.setErrorCode(-1);
			resp.setErrorMessage("该积分商品不存在");
			return resp;
		}
		
		BeanUtils.copyProperties(entity, resp);		
		List<PointFileEntity> deleEntitys = pointFileDao.findByGoodsOid(entity.getOid());
		List<String> fkey = new ArrayList<String>();
		for (PointFileEntity file : deleEntitys) {
			fkey.add(file.getFkey());
		}
		resp.setPointFiles(fkey);
		
		return resp;
	}
	
}
