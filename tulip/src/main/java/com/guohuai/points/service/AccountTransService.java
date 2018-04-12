package com.guohuai.points.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.points.dao.AccountTransDao;
import com.guohuai.points.entity.AccountTransEntity;
import com.guohuai.points.component.AccountTypeEnum;
import com.guohuai.points.component.Constant;
import com.guohuai.points.request.AccountTransRequest;
import com.guohuai.points.response.AccountTransRes;
import com.guohuai.points.response.AccountTransResponse;

/**
 * @ClassName: AccountTransService
 * @Description: 积分交易流水相关
 * @author CHENDONGHUI
 * @date 2017年3月22日 上午11:41:12
 */
@Service
public class AccountTransService {
	private final static Logger log = LoggerFactory.getLogger(AccountTransService.class);
	
	@Autowired
	private AccountTransDao transDao;

	/**
	 * 新增积分交易流水
	 * @param req
	 * @return
	 */
	public AccountTransResponse addAccTrans(AccountTransRequest req) {
		log.info("新增积分账户交易记录：{}",JSONObject.toJSONString(req));
		AccountTransResponse resp  = new AccountTransResponse();
		resp.setReturnCode(Constant.SUCCESS);
		try {
			AccountTransEntity transEntity = new AccountTransEntity();
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String orderNo = req.getOrderNo();
            transEntity.setAccountType(req.getAccountType());
            transEntity.setAccountName(AccountTypeEnum.getEnumName(req.getAccountType()));
            if("add".equals(req.getDirection())){
            	transEntity.setDirection(AccountTransEntity.ADD);
            }else{
            	transEntity.setDirection(AccountTransEntity.REDUCE);
            }
            transEntity.setOrderPoint(req.getOrderPoint());
            transEntity.setPoint(req.getBalance());
            transEntity.setTransAccountNo(req.getTransAccountNo());
        	transEntity.setRequestNo(req.getRequestNo());
        	transEntity.setSystemSource(req.getSystemSource());
        	transEntity.setOrderNo(orderNo);
        	transEntity.setUserOid(req.getUserOid());
        	transEntity.setOrderType(req.getOrderType());
        	transEntity.setRelationProductCode(req.getRelationProductNo());
        	transEntity.setRelationProductName(req.getRelationProductName());
        	transEntity.setPoint(req.getBalance());
        	transEntity.setRemark(req.getRemark());
        	transEntity.setOrderDesc(req.getOrderDesc());
        	transEntity.setOrderDesc(req.getOrderDesc());
        	transEntity.setUpdateTime(time);
            transEntity.setCreateTime(time);
            log.info("保存积分交易流水");
            transEntity = transDao.save(transEntity);
            log.info("保存积分交易流水结束");
            if (transEntity != null) {
                resp.setReturnCode(Constant.SUCCESS);
                resp.setErrorMessage("成功");
            }

        } catch (Exception e) {
            log.error("保存积分交易流水失败", e);
            resp.setReturnCode(Constant.FAIL);
            resp.setErrorMessage("保存积分交易流水失败");
            return resp;
        }
		return resp;
	}

	/**
	 * 批量新增账户交易流水
	 * @param list
	 * @return
	 */
	@Transactional
	public AccountTransResponse addAccTransList(List<AccountTransRequest> list) {
		AccountTransResponse resp  = new AccountTransResponse();
		for(AccountTransRequest req : list){
			resp = addAccTrans(req);
			if(!Constant.SUCCESS.equals(resp.getReturnCode())){
				return resp;
			}
		}
		return resp;
	}
	
	/**
	 * 获取用户交易记录流水
	 * @param req
	 * @return
	 */
	public AccountTransRes getUserAccountTrans(AccountTransRequest req){
		log.info("积分账户交易记录查询",JSONObject.toJSONString(req));
		Page<AccountTransEntity> listPage = transDao.findAll(buildSpecification(req),new PageRequest(req.getPage() - 1, req.getRow()));
		AccountTransRes res =new AccountTransRes();
		if (listPage != null && listPage.getSize() > 0) {
			res.setRows(listPage.getContent());
			res.setTotalPage(listPage.getTotalPages());
			res.setPage(req.getPage());
			res.setRow(req.getRow());
			res.setTotal(listPage.getTotalElements());
			return res;
		}
		return res;
	}
	
	/**
	 * 查询参数组装
	 * @param req
	 * @return
	 */
	public Specification<AccountTransEntity> buildSpecification(final AccountTransRequest req) {
		Specification<AccountTransEntity> spec = new Specification<AccountTransEntity>() {
			@Override
			public Predicate toPredicate(Root<AccountTransEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> bigList =new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getUserOid()))
					bigList.add(cb.equal(root.get("userOid").as(String.class),req.getUserOid()));
				if (!StringUtil.isEmpty(req.getOrderNo()))
					bigList.add(cb.equal(root.get("orderNo").as(String.class),req.getOrderNo()));
				if (!StringUtil.isEmpty(req.getOrderType()))
					bigList.add(cb.equal(root.get("orderType").as(String.class),req.getOrderType()));
				if (!StringUtil.isEmpty(req.getAccountType()))
					bigList.add(cb.equal(root.get("accountType").as(String.class),req.getAccountType()));
				if (!StringUtil.isEmpty(req.getDirection()))
					bigList.add(cb.equal(root.get("direction").as(String.class),req.getDirection()));
				if (req.getMinOrderPoint() != null && req.getMinOrderPoint().compareTo(BigDecimal.ZERO)>0)
					bigList.add(cb.greaterThanOrEqualTo(root.get("amount").as(BigDecimal.class), req.getMinOrderPoint()));
				if (req.getMaxOrderPoint() != null && req.getMaxOrderPoint().compareTo(BigDecimal.ZERO)>0)
					bigList.add(cb.lessThanOrEqualTo(root.get("amount").as(BigDecimal.class), req.getMaxOrderPoint()));
				if (!StringUtil.isEmpty(req.getBeginTime())) {
					Date beginDate = DateUtil.parseDate(req.getBeginTime(), "yyyy-MM-dd HH:mm:ss");
					bigList.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Timestamp.class),
							new Timestamp(beginDate.getTime())));
				}
				if (!StringUtil.isEmpty(req.getEndTime())) {
					Date endDate = DateUtil.parseDate(req.getEndTime(), "yyyy-MM-dd HH:mm:ss");
					bigList.add(cb.lessThanOrEqualTo(root.get("createTime").as(Timestamp.class),
							new Timestamp(endDate.getTime())));
				}
				query.where(cb.and(bigList.toArray(new Predicate[bigList.size()])));
				query.orderBy(cb.desc(root.get("createTime")));
				
				// 条件查询
				return query.getRestriction();
			}
		};
		return spec;
	}

	/**
	 * 根据用户订单号获取交易流水
	 * @param userOid
	 * @param oldOrderNo
	 * @return
	 */
	public List<AccountTransEntity> getUserAccountTransByOrderNo(
			String userOid, String oldOrderNo) {
		List<AccountTransEntity> accountTranslist = transDao.findByUserAndOrderNo(userOid,oldOrderNo);
		return accountTranslist;
	}
}