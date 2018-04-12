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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.points.component.Constant;
import com.guohuai.points.component.TradeEventCodeEnum;
import com.guohuai.points.component.TradeType;
import com.guohuai.points.dao.AccountOrderDao;
import com.guohuai.points.entity.AccountOrderEntity;
import com.guohuai.points.request.CreateAccOrderRequest;
import com.guohuai.points.request.UserPointsRequest;
import com.guohuai.points.response.CreateAccOrderResponse;
import com.guohuai.points.response.UserPointsRecords;
import com.guohuai.points.response.UserPointsRecordsResponse;

/**
 * @ClassName: AccOrderService
 * @Description: 积分订单相关
 * @author CHENDONGHUI
 * @date 2017年3月21日 下午2:19:22
 */
@Service
public class AccountOrderService {
    private final static Logger log = LoggerFactory.getLogger(AccountOrderService.class);
    @Autowired
    private AccountOrderDao orderDao;

    /**
     * 新增积分订单
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CreateAccOrderResponse addAccOrder(CreateAccOrderRequest req) {
        CreateAccOrderResponse resp = new CreateAccOrderResponse();
        resp.setReturnCode(Constant.SUCCESS);
        try {
            AccountOrderEntity orderEntity = null;
            orderEntity = this.getOrderByNo(req.getOrderNo());
            //判断订单是否已存在
            if (orderEntity != null) {
                if (AccountOrderEntity.ORDERSTATUS_SUCC.equals(orderEntity.getOrderStatus())) {//存在且为成功的订单
                    resp.setReturnCode(TradeEventCodeEnum.TRADE_2004.getCode());
                    resp.setErrorMessage(TradeEventCodeEnum.TRADE_2004.getName());//订单号已经存在
                    log.debug("订单号已存在orderNo:[" + orderEntity.getOrderNo() + "]");
                    return resp;
                }
            }
            log.info("订单交易:[" + JSONObject.toJSONString(req) + "]");
            Timestamp time = new Timestamp(System.currentTimeMillis());
            if (null == orderEntity) {
                orderEntity = new AccountOrderEntity();
            }
            String orderNo = req.getOrderNo();
            orderEntity.setRequestNo(req.getRequestNo());
            orderEntity.setSystemSource(req.getSystemSource());
            orderEntity.setOrderNo(orderNo);
            orderEntity.setUserOid(req.getUserOid());
            orderEntity.setOrderType(req.getOrderType());
            orderEntity.setRelationProductCode(req.getRelationProductNo());
            orderEntity.setRelationProductName(req.getRelationProductName());
            orderEntity.setPoint(req.getBalance());
            orderEntity.setOrderStatus(AccountOrderEntity.ORDERSTATUS_INIT);
            orderEntity.setRemark(req.getRemark());
            orderEntity.setOrderDesc(req.getOrderDesc());
            orderEntity.setUpdateTime(time);
            if (null == orderEntity.getCreateTime()) {
                orderEntity.setCreateTime(time);
            }
            log.info("保存积分定单");
            orderEntity = orderDao.save(orderEntity);
            log.info("保存积分定单结束");
            if (orderEntity != null) {
                resp.setReturnCode(Constant.SUCCESS);
                resp.setErrorMessage("成功");
                resp.setOrderOid(orderEntity.getOid());
                resp.setOrderNo(orderNo);
            }

        } catch (Exception e) {
            log.error("订单插入失败", e);
            resp.setReturnCode(Constant.FAIL);
            resp.setErrorMessage("订单保存失败");
            return resp;
        }
        return resp;
    }

    /**
     * 根据orderNo获取订单
     * @param orderNo
     * @return
     */
    public AccountOrderEntity getOrderByNo(String orderNo) {
        return orderDao.findByOrderNo(orderNo);
    }

    /**
     * 根据orderNo获取订单
     * @param oid
     * @return
     */
    public AccountOrderEntity getOrderOid(String oid) {
        return orderDao.findOne(oid);
    }
    
    /**
     * 更新订单状态
     * @param oid
     * @param orderStatus
     * @param errorMessage 
     * @return
     */
    public BaseResp updateOrderStatus(String oid, String orderStatus, String errorMessage) {
    	BaseResp response = new BaseResp();
    	log.info("{}积分订单状态变动,orderStatus:{}", oid, orderStatus);
    	//验证参数
 		if(StringUtil.isEmpty(oid)) {
 			response.setErrorCode(-1);
 			response.setErrorMessage("OID不能为空");
 			return response;
 		}
 		AccountOrderEntity orderEntity = this.getOrderOid(oid);
 		
 		if(orderEntity!=null) {
 			orderEntity.setOrderStatus(orderStatus);
 			orderEntity.setErrorMessage(errorMessage);
 			orderEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
 			orderEntity = this.orderDao.saveAndFlush(orderEntity);
            
            log.info("更新AccOrderEntity,AccOrderEntity:{}", JSON.toJSONString(orderEntity));
            response.setErrorCode(0);
        } else {
        	response.setErrorCode(-1);
        	response.setErrorMessage("找不到订单");
        }
		return response;
    }
    
    /**
     * 根据用户及筛选条件查询用户订单记录
     * @param req
     * @return
     */
	public UserPointsRecordsResponse getUserPointOrderRecord(UserPointsRequest req){
    	log.info("用户积分订单记录查询{}",JSONObject.toJSONString(req));
		Page<AccountOrderEntity> listPage = orderDao.findAll(buildSpecification(req),new PageRequest(req.getPage() - 1, req.getRows()));
		log.info(listPage.toString());
		UserPointsRecordsResponse res =new UserPointsRecordsResponse();
		if (listPage != null && listPage.getSize() > 0) {
			List<UserPointsRecords> rows = new ArrayList<UserPointsRecords>();
			for(AccountOrderEntity accountOrderEntity : listPage.getContent()){
				UserPointsRecords records = new UserPointsRecords();
				records.setPointType(accountOrderEntity.getOrderType());
				records.setPoint(accountOrderEntity.getPoint());
				records.setPointTime(accountOrderEntity.getCreateTime());
				String direction = "+";
				if(TradeType.CONSUME.getValue().equals(accountOrderEntity.getOrderType())
						||TradeType.OVERDUE.getValue().equals(accountOrderEntity.getOrderType())){
					direction = "-";
				}
				records.setDirection(direction);
				rows.add(records);
			}
			res.setRows(rows);
			res.setTotalPage(listPage.getTotalPages());
			res.setPage(req.getPage());
			res.setRow(req.getRows());
			res.setTotal(listPage.getTotalElements());
			return res;
		}
		return null;
    }
    
    /**
	 * 查询参数组装
	 * @param req
	 * @return
	 */
	public Specification<AccountOrderEntity> buildSpecification(final UserPointsRequest req) {
		Specification<AccountOrderEntity> spec = new Specification<AccountOrderEntity>() {
			@Override
			public Predicate toPredicate(Root<AccountOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> bigList =new ArrayList<Predicate>();
				if (!StringUtil.isEmpty(req.getUserOid()))
					bigList.add(cb.equal(root.get("userOid").as(String.class),req.getUserOid()));
				if (!StringUtil.isEmpty(req.getOrderType()))
					bigList.add(cb.equal(root.get("orderType").as(String.class),req.getOrderType()));
//				if (!StringUtil.isEmpty(req.getDirection()))
				if (req.getMinOrderPoint() != null && req.getMinOrderPoint().compareTo(BigDecimal.ZERO)>0)
					bigList.add(cb.greaterThanOrEqualTo(root.get("point").as(BigDecimal.class), req.getMinOrderPoint()));
				if (req.getMaxOrderPoint() != null && req.getMaxOrderPoint().compareTo(BigDecimal.ZERO)>0)
					bigList.add(cb.lessThanOrEqualTo(root.get("point").as(BigDecimal.class), req.getMaxOrderPoint()));
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

    String nullToStr(Object str) {
        if (null == str) {
            return "";
        }
        return str.toString();
    }


}